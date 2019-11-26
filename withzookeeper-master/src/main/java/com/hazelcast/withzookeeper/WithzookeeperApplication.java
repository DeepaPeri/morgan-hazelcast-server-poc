package com.hazelcast.withzookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.withzookeeper.entities.StockPrice;

@SpringBootApplication
public class WithzookeeperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WithzookeeperApplication.class, args);
		HazelcastInstance server = context.getBean(HazelcastInstance.class);
		
		IMap<String, StockPrice> map = server.getMap("stocksMap");
		StockPrice p = map.get("RAYMOND");
        
        System.out.println("Stock is: " + p);
	}
}
