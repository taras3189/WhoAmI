package com.eleks.academy.who_am_i;

import com.eleks.academy.who_am_i.configuration.ContextConfig;
import com.eleks.academy.who_am_i.networking.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ContextConfig.class);

		Server server = applicationContext.getBean(Server.class);


		server.startGame().play();


	}

}
