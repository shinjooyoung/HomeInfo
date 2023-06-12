package com.jshin.house.house.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class RestTemplateHouseApi implements HouseApi{

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu, String dong, String ho) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        log.debug("[HTTP API] url={}, key={}", URL, KEY);

        // header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("ServiceKey", KEY);
        body.add("pnu",pnu);
        if(!dong.isEmpty()) {
            body.add("dongNm",dong);
        }
        if(!ho.isEmpty()) {
            body.add("hoNm",ho);
        }
        body.add("format","json");

        // Combine Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // Request and getResponse
        HttpEntity<String> response = restTemplate.postForEntity(URL, requestMessage, String.class);

        // Response Body
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return objectMapper.readValue(response.getBody(), Map.class);
    }

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu, String dong) throws Exception {
        return requestToHouseInfo(pnu, dong, "");
    }

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu) throws Exception {
        return requestToHouseInfo(pnu, "", "");
    }

}
