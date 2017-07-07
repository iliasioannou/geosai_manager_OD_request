package it.planetek.marinecmems.managerod.mailsender.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.mailsender.utils.Sender;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.processor.services.HumanReadbleExctractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/5/17.
 */
@Service("MailService")
public class MailServiceImpl implements MailService{

    @Value("${mail.from}")
    private String fromAddress;

    @Value("${mail.subject}")
    private String subject;

    @Value("${download.downloadBasePath}")
    private String downloadBasePath;

    @Autowired
    private HumanReadbleExctractor humanReadbleExctractor;

    @Autowired
    private Sender sender;


    /***
     * Send mail and configure injected values read by properties
     * @param toAddress the address the email will be sent to
     * @param text the text to be sent
     */
    private void sendMail(String toAddress, String text){
        sender.sendMail(fromAddress, subject, toAddress, text);
    }

    /***
     * Send mail when a new job has been enqueued
     * @param processing the processing instance whose enqueued job refers to
     */
    @Override
    public String sendMailEnqueuedRequest(Processing processing) throws ProcessingInputParamsException {
        String text = "<p>Dear "
                .concat(processing.getUserEmail())
                .concat(",</p>")
                .concat("<p>your request has been enqueued and will be processed as soon as possible.</p>")
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("<br /><br /><p>Best,<p>")
                .concat("<p>CMEMS Marine Team</p>");
        sendMail(processing.getUserEmail(), text);
        return text;
    }

    /***
     * Helper method to generate the provided request parameters
     * @param processingData the instance which whose provided data refer to
     * @return the entire string
     */
    private String generateInputParamString(ProcessingData processingData) throws ProcessingInputParamsException {


        return "<div>".concat(Stream.of(
                "<p>Provided request parameters:</p>",
                "<ul>",
                "<li><b>Area of Interest</b>: ".concat(humanReadbleExctractor.extractAoI(processingData.getAoi())).concat("</li>"),
                "<li><b>Products</b>: ".concat(humanReadbleExctractor.extractProduct(processingData.getProduct())).concat("</li>"),
                "<li><b>Dates</b>: ".concat(humanReadbleExctractor.extractDates(Arrays.asList(processingData.getStartDate(), processingData.getEndDate()))).concat("</li>"),
                "</ul>"
        ).collect(Collectors.joining("\n"))).concat("</div>");


    }

    /***
     * Send mail after job has been completed.
     * @param processing the processing whose instance refers to
     * @return the sent text
     */
    @Override
    public String sendMailSucceedRequest(Processing processing) throws ProcessingInputParamsException {
        String text = "<p>Dear "
                .concat(processing.getUserEmail())
                .concat(",</p>")
                .concat("<p>your request has been processed and result is available at this ")
                .concat("<a href=")
                .concat(downloadBasePath)
                .concat(processing.getResultPath().replace("shared", "") + ">link</a>.")
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("<br /><br /><p>Best,<p>")
                .concat("<p>CMEMS Marine Team</p>");
        sendMail(processing.getUserEmail(), text);
        return text;
    }

    /***
     * Send mail after job has been completed with errors.
     * @param processing the processing whose instance refers to
     * @return the sent text
     */
    @Override
    public String sendMailFailedRequest(Processing processing) throws ProcessingInputParamsException {
        String text = "<p>Dear "
                .concat(processing.getUserEmail())
                .concat(",</p>")
                .concat("<p>an error occured while serving your request.<p>")
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("<br /><br /><p>Best,<p>")
                .concat("<p>CMEMS Marine Team</p>");
        sendMail(processing.getUserEmail(), text);
        return text;
    }
}
