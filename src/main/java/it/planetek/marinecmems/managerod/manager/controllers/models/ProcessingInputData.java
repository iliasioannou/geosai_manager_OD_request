package it.planetek.marinecmems.managerod.manager.controllers.models;

import java.util.List;

/**
 * Created by francesco on 7/5/17.
 */
public class ProcessingInputData {

    private List<String> dates;
    private String aoi;
    private String product;
    private final String procType = "custom";

    public ProcessingInputData() {
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getAoi() {
        return aoi;
    }

    public void setAoi(String aoi) {
        this.aoi = aoi;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProcType() {
        return procType;
    }
}
