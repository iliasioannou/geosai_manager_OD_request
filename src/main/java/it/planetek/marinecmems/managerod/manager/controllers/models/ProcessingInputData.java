package it.planetek.marinecmems.managerod.manager.controllers.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Francesco Bruni <bruni@planetek.it> on 7/5/17.
 */
public class ProcessingInputData {

    private final String procType = "custom";
    @NotNull
    private List<String> dates;
    @NotBlank
    @NotNull
    private String aoi;
    @NotBlank
    @NotNull
    private String products;

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

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getProcType() {
        return procType;
    }
}
