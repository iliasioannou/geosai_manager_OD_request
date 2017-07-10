package it.planetek.marinecmems.managerod.processor.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.processor.services.exctractors.HumanReadbleExctractor;
import it.planetek.marinecmems.managerod.processor.services.exctractors.HumanReadbleExctractorImpl;
import it.planetek.marinecmems.managerod.processor.services.exctractors.ProductExtractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Francesco Bruni <bruni@planetek.it> on 7/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HumanReadbleExctractorTest {

    @InjectMocks
    private HumanReadbleExctractor humanReadbleExctractor = new HumanReadbleExctractorImpl();

    @Mock
    private ProcessorParamValidatorService processorParamValidatorService;

    @Mock
    private ProductExtractor productExtractor;

    private Date mockDate;

    @Before
    public void setUp() throws ProcessingInputParamsException {
        MockitoAnnotations.initMocks(this);
        when(processorParamValidatorService.validateAoi(anyString())).thenReturn("aoi");
        when(processorParamValidatorService.validateProduct(anyString())).thenReturn("aoi");
        when(processorParamValidatorService.validateDates(anyListOf(Date.class))).thenReturn(Arrays.asList(new Date(), new Date()));
        when(processorParamValidatorService.validateDates(Arrays.asList(mockDate))).thenThrow(new ProcessingInputParamsException("Wrong dates"));

        when(processorParamValidatorService.validateAoi(null)).thenThrow(new ProcessingInputParamsException("AOI null"));
        when(processorParamValidatorService.validateProduct(null)).thenThrow(new ProcessingInputParamsException("product null"));
        when(processorParamValidatorService.validateDates(null)).thenThrow(new ProcessingInputParamsException("date is null"));;
        when(processorParamValidatorService.validateDates(Arrays.asList(new Date()))).thenThrow(new ProcessingInputParamsException("date size"));
        when(productExtractor.mapProductStringsToProductLabels("15", "human")).thenReturn(Arrays.asList("Chlorophille"));
        when(productExtractor.mapProductStringsToProductLabels("1", "human")).thenReturn(Arrays.asList("Sea surface temperature"));

    }
    @Test
    public void extractAoITest() throws Exception {
        Assert.assertEquals(humanReadbleExctractor.extractAoI("1"), "Italy");
        Assert.assertEquals(humanReadbleExctractor.extractAoI("2"), "Greece");
        Assert.assertEquals(humanReadbleExctractor.extractAoI("10"), "Greece and Italy");
    }

    @Test(expected = ProcessingInputParamsException.class)
    public void extractAoIErrorTest() throws Exception {
        humanReadbleExctractor.extractAoI(null);
    }

    @Test
    public void extractProductTest() throws Exception {
        Assert.assertTrue(humanReadbleExctractor.extractProduct("15").contains("Chlorophille"));
        Assert.assertTrue(humanReadbleExctractor.extractProduct("1").contains("Sea surface temperature"));
        Assert.assertFalse(humanReadbleExctractor.extractProduct("1").contains("Chlorophille"));
    }

    @Test(expected = ProcessingInputParamsException.class)
    public void extractProductErrorTest() throws Exception {
        humanReadbleExctractor.extractProduct(null);
    }

    @Test
    public void extractDatesTest() throws Exception {
        Date startDate = new Date();
        Date endDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");

        String expected = "from ".concat(Stream.of(startDate, endDate).map(simpleDateFormat::format).collect(Collectors.joining(" to ")));
        Assert.assertEquals(expected, humanReadbleExctractor.extractDates(Arrays.asList(startDate, endDate)));
    }

    @Test(expected = ProcessingInputParamsException.class)
    public void extractDatesErrorTest() throws Exception {
        humanReadbleExctractor.extractDates(null);

    }

    @Test(expected = ProcessingInputParamsException.class)
    public void extractDatesErrorSizeTest() throws Exception {
        humanReadbleExctractor.extractDates(Arrays.asList(mockDate));

    }

}