FROM openjdk:8
COPY build/libs/product-1.0.0.jar product-1.0.0.jar
ENTRYPOINT ["java", "-jar", "/product-1.0.0.jar"]
