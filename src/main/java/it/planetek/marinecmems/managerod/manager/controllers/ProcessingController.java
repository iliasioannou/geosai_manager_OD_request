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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by Francesco Bruni on 7/4/17.
 */
@RepositoryRestController
@RequestMapping("/processings")
@Validated
public class ProcessingController {

    @Autowired
    private ProcessingService processingService;

    @Autowired
    private ProcessorService processorService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Processing> startProcessing(@Valid @RequestBody  ProcessingModel processingModel, BindingResult bindingResult) throws ProcessingInputParamsException {
        Processing processing = processingService.createNewProcessing(processingModel);
        processorService.startProcessing(processingModel, processing);
        return new ResponseEntity<>(processing, HttpStatus.CREATED);
    }
}
