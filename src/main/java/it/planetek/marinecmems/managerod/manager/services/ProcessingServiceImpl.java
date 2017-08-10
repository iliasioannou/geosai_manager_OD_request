package it.planetek.marinecmems.managerod.manager.services;

import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.manager.domains.constants.StatusConstants;
import it.planetek.marinecmems.managerod.manager.repositories.ProcessingRepository;
import it.planetek.marinecmems.managerod.processor.exceptions.ProcessingRequestAlreadyInQueueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Francesco Bruni on 7/4/17.
 */
@Service("ProcessingService")
public class ProcessingServiceImpl implements ProcessingService {

    @Autowired
    private ProcessingRepository processingRepository;

    @Override
    public Processing createNewProcessing(ProcessingModel processingModel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        List<Date> dates = processingModel.getProcessingInputData().getDates().stream().map(d -> {
            try {
                return sdf.parse(d);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }).collect(Collectors.toList());
        ProcessingData processingData = new ProcessingData();
        processingData.setAoi(processingModel.getProcessingInputData().getAoi());
        processingData.setStartDate(dates.get(0));
        processingData.setEndDate(dates.get(1));
        processingData.setProduct(processingModel.getProcessingInputData().getProducts());

        Processing processing = new Processing();
        processing.setProcessingData(processingData);
        processing.setUserEmail(processingModel.getUserEmail());
        processing.setTimestampRequest(new Date());
        return processingRepository.save(processing);
    }

    @Override
    public Processing updateProcessingFinishedOK(Processing processing, String resultPath) {
        processing.setStatus(StatusConstants.DONE);
        processing.setResultPath(resultPath);
        return processingRepository.save(processing);
    }

    @Override
    public Processing updateProcessingFinishedError(Processing processing) {
        processing.setStatus(StatusConstants.ERROR);
        return processingRepository.save(processing);
    }

    /***
     * Check if a request by the same user has been already enqueued and raise an exception if so.
     * Completed requests do not apply to "enqueued" status.
     * @param userEmail the email which will be used to check current processings
     * @throws ProcessingRequestAlreadyInQueueException if processing has been already enqueued
     */
    @Override
    public void isAlreadyEnqueued(String userEmail) throws ProcessingRequestAlreadyInQueueException {
        List<Processing> processings = processingRepository.findByUserEmailAndStatusNotIn(userEmail, Arrays.asList(StatusConstants.TODO, StatusConstants.WIP));
        if (processings.size() > 0)
            throw new ProcessingRequestAlreadyInQueueException("Processing request has been already enqueued; user email is " + userEmail);
    }


}
