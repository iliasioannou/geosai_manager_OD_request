package it.planetek.marinecmems.managerod.manager.controllers.models;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Francesco Bruni <bruni@planetek.it> on 7/5/17.
 */
public class ProcessingInputData {

    @NotNull
    private List<String> dates;

    @NotBlank
    @NotNull
    private String aoi;

    @NotBlank
    @NotNull
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
