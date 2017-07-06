package it.planetek.marinecmems.managerod.processor.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
public class ProcessorParamValidatorServiceTest {

    @InjectMocks
    private  ProcessorParamValidatorService processorParamValidatorService = new ProcessorParamValidatorServiceImpl();

    @Test(expected = ProcessingInputParamsException.class)
    public void validateAoi() throws Exception {
        processorParamValidatorService.validateAoi(null);
    }

    @Test(expected = ProcessingInputParamsException.class)
    public void validateDates() throws Exception {
        processorParamValidatorService.validateDates(Arrays.asList(new Date()));
        processorParamValidatorService.validateDates(null);
    }

    @Test(expected = ProcessingInputParamsException.class)
    public void validateProduct() throws Exception {
        processorParamValidatorService.validateProduct(null);
    }

}