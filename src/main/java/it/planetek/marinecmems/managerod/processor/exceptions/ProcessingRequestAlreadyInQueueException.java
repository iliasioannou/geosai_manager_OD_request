package it.planetek.marinecmems.managerod.processor.exceptions;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/7/17.
 */
public class ProcessingRequestAlreadyInQueueException extends Exception {

    public ProcessingRequestAlreadyInQueueException(String message) {
        super(message);
    }
}
