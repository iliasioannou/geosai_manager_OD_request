package it.planetek.marinecmems.managerod.processor.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;

import java.util.Date;
import java.util.List;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
public interface ProcessorParamValidatorService {

    /***
     * Validate the aoi provided param
     * @param aoi areao of interest
     * @throws ProcessingInputParamsException raised if validation fails
     */
    String validateAoi(String aoi) throws ProcessingInputParamsException;

    /***
     * Validate the dates array
     * @param dates the dates to be used to run the processing
     * @throws ProcessingInputParamsException raised if validation fails
     */
    List<Date> validateDates(List<Date> dates) throws ProcessingInputParamsException;

    /***
     * Validate product value
     * @param product the product param to be used to run the processing
     * @throws ProcessingInputParamsException raised if validation fails
     */
    String validateProduct(String product) throws ProcessingInputParamsException;
}
