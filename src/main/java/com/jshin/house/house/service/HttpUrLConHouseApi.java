package com.jshin.house.house.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Primary
@Component
public class HttpUrLConHouseApi implements HouseApi{
    public HttpUrLConHouseApi() {
        houseApis.add(this);
    }

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu, String dong, String ho) throws Exception  {

        StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(pnu, "UTF-8"));

        if(!dong.isEmpty()) {
            urlBuilder.append("&" + URLEncoder.encode("dongNm","UTF-8") + "=" + URLEncoder.encode(dong, "UTF-8"));
        }

        if(ho.isEmpty()) {
            urlBuilder.append("&" + URLEncoder.encode("hoNm","UTF-8") + "=" + URLEncoder.encode(ho, "UTF-8"));
        }

        urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();

        rd.close();
        conn.disconnect();
        return convertJsonStrToMap(sb.toString());
    }

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu, String dong) throws Exception {
        return requestToHouseInfo(pnu, dong, "");
    }

    @Override
    public Map<String, Object> requestToHouseInfo(String pnu) throws Exception {
        return requestToHouseInfo(pnu, "", "");
    }

    private Map<String,Object> convertJsonStrToMap(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> apartHousingPrices = (Map<String, Object>) map.get("apartHousingPrices");
        List<Map<String, Object>> fields = (List<Map<String, Object>>) apartHousingPrices.get("field");
        Map<String, Object> field = null;
        if(fields.size() > 0) {
            field = fields.get(fields.size()-1);
        }
        return field;
    }
}
