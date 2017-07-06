package it.planetek.marinecmems.managerod.processor.services;

import java.util.Date;
import java.util.List;

/**
 * Created by Francesco Bruni on 7/5/17.
 */
public interface HumanReadbleExctractor {

    /***
     * Exctract the Area of Interest
     * @param aoi the aoi whose human understable name needs to be extracted by
     * @return the human representation
     */
    String extractAoI(String aoi);


    /***
     * Exctract the Product
     * @param aoi the aoi whose human understable product needs to be extracted by
     * @return the human representation
     */
    String extractProduct(String aoi);


    /***
     * Exctract the dates
     * @param dates the aoi whose human understable dates needs to be extracted by
     * @return the human representation
     */
    String extractDates(List<Date> dates);


}
