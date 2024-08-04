package com.cashrich.userprofile_service.service;

import com.cashrich.userprofile_service.entity.ApiResponseEntity;
import com.cashrich.userprofile_service.repository.ApiResponseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CoinMarketCapService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    @Value("${coinmarketcap.api.url}")
    private  String apiUrl;

    private final RestTemplate restTemplate;
    private final ApiResponseRepository apiResponseRepository;



    public CoinMarketCapService(RestTemplate restTemplate, ApiResponseRepository coinRepository) {
        this.restTemplate = restTemplate;
        this.apiResponseRepository = coinRepository;
    }

    public String fetchCoinData(String userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {

            throw new RuntimeException("API call failed: " + e.getMessage());
        }

        String response = responseEntity.getBody();

        saveResponse(userId, apiUrl);

        return response;
    }

    private void saveResponse(String userId, String requestUrl) {
        ApiResponseEntity log = new ApiResponseEntity();
        log.setUserId(userId);
        log.setResponse(requestUrl);
        log.setTimestamp(LocalDateTime.now());
        apiResponseRepository.save(log);
    }

}

