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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    private String createZipResult(String result) throws IOException {
        ZipUtil.pack(new File(result), new File("download.zip"));
        return "donwload.zip";
    }

    private Processing updateProcessingFinishedOK(Processing processing, String resultPath) {
        processing.setStatus(StatusConstants.DONE);
        processing.setResultPath(resultPath);
        return processingRepository.save(processing);
    }

    private Processing updateProcessingFinishedError(Processing processing) {
        processing.setStatus(StatusConstants.DONE);
        return processingRepository.save(processing);
    }


    @Override
    @Async(value = "processCallerExecutor")
    public void startProcessing(ProcessingModel processingModel, Processing processing) {
        try {
            String jsonData = new ObjectMapper().writeValueAsString(processingModel.getProcessingInputData());
            XMLRPCClient client = new XMLRPCClient(new URL("http://processors:9091"));
            String result = (String) client.call("execute", jsonData);

            HashMap<String, String> resultMap = new ObjectMapper().readValue(result, HashMap.class);
            //String outPath = createZipResult(resultMap.get("outPath"));
            updateProcessingFinishedOK(processing, "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            updateProcessingFinishedError(processing);
        } catch (XMLRPCServerException ex) {
            updateProcessingFinishedError(processing);
        } catch (XMLRPCException ex) {
            updateProcessingFinishedError(processing);
        } catch (Exception ex) {
            updateProcessingFinishedError(processing);
        }
    }
}
