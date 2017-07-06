package it.planetek.marinecmems.managerod.mailsender.services;

import it.planetek.marinecmems.managerod.manager.domains.Processing;

/**
 * Created by Francesco on 7/5/17.
 */
public interface MailService {
    String sendMailEnqueuedRequest(Processing processing);

    String sendMailSucceedRequest(Processing processing);

    String sendMailFailedRequest(Processing processing);
}
