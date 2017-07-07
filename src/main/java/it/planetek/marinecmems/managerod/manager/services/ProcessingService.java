package it.planetek.marinecmems.managerod.manager.services;

import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.processor.exceptions.ProcessingRequestAlreadyInQueueException;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
public interface ProcessingService {
    Processing createNewProcessing(ProcessingModel processingModel);

    Processing updateProcessingFinishedOK(Processing processing, String resultPath);

    Processing updateProcessingFinishedError(Processing processing);

    void isAlreadyEnqueued(String userEmail) throws ProcessingRequestAlreadyInQueueException;
}
