package com.mpesapush.model;

import javax.persistence.*;

@Entity
@Table(name = "b2c_config")
public class Config {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;


	@Column(name = "tenant")
	private String tenant;

	@Column(name = "initiatorName")
	private String initiatorName;
	
	@Column(name = "securityCredential")
	private String securityCredential;
	
	@Column(name = "commandId")
	private String commandId;

	@Column(name = "shortcode")
	private Long shortcode;
	
	@Column(name = "queueTimeoutUrl")
	private String queueTimeoutUrl;
	
	@Column(name = "resultURL")	
	private String resultURL;

	@Column(name = "occassion")
	private String occassion;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "appkey")
	private String appkey;
	
	@Column(name = "appsecret")
	private String appsecret;

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}

	public String getSecurityCredential() {
		return securityCredential;
	}

	public void setSecurityCredential(String securityCredential) {
		this.securityCredential = securityCredential;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public Long getShortcode() {
		return shortcode;
	}

	public void setShortcode(Long shortcode) {
		this.shortcode = shortcode;
	}

	public String getQueueTimeoutUrl() {
		return queueTimeoutUrl;
	}

	public void setQueueTimeoutUrl(String queueTimeoutUrl) {
		this.queueTimeoutUrl = queueTimeoutUrl;
	}

	public String getResultURL() {
		return resultURL;
	}

	public void setResultURL(String resultURL) {
		this.resultURL = resultURL;
	}

	public String getOccassion() {
		return occassion;
	}

	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}





	public Config(String tenant, String initiatorName, String securityCredential, String commandId, Long shortcode,
			String queueTimeoutUrl, String resultURL, String occassion, String remarks, String appkey,
			String appsecret) {
		super();
		this.tenant = tenant;
		this.initiatorName = initiatorName;
		this.securityCredential = securityCredential;
		this.commandId = commandId;
		this.shortcode = shortcode;
		this.queueTimeoutUrl = queueTimeoutUrl;
		this.resultURL = resultURL;
		this.occassion = occassion;
		this.remarks = remarks;
		this.appkey = appkey;
		this.appsecret = appsecret;
	}

	public Config() {
		super();
		// TODO Auto-generated constructor stub
	}


	

}
