package com.hazelcast.withzookeeper.caching;

import java.util.Collection;



import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
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

		Query<StockPrice> query = session.createQuery("from StockPrice s where UPPER(s.stockName)=UPPER(:stock_Name)");
		query.setParameter("stock_Name", stockName);

		StockPrice stockPrice = query.getSingleResult();
		
		System.out.println("load used in hazelcast");
		return stockPrice;
	}

	
	@Override
	public Map<String, StockPrice> loadAll(Collection<String> stockNames) {
		Session session = getSession();
		Query<StockPrice> query = session.createQuery("from StockPrice s where s.stockName in (:stock_Names)");
		query.setParameterList("stock_Names", stockNames);
		
		List<StockPrice> listStockPrice = query.getResultList();
		System.out.println("loadall used in hazelcast");
		return listStockPrice.stream().collect(Collectors.toMap(StockPrice::getStockName, Function.identity()));
	}

	@Override
	public Iterable<String> loadAllKeys() {
		Session session = getSession();
		Query<String> query = session.createQuery("select stockName from StockPrice", String.class);
		List<String> listStockPrice = query.getResultList();
		System.out.println("loadall keys used in hazelcast");
		return listStockPrice;
	}

	@Override
	public void store(String key, StockPrice value) {
		Session session = getSession();
		Query query = session.createQuery("INSERT INTO StockPrice (stockName, price) " +"SELECT stockName, price FROM StockName");
		query.setParameter("key", key);
		query.setParameter("value", value);
		System.out.println("The store method::" + key + "::" + value);
	}

	@Override
	public void storeAll(Map<String, StockPrice> map) {
		// TODO Auto-generated method stub
//		Session session = getSession();
//		
//		Query query = session.createQuery("insert into StockPrice (StockName, price) values  ");
//		System.out.println("The storeAll method::" + map);
		
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void delete(String key) {
//		EntityManagerFactory factory = Persistence
//	            .createEntityManagerFactory("StockPriceMapStore");
//	    entityManager = factory.createEntityManager();
//		// TODO Auto-generated method stub
//		//Session session = getSession();
//		entityManager.getTransaction().begin();
//		//Query query = session.createQuery(" delete StockPrice s where UPPER(s.stockName) = UPPER(:key)");
//		javax.persistence.Query query = entityManager.createNativeQuery("Delete from StockPrice where stockName=?");
//		query.setParameter(1, key);
//		//query.setParameter("key", key);
//		entityManager.getTransaction().commit();
//		entityManager.close();
//		//query.executeUpdate();
//		
//		System.out.println("The delete method::" + key);
	}

	@Override
	public void deleteAll(Collection<String> keys) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Query query = session.createQuery("delete StockPrice s where s.stockName in (:keys)", String.class);
		query.setParameterList("keys", keys);
		query.executeUpdate();
		System.out.println("The deleteAll method::" + keys);
	}

}
