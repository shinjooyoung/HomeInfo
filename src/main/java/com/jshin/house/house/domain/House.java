package com.jshin.house.house.domain;

import com.jshin.house.house.service.HouseApi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.MapUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class House {


    private long id;

    private long pnu;           //고유번호

    private long idCode;        //행정구역코드

    private String aphusSeCodeNm; // 공동주택구분명 ex) 아파트, 빌라 ...

    private String idCodeNm;    //법정동명 ex) 서울특별시 마포구 상암동

    private String mnnmSlno;    //지번

    private String aphusNm;     //공동주택명 ex) 상암월드컵1단지

    private String dongNm;      //동명

    private String hoNm;        //호명

    private LocalDateTime lastUpdtDt;   // 데이터 기준일자

    private long pblntfPc; // 공시가격

    public static House searchHouseInfo(String pun, String dong, String ho) throws Exception {
        return  convertMapToHouse(HouseApi.getInstance().requestToHouseInfo(pun, dong, ho));
    }

    private static House convertMapToHouse(Map<String,Object> map) {


        if(MapUtils.isEmpty(map)){
            throw new IllegalArgumentException("error: House Info Data");
        }
        return House.builder()
                .pnu(Long.parseLong(map.get("pnu").toString()) )
                .idCode(Long.parseLong(map.get("ldCode").toString()))
                .aphusSeCodeNm(map.get("aphusSeCodeNm").toString())
                .mnnmSlno(map.get("mnnmSlno").toString())
                .aphusNm(map.get("aphusNm").toString())
                .dongNm(map.get("dongNm").toString())
                .hoNm(map.get("hoNm").toString())
                .lastUpdtDt(LocalDate.parse(map.get("lastUpdtDt").toString()).atStartOfDay())
                .build();
    }

}
