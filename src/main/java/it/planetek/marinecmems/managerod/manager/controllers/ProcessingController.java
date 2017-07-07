package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.mailsender.services.MailService;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import it.planetek.marinecmems.managerod.processor.exceptions.ProcessingRequestAlreadyInQueueException;
import it.planetek.marinecmems.managerod.processor.services.ProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/4/17.
 */
@RepositoryRestController
@RequestMapping("/processings")
@Validated
public class ProcessingController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private ProcessorService processorService;
    @Autowired
    private MailService mailService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Processing> startProcessing(@Valid @RequestBody ProcessingModel processingModel, BindingResult bindingResult) throws ProcessingInputParamsException, ProcessingRequestAlreadyInQueueException {
        log.info("------ START MANAGER OD REQUEST -----");
        log.info("Request user: ".concat(processingModel.getUserEmail()));
        log.info("RequestData: AOI".concat(processingModel.getProcessingInputData().getAoi()));
        log.info("RequestData: Products".concat(processingModel.getProcessingInputData().getProducts()));
        log.info("RequestData: Start Date ".concat(processingModel.getProcessingInputData().getDates().get(0)));
        log.info("RequestData: End Date ".concat(processingModel.getProcessingInputData().getDates().get(1)));

        processingService.isAlreadyEnqueued(processingModel.getUserEmail());
        Processing processing = processingService.createNewProcessing(processingModel);
        mailService.sendMailEnqueuedRequest(processing);
        processorService.startProcessing(processingModel, processing);
        log.info("------ END MANAGER OD REQUEST -----");
        return new ResponseEntity<>(processing, HttpStatus.CREATED);
    }
}
