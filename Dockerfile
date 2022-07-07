FROM openjdk:18-alpine
COPY ./ /truck-microservice/
WORKDIR /truck-microservice/
EXPOSE 8080
EXPOSE 27017
CMD ./gradlew run