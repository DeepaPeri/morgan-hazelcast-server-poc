package com.hazelcast.withzookeeper.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="stock_price_table")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3936098799162312502L;

	@Id
	@Column(name="stock_name")
	private String stockName;
	
	@UpdateTimestamp
	@Column(name="timestamp", updatable = true)
	private Timestamp timestamp;
	
	@Column(name="price")
	private float price;
	
	public StockPrice() {
		
	}
	

	public StockPrice(String stockName, float price) {
		this.stockName = stockName;
		this.price = price;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "StockPrice [stockName=" + stockName + ", timestamp=" + timestamp + ", price=" + price + "]";
	}
	
}
