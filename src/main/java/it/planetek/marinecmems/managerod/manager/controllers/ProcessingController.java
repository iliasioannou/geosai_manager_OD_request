package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.processor.services.ProcessorService;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Francesco Bruni on 7/4/17.
 */
@RepositoryRestController
@RequestMapping("/processings")
public class ProcessingController {

    @Autowired
    private ProcessingService processingService;

    @Autowired
    private ProcessorService processorService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Processing> startProcessing(@RequestBody  ProcessingModel processingModel) throws ProcessingInputParamsException {
        Processing processing = processingService.createNewProcessing(processingModel);
        processorService.startProcessing(processingModel, processing);
        return new ResponseEntity<>(processing, HttpStatus.CREATED);
    }
}
