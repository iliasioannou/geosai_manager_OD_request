package it.planetek.marinecmems.managerod.manager.controllers.models;

import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
public class ProcessingModel {

    @Email
    private String userEmail;

    @NotNull
    @Valid
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
