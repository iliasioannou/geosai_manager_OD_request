package it.planetek.marinecmems.managerod.processor.services.exctractors;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/10/17.
 */
@Service
public class ProductExtractor {

    private List<String> humanLabels = Arrays.asList("Sea Surface Temperature",
            "Chlorophille",
            "Water Temperature",
            "Turbidity");

    private List<String> legendLabels = Arrays.asList(
            "SST",
            "CHL",
            "TUR",
            "WT"
    );

    private Map<String, String> humanStringLabels;
    private Map<String, String> legendStringLabels;


    /***
     * Fill private variable with legend/hum labels
     */
    public ProductExtractor() {
        List<String> productLabels = extractProductType("15");


        humanStringLabels = IntStream.range(0, Math.min(productLabels.size(), humanLabels.size()))
                .mapToObj(i -> productLabels.get(i) + ":" + humanLabels.get(i))
                .map(elem -> elem.split(":"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));

        legendStringLabels = IntStream.range(0, Math.min(productLabels.size(), legendLabels.size()))
                .mapToObj(i -> productLabels.get(i) + ":" + legendLabels.get(i))
                .map(elem -> elem.split(":"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }


    /***
     * Exctract product labels
     * @param pds the requested products
     * @return a list of parsed product labels
     */
    private List<String> extractProductType(String pds) {
        List<String> productStrings = new ArrayList<>();
        int products = Integer.valueOf(pds);
        if (!((products & 1) == 0)) productStrings.add("SST");
        if (!((products & 2) == 0)) productStrings.add("CHL");
        if (!((products & 4) == 0)) productStrings.add("WT");
        if (!((products & 8) == 0)) productStrings.add("TU");
        return productStrings;
    }

    /***
     * Map product type to label (legend or human based labels)
     * @param product the product to be mapped over
     * @param typeOfConversion the type of requested mapping
     * @return the labels to be used
     */
    public List<String> mapProductStringsToProductLabels(String product, String typeOfConversion) {
        List<String> products = extractProductType(product);
        if (typeOfConversion.equals("human")) {
            return humanStringLabels
                    .entrySet()
                    .stream()
                    .filter(el -> products.contains(el.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        if (typeOfConversion.equals("legend")) {
            return legendStringLabels
                    .entrySet()
                    .stream()
                    .filter(el -> products.contains(el.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
