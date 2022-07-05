FROM openjdk:11
RUN apt-get update && apt-get install -y maven
COPY . /project
RUN cd /project && mvn package -DskipTests
ENTRYPOINT ["java","-jar","/project/target/interview-0.0.1-SNAPSHOT.jar"]