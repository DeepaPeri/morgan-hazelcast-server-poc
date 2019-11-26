package com.hazelcast.withzookeeper.caching;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.MapStore;
import com.hazelcast.withzookeeper.entities.StockPrice;

@Component
public class StockPriceMapStore implements MapStore<String, StockPrice>{

	@Autowired
    private EntityManager entityManager;
	
	@Transactional
	private Session getSession() {
		return entityManager.getEntityManagerFactory().createEntityManager().unwrap(Session.class);
	}
	
	@Override
	public StockPrice load(String stockName) {
		Session session = getSession();

		Query<StockPrice> query = session.createQuery("from StockPrice s where UPPER(s.stockName)=UPPER(:stockName)");
		query.setParameter("stockName", stockName);

		StockPrice stockPrice = query.getSingleResult();

		return stockPrice;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, StockPrice> loadAll(Collection<String> stockNames) {
		Session session = getSession();
		Query<StockPrice> query = session.createQuery("from StockPrice s where UPPER(s.stockName) in UPPER(:stockNames)");
		query.setParameterList(0, stockNames);
		
		List<StockPrice> listStockPrice = query.getResultList();
		
		return listStockPrice.stream().collect(Collectors.toMap(StockPrice::getStockName, Function.identity()));
	}

	@Override
	public Iterable<String> loadAllKeys() {
		Session session = getSession();
		Query<String> query = session.createQuery("select stockName from StockPrice", String.class);
		List<String> listStockPrice = query.getResultList();
		
		return listStockPrice;
	}

	@Override
	public void store(String key, StockPrice value) {
		// TODO Auto-generated method stub
		System.out.println("The store method::" + key + "::" + value);
	}

	@Override
	public void storeAll(Map<String, StockPrice> map) {
		// TODO Auto-generated method stub
		System.out.println("The storeAll method::" + map);
	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		System.out.println("The delete method::" + key);
	}

	@Override
	public void deleteAll(Collection<String> keys) {
		// TODO Auto-generated method stub
		System.out.println("The deleteAll method::" + keys);
	}

}
