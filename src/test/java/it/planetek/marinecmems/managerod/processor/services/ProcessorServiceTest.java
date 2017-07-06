package it.planetek.marinecmems.managerod.processor.services;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingInputData;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import it.planetek.marinecmems.managerod.manager.services.ProcessingServiceImpl;
import it.planetek.marinecmems.managerod.processor.exceptions.ProcessorResultException;
import it.planetek.marinecmems.managerod.processor.services.ProcessorService;
import it.planetek.marinecmems.managerod.processor.services.ProcessorServiceImpl;
import it.planetek.marinecmems.managerod.processor.utils.Zipper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessorServiceTest {

    @Mock
    private XMLRPCClient client;

    @Mock
    private Zipper zipper;

    @Mock
    private ProcessingService processingService;


    @InjectMocks
    private ProcessorServiceImpl processorService = new ProcessorServiceImpl();


    private ProcessingModel processingModel;


    @Before
    public void setUp() throws MalformedURLException, XMLRPCException {
        Processing savedProcessing = new Processing();
        savedProcessing.setResultPath("ciccio.zip");

        MockitoAnnotations.initMocks(this);
        when(zipper.zipFileWithRandomName(anyString(), anyString())).thenReturn("aaa.zip");
        when(processingService.updateProcessingFinishedOK(any(Processing.class), anyString())).thenReturn(savedProcessing);
        when(processingService.updateProcessingFinishedError(any(Processing.class))).thenReturn(savedProcessing);

        ReflectionTestUtils.setField(processorService, "processorMethodName", "execute");
        ReflectionTestUtils.setField(processorService, "processorOuptutFolder", "output");
        ReflectionTestUtils.setField(processorService, "downloadOutputFolder", "/download");

        processingModel = new ProcessingModel();
        processingModel.setUserEmail("data");

        ProcessingInputData processingInputData = new ProcessingInputData();
        processingInputData.setAoi("1");
        processingInputData.setProduct("15");
        processingInputData.setDates(Arrays.asList("2017-02-01", "2017-02-01"));

        processingModel.setProcessingInputData(processingInputData);
   }

    @Test
    public void startProcessingTest() throws XMLRPCException {
        when(client.call(anyString(), anyString())).thenReturn("{\"returnCode\":0, \"outPath\":\"/blablabla\"}");
        String path = processorService.startProcessing(processingModel, new Processing());
        Assert.assertEquals(path, "aaa.zip");
    }


}
