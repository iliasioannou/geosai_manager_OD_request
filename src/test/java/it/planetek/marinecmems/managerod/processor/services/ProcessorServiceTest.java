package it.planetek.marinecmems.managerod.processor.services;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.mailsender.services.MailService;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingInputData;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import it.planetek.marinecmems.managerod.processor.services.exctractors.ProductExtractor;
import it.planetek.marinecmems.managerod.processor.utils.Copier;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by Francesco Bruni - <bruni.planetek.it> on 7/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessorServiceTest {

    @Mock
    private XMLRPCClient client;

    @Mock
    private Zipper zipper;

    @Mock
    private ProcessingService processingService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private ProcessorServiceImpl processorService = new ProcessorServiceImpl();

    @Mock
    private ProcessorParamValidatorService processorParamValidatorService;

    @Mock
    private Copier copierService;

    @Mock
    private ProductExtractor productExtractor;


    private ProcessingModel processingModel;


    @Before
    public void setUp() throws IOException, XMLRPCException, ProcessingInputParamsException {
        Processing savedProcessing = new Processing();
        savedProcessing.setResultPath("ciccio.zip");

        MockitoAnnotations.initMocks(this);
        when(zipper.zipFileWithRandomName(anyString(), anyString())).thenReturn("aaa.zip");
        when(processingService.updateProcessingFinishedOK(any(Processing.class), anyString())).thenReturn(savedProcessing);
        when(processingService.updateProcessingFinishedError(any(Processing.class))).thenReturn(savedProcessing);
        when(mailService.sendMailEnqueuedRequest(any(Processing.class))).thenReturn("ok");
        when(mailService.sendMailFailedRequest(any(Processing.class))).thenReturn("ok");
        when(mailService.sendMailSucceedRequest(any(Processing.class))).thenReturn("ok");
        when(processorParamValidatorService.validateAoi(anyString())).thenReturn("aoi");
        when(processorParamValidatorService.validateProduct(anyString())).thenReturn("aoi");
        when(processorParamValidatorService.validateDates(anyListOf(Date.class))).thenReturn(Arrays.asList(new Date(), new Date()));
        when(productExtractor.mapProductStringsToProductLabels(anyString(), anyString())).thenReturn(Arrays.asList("SST"));
        when(copierService.copyFileInFolder(anyString(), anyString())).thenReturn("outPath");

        ReflectionTestUtils.setField(processorService, "processorMethodName", "execute");
        ReflectionTestUtils.setField(processorService, "processorOuptutFolder", "output");
        ReflectionTestUtils.setField(processorService, "downloadOutputFolder", "/download");
        ReflectionTestUtils.setField(processorService, "legendaFolder", "/legenda");
        processingModel = new ProcessingModel();
        processingModel.setUserEmail("data");

        ProcessingInputData processingInputData = new ProcessingInputData();
        processingInputData.setAoi("1");
        processingInputData.setProducts("15");
        processingInputData.setDates(Arrays.asList("2017-02-01", "2017-02-01"));

        processingModel.setProcessingInputData(processingInputData);
   }

    @Test
    public void startProcessingTest() throws XMLRPCException, ProcessingInputParamsException {
        when(client.call(anyString(), anyString())).thenReturn("{\"returnCode\":0, \"outPath\":\"/blablabla\"}");
        Processing processing = new Processing();
        ProcessingData processingInputData = new ProcessingData();
        processing.setProcessingData(processingInputData);
        Processing resultProcessing = processorService.startProcessing(processingModel, processing);
        Assert.assertEquals(resultProcessing.getResultPath(), "ciccio.zip");
    }

}
