FROM goyalzz/ubuntu-java-8-maven-docker-image:16.04
ARG branch

WORKDIR /
RUN git clone -b $branch https://teamEreticoTfs:hIEMK-i=d@tfs.planetek.it/Planetek%20Hellas/pkh111_EOSAI/_git/pkh111_EOSAI_Manager_OD_Request /src
WORKDIR /src
RUN mvn package
CMD java -jar target/manager.jar
