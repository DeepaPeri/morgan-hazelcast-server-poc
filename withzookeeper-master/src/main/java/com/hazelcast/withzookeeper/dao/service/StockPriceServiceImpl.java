package com.hazelcast.withzookeeper.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.withzookeeper.dao.StockPriceDAO;
import com.hazelcast.withzookeeper.entities.StockPrice;

@Service
public class StockPriceServiceImpl implements StockPriceService {

	@Autowired
	private StockPriceDAO stockPriceDAO;
	
	@Transactional
	public float getLatestPrice(String stockName) {
		return stockPriceDAO.getLatestPrice(stockName);
	}

	@Transactional
	public List<StockPrice> getStocks() {
		return stockPriceDAO.getStocks();
	}
	@Transactional
	public StockPrice saveOrUpdateStock(StockPrice theStock) {
		return stockPriceDAO.saveOrUpdateStock(theStock);
	}

	@Transactional
	public StockPrice deleteStock(String stockName) {
		return stockPriceDAO.deleteStock(stockName);
	}

	@Transactional
	public StockPrice getStock(String stockName) {
		return stockPriceDAO.getStock(stockName);
	}

}
