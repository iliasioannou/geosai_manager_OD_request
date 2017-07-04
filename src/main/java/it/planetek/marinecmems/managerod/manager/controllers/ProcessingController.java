package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@Controller
public class ProcessingController {

    @Autowired
    private ProcessingService processingService;

    @ResponseBody
    @RequestMapping(value = "/processings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<ProcessingResource>> startProcessing(@RequestBody  ProcessingModel processingModel){
        Processing processing = processingService.createNewProcessing(processingModel);
        processingService.startProcessing(processingModel, processing);
        ProcessingResource processingResource = new ProcessingResource(
                processing.getUserEmail(),
                processing.getProcessingData(),
                processing.getStatus(),
                processing.getTimestampRequest(),
                processing.getLastUpdate(),
                processing.getResultPath()
        );
        Resource<ProcessingResource> resource = new Resource<>(processingResource);
        resource.add(new Link("http://temisto.planetek.it:9999/processings/"+processing.getId(), "self"));
        resource.add(new Link("http://temisto.planetek.it:9999/processings/"+processing.getId(), "processing"));
        resource.add(new Link("http://temisto.planetek.it:9999/processings/"+processing.getId()+"/processingData/"+processing.getProcessingData().getId(), "processingData"));
        return ResponseEntity.ok(resource);

    }
}
