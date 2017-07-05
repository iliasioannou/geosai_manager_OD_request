package it.planetek.marinecmems.managerod;

import de.timroes.axmlrpc.XMLRPCClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
@ComponentScan(basePackages = {"it.planetek.marinecmems.managerod"})
@Order(6)
@EnableAsync
@EnableJpaAuditing
public class ManagerodApplication {

    @Value("${processor.url}")
    private String processorUrl;

	public static void main(String[] args) {
		SpringApplication.run(ManagerodApplication.class, args);
	}


    @Bean(name = "processCallerExecutor")
    public TaskExecutor infobipCallbackTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean(name="client")
    public XMLRPCClient xmlRpcClient() throws MalformedURLException {
	    return new XMLRPCClient(new URL(processorUrl));

    }
}
