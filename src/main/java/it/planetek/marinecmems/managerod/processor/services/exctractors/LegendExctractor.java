package it.planetek.marinecmems.managerod.processor.services.exctractors;

import java.util.List;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/10/17.
 */
public interface LegendExctractor {

    /***
     * Extract legend names by provided product
     * @param product the product whose related legend label will be associated to
     * @return the list of legend labels
     */
    List<String> exctractLegendNameByProduct(String product);
}
