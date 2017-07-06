package it.planetek.marinecmems.managerod.processor.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
public interface ProcessorService {

    Processing startProcessing(ProcessingModel processingModel, Processing processing) throws ProcessingInputParamsException;

}
