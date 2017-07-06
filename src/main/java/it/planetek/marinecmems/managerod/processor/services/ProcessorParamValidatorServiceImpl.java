package it.planetek.marinecmems.managerod.processor.services;

import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
public class ProcessorParamValidatorServiceImpl implements ProcessorParamValidatorService {
    @Override
    public String validateAoi(String aoi) throws ProcessingInputParamsException {
        return Optional.ofNullable(aoi).orElseThrow(() -> new ProcessingInputParamsException("Set aoi is null!"));
    }

    @Override
    public List<Date> validateDates(List<Date> dates) throws ProcessingInputParamsException {
        Optional.ofNullable(dates)
                .orElseThrow(() -> new ProcessingInputParamsException("Provided dates are null"));
        if (!(dates.size() == 2)) throw new ProcessingInputParamsException("Processing dates array size seems wrong");
        return dates;
    }

    @Override
    public String validateProduct(String product) throws ProcessingInputParamsException {
        return Optional.ofNullable(product)
                .orElseThrow(() -> new ProcessingInputParamsException("Provided product is null"));
    }
}
