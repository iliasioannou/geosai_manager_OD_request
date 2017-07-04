package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@RepositoryRestController
public class ProcessingController {

    @Autowired
    private ProcessingService processingService;

    @ResponseBody
    @RequestMapping(value = "/processings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Processing startProcessing(@RequestBody  ProcessingModel processingModel){
        Processing processing = processingService.createNewProcessing(processingModel);
        processingService.startProcessing(processingModel, processing);
        return processing;
    }
}
