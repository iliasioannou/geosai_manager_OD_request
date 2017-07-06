package it.planetek.marinecmems.managerod.mailsender.services;

import it.planetek.marinecmems.managerod.mailsender.services.utils.Sender;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import it.planetek.marinecmems.managerod.processor.services.HumanReadbleExctractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/5/17.
 */
public class MailServiceImpl implements MailService{

    @Value("${mail.from}")
    private String fromAddress;

    @Value("${mail.subject}")
    private String subject;

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
    public String sendMailEnqueuedRequest(Processing processing) {
        String text = "Dear "
                .concat(processing.getUserEmail())
                .concat(",\n")
                .concat("your request has been enqueued and will be processed as soon as possible.\n")
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("Best,\n")
                .concat("CMEMS Marine Team");
        sendMail(processing.getUserEmail(), text);
        return text;
    }

    /***
     * Helper method to generate the provided request parameters
     * @param processingData the instance which whose provided data refer to
     * @return the entire string
     */
    private String generateInputParamString(ProcessingData processingData){
        return "\n".concat(Stream.of(
                "Provided request parameters:",
                humanReadbleExctractor.extractAoI(processingData.getAoi()),
                humanReadbleExctractor.extractProduct(processingData.getProduct()),
                humanReadbleExctractor.extractDates(Arrays.asList(processingData.getStartDate(), processingData.getEndDate()))
        ).collect(Collectors.joining(" "))).concat("\n");


    }

    /***
     * Send mail after job has been completed.
     * @param processing the processing whose instance refers to
     * @return the sent text
     */
    @Override
    public String sendMailSucceedRequest(Processing processing) {
        String text = "Dear "
                .concat(processing.getUserEmail())
                .concat(",\n")
                .concat("your request has been processed and result is available at this link:\n")
                .concat(processing.getResultPath())
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("Best,\n")
                .concat("CMEMS Marine Team");
        sendMail(processing.getUserEmail(), text);
        return text;
    }

    /***
     * Send mail after job has been completed with errors.
     * @param processing the processing whose instance refers to
     * @return the sent text
     */
    @Override
    public String sendMailFailedRequest(Processing processing) {
        String text = "Dear "
                .concat(processing.getUserEmail())
                .concat(",\n")
                .concat("an error occured while serving your request.\n")
                .concat(generateInputParamString(processing.getProcessingData()))
                .concat("Best\n")
                .concat("CMEMS Marine Team");
        sendMail(processing.getUserEmail(), text);
        return text;
    }
}
