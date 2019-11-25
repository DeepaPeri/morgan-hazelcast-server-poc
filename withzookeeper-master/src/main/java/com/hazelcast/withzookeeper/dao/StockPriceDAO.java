package com.hazelcast.withzookeeper.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hazelcast.withzookeeper.entities.StockPrice;

@Repository
public interface StockPriceDAO {
	
	public float getLatestPrice(String stockName);
	public List<StockPrice> getStocks();
	public StockPrice saveOrUpdateStock(StockPrice theStock);
	public StockPrice deleteStock(String stockName);
	public StockPrice getStock(String stockName);
}
