package it.planetek.marinecmems.managerod.manager.controllers.models;

import java.util.List;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
public class ProcessingModel {

    public class ProcessingInputData{

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

    private String userEmail;
    private ProcessingInputData processingInputData;

    public ProcessingModel() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ProcessingInputData getProcessingInputData() {
        return processingInputData;
    }

    public void setProcessingInputData(ProcessingInputData processingInputData) {
        this.processingInputData = processingInputData;
    }
}
