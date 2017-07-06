package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.ManagerodApplication;
import it.planetek.marinecmems.managerod.mailsender.exceptions.ProcessingInputParamsException;
import it.planetek.marinecmems.managerod.manager.controllers.models.ProcessingModel;
import it.planetek.marinecmems.managerod.manager.domains.Processing;
import it.planetek.marinecmems.managerod.manager.services.ProcessingService;
import it.planetek.marinecmems.managerod.processor.services.ProcessorService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManagerodApplication.class)
@WebAppConfiguration
@EnableWebMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ProcessingControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Mock
    private ProcessingService processingService;

    @Mock
    private ProcessorService processorService;

    @Before
    public void  setUp() throws ProcessingInputParamsException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void startProcessingValidationErrorTest() throws Exception {
        String data = "{\n" +
                "    \"userEmail\": \"ciccio\",\n" +
                "    \"processingInputData\":{\n" +
                "        \"dates\":[\"2017-06-20\", \"2017-06-23\"],\n" +
                "        \"aoi\":\"\",\n" +
                "        \"product\":\"1\"\n" +
                "    }\n" +
                "}";
        mockMvc.perform(post("/processings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

    }

    @Test
    public void startProcessingValidationErrorProductTest() throws Exception {
        String data = "{\n" +
                "    \"userEmail\": \"ciccio@ciccio.it\",\n" +
                "    \"processingInputData\":{\n" +
                "        \"dates\":[\"2017-06-20\", \"2017-06-23\"],\n" +
                "        \"aoi\":\"\",\n" +
                "        \"product\":\"1\"\n" +
                "    }\n" +
                "}";
        String aaa = mockMvc.perform(post("/processings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        System.out.print("a");

    }



}