package com.vamsi.TimeManagementService;

import com.vamsi.TimeManagementService.Configuration.ApplicationConfiguration;
import com.vamsi.TimeManagementService.dataaccess.UserStore;
import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.validation.constraints.Email;

@SpringBootApplication
public class TimeManagementServiceApplication {




	public static void main(String[] args) {
		SpringApplication.run(TimeManagementServiceApplication.class, args);
//		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);



	}

}
