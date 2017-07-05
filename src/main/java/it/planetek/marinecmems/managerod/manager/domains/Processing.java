package it.planetek.marinecmems.managerod.manager.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.planetek.marinecmems.managerod.manager.domains.constants.StatusConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
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
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestampRequest;

    @LastModifiedDate
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
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
