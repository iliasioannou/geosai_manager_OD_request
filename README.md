# Requirements

- Mysql (use the docker-compose file to pull up a Mysql instance)


# Start processing

Example call:

    POST /processings
    
    {
        "userEmail": "ciccio",
        "processingInputData":{
            "dates":["2017-06-20", "2017-06-23"],
            "aoi":"1",
            "product":"1"
        }
    }

# Build the Docker image

In order to build the Docker image, just move in the *docker* folder and type:

    ./build.sh <branch_name>

# Run in local

Check compose file to use the *planetek/manager_od_dev* image
check application properties file to be sure all needed services are up and running;
build the image

    ./build_dev.sh <develop> 

Run compose!