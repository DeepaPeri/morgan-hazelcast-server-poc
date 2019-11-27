package com.hazelcast.withzookeeper;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spi.properties.GroupProperty;
import com.hazelcast.withzookeeper.caching.StockPriceMapStore;
import com.hazelcast.zookeeper.ZookeeperDiscoveryProperties;
import com.hazelcast.zookeeper.ZookeeperDiscoveryStrategyFactory;
import static com.hazelcast.config.EvictionPolicy.LRU;
import static com.hazelcast.config.EvictionPolicy.LFU;

@Configuration
public class HazelcastConfig {
	@Autowired
	private StockPriceMapStore mapStore;
	
	@Bean
	public HazelcastInstance getHazelcastInstance(Config configuration) {
		return Hazelcast.newHazelcastInstance(configuration);
	}
	
	@Bean
	public Config getHazelcastConfig(DiscoveryStrategyConfig discoveryStrategyConfig) {
		Config config = new Config();
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.setProperty(GroupProperty.DISCOVERY_SPI_ENABLED.getName(), "true");
		config.getNetworkConfig().getJoin().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);
		configureStocksMap(config);
		config.getManagementCenterConfig().setEnabled(true);
		config.getManagementCenterConfig().setUrl("http://localhost:8090/hazelcast-mancenter");
		return config;
	}
	
	@Bean
	public DiscoveryStrategyConfig getDiscoveryConfig(
			@Value("${zookeeper_ip_address}") String zookeeperUrl, 
			@Value("${zookeeper_port}") String zookeeperPort, 
			@Value("${zookeeper_path}") String zookeeperPath,
			@Value("${hazelcast_group}") String hazelcastGroup) {
		DiscoveryStrategyConfig discoverStrategyConfig = new DiscoveryStrategyConfig(new ZookeeperDiscoveryStrategyFactory());
		discoverStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_URL.key(), zookeeperUrl + ":" + zookeeperPort);
		discoverStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_PATH.key(), zookeeperPath);
		discoverStrategyConfig.addProperty(ZookeeperDiscoveryProperties.GROUP.key(), hazelcastGroup);
		
		return discoverStrategyConfig;
	}
	
	public void configureStocksMap(Config configuration) {
		MapConfig stocksMapConfig = configuration.getMapConfig("stocksMap");
		//stocksMapConfig.setInMemoryFormat(InMemoryFormat.NATIVE);
		// Eviction policy is set to Least Recently Used
		stocksMapConfig.setEvictionPolicy(LRU);
		stocksMapConfig.setEvictionPolicy(LFU);
		MapStoreConfig stocksMapStoreConfig = stocksMapConfig.getMapStoreConfig();
		
		stocksMapStoreConfig
			.setEnabled(true)
			.setImplementation(mapStore);
	}
}
