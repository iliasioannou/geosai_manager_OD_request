package it.planetek.marinecmems.managerod.manager.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.manager.domains.constants.StatusConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.util.Date;

/**
 * Created by francesco on 7/4/17.
 */
public class ProcessingResource extends ResourceSupport{
    private String userEmail;
    private ProcessingData processingData;
    private int status = StatusConstants.TODO;
    private Date timestampRequest;
    private Date lastUpdate;
    private String resultPath;

    public ProcessingResource(String userEmail, ProcessingData processingData, int status, Date timestampRequest, Date lastUpdate, String resultPath) {
        this.userEmail = userEmail;
        this.processingData = processingData;
        this.status = status;
        this.timestampRequest = timestampRequest;
        this.lastUpdate = lastUpdate;
        this.resultPath = resultPath;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ProcessingData getProcessingData() {
        return processingData;
    }

    public void setProcessingData(ProcessingData processingData) {
        this.processingData = processingData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimestampRequest() {
        return timestampRequest;
    }

    public void setTimestampRequest(Date timestampRequest) {
        this.timestampRequest = timestampRequest;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}
