FROM adoptopenjdk/openjdk15:jre-15.0.2_7-alpine
ENV TZ=Asia/Almaty
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]