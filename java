FROM maven:3.6.2-jdk-11
EXPOSE 4567
WORKDIR /code
COPY . /code
RUN mvn dependency:go-offline
