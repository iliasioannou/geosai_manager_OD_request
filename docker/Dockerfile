FROM goyalzz/ubuntu-java-8-maven-docker-image:16.04
RUN mvn package -Dmaven.test.skip=true
CMD java -jar target/manager.jar