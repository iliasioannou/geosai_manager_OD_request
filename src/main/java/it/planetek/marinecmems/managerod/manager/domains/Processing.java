package it.planetek.marinecmems.managerod.manager.domains;

import it.planetek.marinecmems.managerod.manager.domains.constants.StatusConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@Entity
public class Processing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "processing_data_id")
    private ProcessingData processingData;


    private int status = StatusConstants.TODO;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd H:M:s")
    private Date timestampRequest;

    @Version
    @DateTimeFormat(pattern = "yyyy-mm-dd H:M:s")
    private Date lastUpdate;

    private String resultPath;

    public Processing() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
