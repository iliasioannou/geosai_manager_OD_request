package it.planetek.marinecmems.managerod.processor.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Francesco Bruni <bruni@planetek.it> on 7/5/17.
 */
public class HumanReadbleExctractorTest {

    @InjectMocks
    private HumanReadbleExctractor humanReadbleExctractor = new HumanReadbleExctractorImpl();

    @Test
    public void extractAoI() throws Exception {
        Assert.assertEquals(humanReadbleExctractor.extractAoI("1"), "Italy");
        Assert.assertEquals(humanReadbleExctractor.extractAoI("2"), "Greece");
        Assert.assertEquals(humanReadbleExctractor.extractAoI("10"), "Greece and Italy");
    }

    @Test
    public void extractProduct() throws Exception {
        Assert.assertTrue(humanReadbleExctractor.extractProduct("15").contains("Chlorophille"));
        Assert.assertTrue(humanReadbleExctractor.extractProduct("1").contains("Sea surface temperature"));
        Assert.assertFalse(humanReadbleExctractor.extractProduct("1").contains("Chlorophille"));
    }

    @Test
    public void extractDates() throws Exception {
        Date startDate = new Date();
        Date endDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd");

        String expected = "from ".concat(Stream.of(startDate, endDate).map(simpleDateFormat::format).collect(Collectors.joining(" to ")));
        Assert.assertEquals(expected, humanReadbleExctractor.extractDates(Arrays.asList(startDate, endDate)));

    }

}