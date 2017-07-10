package it.planetek.marinecmems.managerod.processor.services.exctractors;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.processor.services.ProcessorParamValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
@Service("HumanReadbleExctractor")
public class HumanReadbleExctractorImpl  implements HumanReadbleExctractor {

    @Autowired
    private ProcessorParamValidatorService processorParamValidatorService;


    @Autowired
    private ProductExtractor productExtractor;

    /***
     * Exctract the Area of Interest
     * @param aoi the aoi whose human understable name needs to be extracted by
     * @return the human representation
     */
    @Override
    public String extractAoI(String aoi) throws ProcessingInputParamsException {
        processorParamValidatorService.validateAoi(aoi);
        switch (aoi){
            case "1":
                return "Italy";
            case "2":
                return "Greece";
            default:
                return "Greece and Italy";
        }
    }

    /***
     * Exctract the Product
     * @param product the aoi whose human understable product needs to be extracted by
     * @return the human representation
     */
    @Override
    public String extractProduct(String product) throws ProcessingInputParamsException {
        processorParamValidatorService.validateProduct(product);
        List<String> productLabels = productExtractor.mapProductStringsToProductLabels(product, "human");
        return productLabels.stream().collect(Collectors.joining(","));
    }

    /***
     * Exctract the dates
     * @param dates the aoi whose human understable dates needs to be extracted by
     * @return the human representation
     */
    @Override
    public String extractDates(List<Date> dates) throws ProcessingInputParamsException {
        processorParamValidatorService.validateDates(dates);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/mm/dd");
        String dateStrings = dates.stream().map(simpleDateFormat::format).collect(Collectors.joining(" to "));
        return "from ".concat(dateStrings);
    }

}
