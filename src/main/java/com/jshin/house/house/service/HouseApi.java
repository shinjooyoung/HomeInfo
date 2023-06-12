package com.jshin.house.house.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HouseApi {

    String URL = "http://apis.data.go.kr/1611000/nsdi/ApartHousingPriceService/attr/getApartHousingPriceAttr";

    String KEY = "%2FcWDDcvoG8KiGKfeVzn7bv9jWOeLZx1L%2BPYI5Z6gKrFwRbRy1VaCmCZgrvit90sYrwCfx9ycyzJC5Hn9FZRvwA%3D%3D";

    List<HouseApi> houseApis  = new ArrayList<>();

    static HouseApi getInstance() {
        if(houseApis.isEmpty()){
            throw new IllegalArgumentException("error: HouseApi initialization failed");
        }
        return houseApis.get(0);
    }

    Map<String, Object> requestToHouseInfo(String pnu, String dong, String ho) throws Exception;
    Map<String, Object> requestToHouseInfo(String pnu, String dong) throws Exception;
    Map<String, Object> requestToHouseInfo(String pnu) throws Exception;

}
