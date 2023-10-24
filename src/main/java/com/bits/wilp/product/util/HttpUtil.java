package com.bits.wilp.product.util;

import javax.servlet.http.HttpServletRequest;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class HttpUtil {

    private RestTemplate restTemplate = new RestTemplate();

    public boolean isJwtExpired(HttpServletRequest request) {
        String authVal = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authVal);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        try (FileReader reader = new FileReader("src/main/resources/application.properties")) {
            // create properties object 
            Properties p = new Properties(); 
            p.load(reader);

            response = restTemplate.exchange(p.getProperty("user.service.base.url") + "/token-expiry",
                    HttpMethod.GET, requestEntity, String.class);
        } catch(RestClientException | IOException ioe) {
            return true;
        }
    
        if(response.getStatusCode() == HttpStatus.OK)
            return Boolean.parseBoolean(response.getBody());

        return true;

    }
}
