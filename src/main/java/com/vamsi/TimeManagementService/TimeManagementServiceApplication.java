package com.vamsi.TimeManagementService;

import com.vamsi.TimeManagementService.Configuration.ApplicationConfiguration;
import com.vamsi.TimeManagementService.dataaccess.UserStore;
import com.vamsi.TimeManagementService.databasemodels.UserDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication
public class TimeManagementServiceApplication {



	public static void main(String[] args) {
		SpringApplication.run(TimeManagementServiceApplication.class, args);
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		UserStore userStore = (UserStore) context.getBean("userStore") ;
		UserDocument userDocument = new UserDocument();
		userDocument.setEmail("alskf");
		userDocument.setEmployeeCode("alsiejf");
		userDocument.setFirstName("alsifje");
		userDocument.setUserName("vamsi");
		userStore.insert(userDocument);
		UserDocument userDocument1 = userStore.findByUsername(userDocument);
		System.out.println(userDocument1);
	}

}
