package com.cashrich.userprofile_service.service;

import com.cashrich.userprofile_service.entity.ApiResponseEntity;
import com.cashrich.userprofile_service.repository.ApiResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CoinMarketCapService {

    private final RestTemplate restTemplate;

    private  final ApiResponseRepository apiResponseRepository;

    private static final String COINMARKETCAP_API_URL = "https://proapi.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";
    private static final String API_KEY = "27ab17d1-215f-49e5-9ca4-afd48810c149";

    @Autowired
    public CoinMarketCapService(RestTemplate restTemplate, ApiResponseRepository apiResponseRepository) {
        this.restTemplate = restTemplate;
        this.apiResponseRepository = apiResponseRepository;
    }

    public String fetchCoinData(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    COINMARKETCAP_API_URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            ApiResponseEntity apiResponseEntity = new ApiResponseEntity();
            apiResponseEntity.setUserId(userId);
            apiResponseEntity.setResponse(response.getBody());
            apiResponseEntity.setTimestamp(LocalDateTime.now());
            apiResponseRepository.save(apiResponseEntity);

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch coin data from external API", e);
        }
    }
}

