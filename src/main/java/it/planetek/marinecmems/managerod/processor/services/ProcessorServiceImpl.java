package it.planetek.marinecmems.managerod.processor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import it.planetek.marinecmems.managerod.processor.utils.Zipper;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.processor.exceptions.ProcessorResultException;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
@Service("ProcessorService")
public class ProcessorServiceImpl implements ProcessorService {

    @Value("${processor.methodName}")
    private String processorMethodName;

    @Value("${processor.outputFolder}")
    private String processorOuptutFolder;

    @Value("${download.outputFolder}")
    private String downloadOutputFolder;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProcessingService processingService;

    @Autowired
    private Zipper zipperService;

    @Autowired
    private XMLRPCClient client;

    /***
     * Call the RPC XML server via RPC XML client
     * @param jsonData the data to be posted
     * @param methodName the method name over the RPC server
     * @return the method result
     * @throws XMLRPCException for common issues related to RPC server
     * @throws MalformedURLException if provided URL is not syntactically correct
     */
    private String callRpcServer(String jsonData, String methodName) throws XMLRPCException, MalformedURLException {
        return (String) client.call(methodName, jsonData);
    }

    /**
     * Validate result data coming from processor: check for present 'outPath' and  valid 'result' attribute
     * @param resultData the resultData to be validated
     * @throws ProcessorResultException if a rule doesn't apply
     */
    private void validateResult(HashMap<String, Object> resultData) throws ProcessorResultException{
        Optional.ofNullable(resultData.getOrDefault("outPath", null))
                .orElseThrow(() -> new ProcessorResultException("Out path folder seems not valid!"));

        Optional.ofNullable(resultData.getOrDefault("returnCode", null))
                .map(rs -> ((int)rs == 0) ? true : null)
                .orElseThrow(() -> new ProcessorResultException("Expected result is not 0"));
    }

    /***
     * Update result based on processing result
     * @param processing the instance to be eventually updated
     * @param resultPath the final path to be saved
     */
    @Transactional
    public void updateResult(Processing processing, String resultPath){
        Optional.ofNullable(resultPath)
                .map(rp -> processingService.updateProcessingFinishedOK(processing, rp))
                .orElseGet(() -> processingService.updateProcessingFinishedError(processing));
    }

    /***
     * Start processing
     * @param processingModel the processing model info with informations coming from client
     * @param processing a processing instance (with persisted local informations)
     *
     */
    @Async(value = "processCallerExecutor")
    public String startProcessing(ProcessingModel processingModel, Processing processing) {
        try {
            String jsonData = objectMapper.writeValueAsString(processingModel.getProcessingInputData());
            String result = callRpcServer(jsonData, processorMethodName);

            HashMap<String, Object> resultMap = objectMapper.readValue(result, HashMap.class);
            validateResult(resultMap);
            String outPath = zipperService.zipFileWithRandomName(
                    processorOuptutFolder.concat((String)resultMap.get("outPath")),
                    downloadOutputFolder
            );
            updateResult(processing, outPath);
            return outPath;
        } catch (XMLRPCException | IOException | ProcessorResultException ex){
            ex.printStackTrace();
            updateResult(processing, null);
            return null;
        }
    }
}
