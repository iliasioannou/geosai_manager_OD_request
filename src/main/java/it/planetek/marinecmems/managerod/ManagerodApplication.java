package it.planetek.marinecmems.managerod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@ComponentScan(basePackages = {"it.planetek.marinecmems.managerod"})
@EnableAsync
public class ManagerodApplication {

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
}
