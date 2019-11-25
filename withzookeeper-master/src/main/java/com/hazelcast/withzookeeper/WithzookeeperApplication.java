package com.hazelcast.withzookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.hazelcast.core.HazelcastInstance;

@SpringBootApplication
public class WithzookeeperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WithzookeeperApplication.class, args);
		HazelcastInstance server = context.getBean(HazelcastInstance.class);
		
		System.out.println("server is not null: " + server.getClass().getCanonicalName());
	}

}
