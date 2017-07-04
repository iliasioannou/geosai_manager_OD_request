# Requisites

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