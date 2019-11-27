package com.hazelcast.withzookeeper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.withzookeeper.entities.StockPrice;

@Configuration
@SpringBootApplication
//@ImportResource({"persistence.xml"})
public class WithzookeeperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WithzookeeperApplication.class, args);
		HazelcastInstance server = context.getBean(HazelcastInstance.class);
		
		// load all
		IMap<String, StockPrice> map = server.getMap("stocksMap");
//		// load
//		StockPrice p = map.get("RAYMOND");
//		StockPrice p1 = map.get("igl");
//		// load all keys
//		Set<String> stock_names = map.keySet();
//		
//		System.out.println("Get all keys: " + stock_names);
//        System.out.println("Stock is: " + p1);
		map.delete("IGL");
	}
}
