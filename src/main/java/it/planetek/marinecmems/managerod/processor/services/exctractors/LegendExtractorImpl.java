package it.planetek.marinecmems.managerod.processor.services.exctractors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/10/17.
 */
@Service("LegendExctractor")
public class LegendExtractorImpl implements LegendExctractor {

    @Autowired
    private ProductExtractor productExtractor;


    /***
     * Extract legend names by provided product
     * @param product the product whose related legend label will be associated to
     * @return the list of legend labels
     */
    @Override
    public List<String> exctractLegendNameByProduct(String product) {
        return productExtractor.mapProductStringsToProductLabels(product, "legend");
    }
}
