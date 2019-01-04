package org.asck.config;

import org.asck.service.client.IFeedbackClientService;
import org.asck.service.client.impl.FeedbackClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class RootConfiguration {

	@Value("${service.base.path:http://localhost:8080/v1/feedback}")
    private String serviceBasePath;
    
    @Bean
    public IFeedbackClientService createService() {
    	return new FeedbackClientService(this.serviceBasePath);
    }
	
}
