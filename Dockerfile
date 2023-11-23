FROM openjdk:17
EXPOSE 8080
ADD target/onboarding-app-ci-cd-workflow.jar onboarding-app-ci-cd-workflow.jar
ENTRYPOINT ["java","-jar","onboarding-app-ci-cd-workflow.jar"]