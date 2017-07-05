package it.planetek.marinecmems.managerod.manager.controllers.models;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
public class ProcessingModel {

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
