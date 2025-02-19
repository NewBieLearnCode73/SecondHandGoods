FROM maven:3.5.3-jdk-8-alpine AS build
WORKDIR /app
COPY . .
RUN mvn package

