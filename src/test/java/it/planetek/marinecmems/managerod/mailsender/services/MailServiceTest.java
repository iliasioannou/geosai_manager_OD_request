package it.planetek.marinecmems.managerod.mailsender.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.mailsender.utils.Sender;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.processor.services.exctractors.HumanReadbleExctractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @InjectMocks
    private MailService mailService = new MailServiceImpl();

    @Mock
    private Sender sender;

    @Mock
    private HumanReadbleExctractor humanReadbleExctractor;

    private Processing processing;

    @Before
    public void setUp() throws ProcessingInputParamsException {
        MockitoAnnotations.initMocks(this);
        when(sender.sendMail(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        when(humanReadbleExctractor.extractAoI(anyString())).thenReturn("1");
        when(humanReadbleExctractor.extractProduct(anyString())).thenReturn("15");
        when(humanReadbleExctractor.extractDates(anyListOf(Date.class))).thenReturn("1");
        ReflectionTestUtils.setField(mailService, "downloadBasePath", "downloadPath");
        processing = new Processing();
        ProcessingData processingData = new ProcessingData();
        processingData.setAoi("1");
        processingData.setEndDate(new Date());
        processingData.setStartDate(new Date());
        processingData.setProduct("1");
        processing.setResultPath("file.zip");
        processing.setProcessingData(processingData);
        processing.setUserEmail("ddd@ddd.it");
    }

    @Test
    public void sendMailEnqueuedRequest() throws Exception {
        String text = mailService.sendMailEnqueuedRequest(processing);
        Assert.assertTrue(text.contains("ddd@ddd.it"));
        Assert.assertTrue(text.contains("your request has been enqueued"));
        Assert.assertTrue(text.contains("Provided request parameters:"));
    }

    @Test
    public void sendMailSucceedRequest() throws Exception {
        String text = mailService.sendMailSucceedRequest(processing);
        Assert.assertTrue(text.contains("ddd@ddd.it"));
        Assert.assertTrue(text.contains("your request has been processed"));
        Assert.assertTrue(text.contains("Provided request parameters:"));
    }

    @Test
    public void sendMailFailedRequest() throws Exception {
        String text = mailService.sendMailFailedRequest(processing);
        Assert.assertTrue(text.contains("ddd@ddd.it"));
        Assert.assertTrue(text.contains("an error occured while serving"));
        Assert.assertTrue(text.contains("Provided request parameters:"));
    }

}