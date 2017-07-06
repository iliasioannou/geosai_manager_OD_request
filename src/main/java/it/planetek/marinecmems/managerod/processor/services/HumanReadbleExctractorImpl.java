package it.planetek.marinecmems.managerod.processor.services;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
@Service("HumanReadbleExctractor")
public class HumanReadbleExctractorImpl  implements HumanReadbleExctractor {

    @Override
    public String extractAoI(String aoi) {
        switch (aoi){
            case "1":
                return "Italy";
            case "2":
                return "Greece";
            default:
                return "Greece and Italy";
        }
    }

    @Override
    public String extractProduct(String aoi) {
        int products = Integer.valueOf(aoi);
        List<String> productLabels = new ArrayList<>();

        if (!((products & 1) == 0)) productLabels.add("Sea surface temperature");
        if (!((products & 2) == 0)) productLabels.add("Chlorophille");
        if (!((products & 4) == 0)) productLabels.add("Water temperature");
        if (!((products & 8) == 0)) productLabels.add("Turbidity");

        return productLabels.stream().collect(Collectors.joining(","));
    }

    @Override
    public String extractDates(List<Date> dates) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/mm/dd");
        String dateStrings = dates.stream().map(simpleDateFormat::format).collect(Collectors.joining(" to "));
        return "from ".concat(dateStrings);
    }

}
