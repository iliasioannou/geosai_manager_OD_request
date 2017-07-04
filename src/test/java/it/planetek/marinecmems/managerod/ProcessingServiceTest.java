package it.planetek.marinecmems.managerod;

import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import org.junit.Test;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
public class ProcessingServiceTest {

    @Test
    public void startProcessingTest(){
        ProcessingModel processingModel = new ProcessingModel();
        processingModel.setUserEmail("francesco@francesco.it");

    }
}
