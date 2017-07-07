FROM goyalzz/ubuntu-java-8-maven-docker-image:16.04
ARG branch

WORKDIR /
RUN git clone -b $branch https://teamEreticoTfs:hI#EMK-i=d@tfs.planetek.it/SBU-GS/pkz029_UU_CMEMS/_git/pkz029_UU_CMEMS_Manager_OD_Request /src
WORKDIR /src
RUN mvn package
CMD java -jar target/manager.jar
