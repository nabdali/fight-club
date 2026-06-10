FROM eclipse-temurin:26-jdk-jammy AS build
ARG MAVEN_VERSION=3.9.14
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*
RUN wget -q https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    && tar xf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/local/bin/mvn \
    && rm apache-maven-${MAVEN_VERSION}-bin.tar.gz
WORKDIR /app
COPY pom.xml .
COPY arena-service/pom.xml arena-service/pom.xml
RUN mvn dependency:go-offline -q -f arena-service/pom.xml
COPY arena-service/src arena-service/src
RUN mvn package -DskipTests -q -f arena-service/pom.xml

FROM eclipse-temurin:26-jre-jammy
WORKDIR /app
COPY --from=build /app/arena-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
