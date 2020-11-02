package com.bbl.interview.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Key {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	  
	private String key;
	private String sum;
	
    public Key(){

    }
	
	
	public Key(String key, String sum){
		this.setKey(key);
		this.setSum(sum);
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
}
