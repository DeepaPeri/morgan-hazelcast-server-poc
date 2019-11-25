package com.hazelcast.withzookeeper.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hazelcast.withzookeeper.entities.StockPrice;

@Repository
public class StockPriceDAOImpl implements StockPriceDAO{
	
	@Autowired
    private EntityManager entityManager;
	
	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Override
	public StockPrice getStock(String stockName) {
		Session session = getSession();

		Query<StockPrice> query = session.createQuery("from StockPrice s where UPPER(s.stockName)=UPPER(:stockName)");
		query.setParameter("stockName", stockName);

		StockPrice stockPrice = query.getSingleResult();

		return stockPrice;
	}
	
	@Override
	public float getLatestPrice(String stockName) {
		return getStock(stockName).getPrice();
	}
	
	@Override
	public List<StockPrice> getStocks(){
		
		List<StockPrice> listStockPrice = null;
		
		//getCurrent session
		Session session = getSession();
		
		Query<StockPrice> query = session.createQuery("from StockPrice",StockPrice.class);
		
		listStockPrice = query.getResultList();
		
		return listStockPrice;
		
	}
	
	@Override
	public StockPrice saveOrUpdateStock(StockPrice theStock) {
		
		//getCurrent session
		Session session = getSession();
		
		session.saveOrUpdate(theStock);
		
		return theStock;
	}
	
	@Override
	public StockPrice deleteStock(String stockName) {
		Session session = getSession();
		StockPrice stock = getStock(stockName);
		
		Query theQuery = 
				session.createQuery("delete from StockPrice s where UPPER(s.stockName)=UPPER(:stock_Name)");
		theQuery.setParameter("stock_Name", stockName);
		
		theQuery.executeUpdate();
		return stock;
	}
}
