package com.hazelcast.withzookeeper.dao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hazelcast.withzookeeper.entities.StockPrice;

@Service
public interface StockPriceService {

	public float getLatestPrice(String stockName);
	public List<StockPrice> getStocks();
	public StockPrice saveOrUpdateStock(StockPrice theStock);
	public StockPrice deleteStock(String stockName);
	public StockPrice getStock(String stockName);
}
