package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.util.Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class LoanApi {
    private RestTemplate restTemplate;

    @Value("${spring.loan.host}")
    private String host;

    @Value("${spring.loan.endpoint.getInfo}")
    private String getInfo;

    @Autowired
    public LoanApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetLoanInfoResponse getLoanInfo(Long id) throws Exception {
        String data = "{}";
        try{
            RequestEntity requestEntity = RequestEntity.get(URI.create(host + "/" + getInfo + "/" + id)).build();

            System.out.println("Request: " + requestEntity.getMethod() + "url: " + requestEntity.getUrl());
            ResponseEntity<String> res = restTemplate.exchange(requestEntity, String.class);

            if(res.getStatusCode().value() == 200){
                JSONObject resp = new JSONObject(res.getBody());
                JSONObject status = new JSONObject(resp.getString("status"));
                if(status.getString("code").equals("0")){
                    data = resp.get("data").toString();
                }
            }
        } catch (final HttpClientErrorException e) {
            System.out.println("HttpClientErrorException " + e);
            throw new Exception("HttpClientErrorException");
        } catch (HttpServerErrorException e) {
            System.out.println("HttpServerErrorException " + e);
            throw new Exception("httpServerErrorException");
        } catch (Exception e) {
            throw e;
        }

        return Util.readValue(data, GetLoanInfoResponse.class);
    }
}
