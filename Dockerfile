# --- Estágio 1: Build (Compilação com Java 21) ---
# Usamos uma imagem do Maven que já contém o JDK 21 para compilar a aplicação.
# O alias "build" nos permite referenciar este estágio posteriormente.
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container.
WORKDIR /app

# Copia o arquivo de configuração do Maven (pom.xml) primeiro.
# Isso aproveita o cache de camadas do Docker: se as dependências não mudarem,
# o Docker não precisará baixá-las novamente em builds futuros.
COPY pom.xml .

# Baixa todas as dependências do projeto.
RUN mvn dependency:go-offline

# Copia o restante do código-fonte da aplicação.
COPY src ./src

# Executa o comando para compilar o projeto e empacotá-lo em um arquivo .jar.
# -DskipTests pula a execução dos testes para acelerar o processo de build do container.
RUN mvn package -DskipTests


# --- Estágio 2: Runtime (Execução com Java 21) ---
# Usamos uma imagem JRE (Java Runtime Environment) slim para Java 21.
# Ela é muito menor que a imagem JDK, pois contém apenas o necessário para executar.
FROM eclipse-temurin:21-jre

# Define o diretório de trabalho.
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de "build" para a imagem final.
# O nome do arquivo .jar pode variar, por isso usamos um curinga (*)
# e o renomeamos para app.jar para facilitar.
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão para aplicações Spring Boot.
EXPOSE 8080

# Define o comando que será executado quando o container iniciar.
# Este comando inicia a aplicação Java a partir do arquivo .jar.
ENTRYPOINT ["java", "-jar", "app.jar"]

