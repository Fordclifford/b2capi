/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpesapush.controller;

import com.mpesapush.model.B2C;
import com.mpesapush.repository.ConfigRepo;
import com.mpesapush.repository.b2cRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.*;


/**
 *
 * @author techsavanna
 */
@RestController
@RequestMapping("/api")
public class MpesaController {

  
    
    @Autowired
    private ConfigRepo cRepo;
    
    @Autowired
    private b2cRepository b2cRepo;
    
    
     
     HttpPost httpPost;
     HttpClient httpClient;
    
     public String authenticate(String appKey,String appSecret) throws IOException, JSONException {
         String app_key = appKey/*"GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V"*/;
         String app_secret = appSecret;
         String appKeySecret = app_key + ":" + app_secret;
         byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
         String encoded = Base64.getEncoder().encodeToString(bytes);

         okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

         okhttp3.Request request = new okhttp3.Request.Builder()
                 .url("https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                 .get()
                 .addHeader("authorization", "Basic " + encoded)
                 .addHeader("cache-control", "no-cache")
                 .build();

         okhttp3.Response response = client.newCall(request).execute();
         org.json.JSONObject jsonObject = new org.json.JSONObject(response.body().string());
         //System.out.println("access"+jsonObject);
         return jsonObject.getString("access_token");
     }


    public static String getpassword(String bs, String webpass, String timestamp) throws IOException {
        String password = toBase64(bs + webpass + timestamp);

        return password;
    }

    public static String toBase64(String raw) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }
   // public String STKPushSimulation(String businessShortCode, String password, String timestamp, String transactionType, Long amount, Long phoneNumber, Long partyA, String partyB, String callBackURL, String queueTimeOutURL, String accountReference, String transactionDesc) throws JSONException, IOException {


  
    public String recurseKeys(org.json.JSONObject jObj, String findKey) throws org.json.JSONException {
        String finalValue = "";
        if (jObj == null) {
            return "";
        }

        Iterator<String> keyItr = jObj.keys();
        Map<String, String> map = new HashMap<>();

        while (keyItr.hasNext()) {
            String key = keyItr.next();
            map.put(key, jObj.getString(key));
        }

        for (Map.Entry<String, String> e : (map).entrySet()) {
            String key = e.getKey();
            if (key.equalsIgnoreCase(findKey)) {
                return jObj.getString(key);
            }

            // read value
            Object value = jObj.get(key);

            if (value instanceof org.json.JSONObject) {
                finalValue = recurseKeys((org.json.JSONObject) value, findKey);
            }
        }

        // key is not found
        return finalValue;
    }

    
    public String getStdTimeStamp() {
        String date = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR),
                mon = cal.get(Calendar.MONTH) + 1,
                day = cal.get(Calendar.DAY_OF_MONTH),
                hr = cal.get(Calendar.HOUR_OF_DAY),
                min = cal.get(Calendar.MINUTE),
                sec = cal.get(Calendar.SECOND);
        date = year + append(mon) + append(day) + append(hr) + append(min) + append(sec);
        return date;
    }

    private String append(int val) {
        if (val < 10) {
            return "0" + val;
        }
        return "" + val;
    }

  
    /**
     *
     *  * @return @throws IOException
     * @throws JSONException
     */
 
   @PostMapping("/mpesa/b2c")
    public String B2CRequest( @RequestBody com.mpesapush.payload.Request pBody) throws IOException, JSONException {
	   com.mpesapush.model.Config config = cRepo.findByTenant(pBody.getTenant());
	   
	   System.out.println(config.getSecurityCredential());
	   
	   JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("InitiatorName", config.getInitiatorName());
        jsonObject.put("SecurityCredential", config.getSecurityCredential());
        jsonObject.put("CommandID", config.getCommandId());
        jsonObject.put("Amount", pBody.getAmount());
        jsonObject.put("PartyA", config.getShortcode());
        jsonObject.put("PartyB", pBody.getPartyB());
        jsonObject.put("Remarks", config.getRemarks());
        jsonObject.put("QueueTimeOutURL", config.getQueueTimeoutUrl());
        jsonObject.put("ResultURL", config.getResultURL());
        jsonObject.put("Occassion", config.getOccassion());


        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");


        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, requestJson);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url( "https://api.safaricom.co.ke/mpesa/b2c/v1/paymentrequest")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+authenticate(config.getAppkey(),config.getAppsecret()))
                .addHeader("cache-control", "no-cache")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        String res = response.body().string();
        System.out.println(res);
        JSONObject jsonmn = new JSONObject(res);

        String ConversationID = recurseKeys(jsonmn, "ConversationID");
        String OriginatorConversationID = recurseKeys(jsonmn, "OriginatorConversationID");
        String ResponseCode = recurseKeys(jsonmn, "ResponseCode");
        String ResponseDescription = recurseKeys(jsonmn, "ResponseDescription");
       
        
        B2C b2c = new B2C(config.getCommandId(),config.getRemarks(),pBody.getAmount(),config.getShortcode(),pBody.getPartyB(),config.getResultURL(),config.getQueueTimeoutUrl(),config.getOccassion(),OriginatorConversationID,ConversationID,ResponseDescription,
        		null,ResponseCode,null,null,null,null,null,null,null,null,null,null);
        ///System.out.println(response.body().string());
        
               b2cRepo.save(b2c);
        return res;
}

    
}
