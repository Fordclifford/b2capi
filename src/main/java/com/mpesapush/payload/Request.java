package com.mpesapush.payload;

import javax.validation.constraints.Null;

public class Request {
	private String tenant;
	private Long amount;
	private String partyB;
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getPartyB() {
		return partyB;
	}
	public void setPartyB(String partyB) {
		this.partyB = partyB;
	}
	
	
	
	
	}
