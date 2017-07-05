package it.planetek.marinecmems.managerod.manager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.manager.domains.constants.StatusConstants;
import it.planetek.marinecmems.managerod.manager.repositories.ProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
        processingData.setStartDate(dates.get(1));
        processingData.setEndDate(dates.get(0));
        processingData.setProduct(processingModel.getProcessingInputData().getProduct());

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


}
