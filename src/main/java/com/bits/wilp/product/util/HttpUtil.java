package com.bits.wilp.product.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class HttpUtil {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${user.service.base.url}")
    private String userServiceBaseUrl;

    public boolean isJwtExpired(HttpServletRequest request) {
        String authVal = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authVal);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(this.userServiceBaseUrl + "/token-expiry",
                    HttpMethod.GET, requestEntity, String.class);
        } catch(RestClientException ioe) {
            System.out.println(ioe.getMessage());
            return true;
        }
    
        if(response.getStatusCode() == HttpStatus.OK)
            return Boolean.parseBoolean(response.getBody());

        return true;

    }
}
