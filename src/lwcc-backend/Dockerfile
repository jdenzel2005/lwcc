FROM registry-emea.app.corpintra.net/i3-images/eclipse-temurin:21-jre-alpine-2025.05.06

RUN mkdir /home/javarun/config

COPY ./build/libs/app.jar /home/javarun/app.jar

CMD ["java", "-jar", "-XX:InitialRAMPercentage=50.0", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "/home/javarun/app.jar"]
