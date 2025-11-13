package com.koreait.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koreait.project.DAO.MapDAO;
import com.koreait.project.DTO.MapDTO;





@Service
public class MapService {
	@Autowired 
	private MapDAO dao;
	
	private final String KAKAO_REST_KEY = "646fcb72c66e03ccced09c085a5b1bdc";
    private final String SERVICE_KEY = "75e8f96947211aea03866523eafc2b89875df67309f173c4277866a804c7868d";
	

    public List<MapDTO> getRentalList(String startYmd, String endYmd, int numOfRows) {
        List<MapDTO> result = new ArrayList<>();
        if (startYmd == null || endYmd == null) return result;

        String current = startYmd;

        // 대전 전체 법정동 코드 배열 추가 (변경)
        String[] lawdCds = {"30110", "30140", "30170", "30200", "30230"};

        while (Integer.parseInt(current) <= Integer.parseInt(endYmd)) {

            for (String lawdCd : lawdCds) { // lawdCd 반복 조회 (추가)
                List<MapDTO> monthlyList = fetchMonthlyRental(current, numOfRows, lawdCd);

                // 월세/전세 단위 처리
                monthlyList.forEach(dto -> {
                    // 월세가 null, 빈 문자열, 0이면 월세 없음
                    if (dto.getMonthlyRent() == null || dto.getMonthlyRent().isEmpty() || "0".equals(dto.getMonthlyRent())) {
                        dto.setMonthlyRent(null); // 월세 없음 처리
                    }

                    // 보증금(전세)
                    if (dto.getDeposit() == null || dto.getDeposit().isEmpty() || "0".equals(dto.getDeposit())) {
                        dto.setDeposit(null); // 전세 없음 처리
                    }
                });


                result.addAll(monthlyList);
            }

            // 다음 달 계산
            int year = Integer.parseInt(current.substring(0, 4));
            int month = Integer.parseInt(current.substring(4, 6)) + 1;
            if (month > 12) {
                year += 1;
                month = 1;
            }
            current = String.format("%04d%02d", year, month);
        }
        return result;
    }

    
    private List<MapDTO> fetchMonthlyRental(String dealYmd, int numOfRows, String lawdCd) {
        List<MapDTO> list = new ArrayList<>();
        try {
            String encodedServiceKey = URLEncoder.encode(SERVICE_KEY, StandardCharsets.UTF_8);
            String apiUrl = String.format(
                    "https://apis.data.go.kr/1613000/RTMSDataSvcOffiRent/getRTMSDataSvcOffiRent" +
                    "?serviceKey=%s&LAWD_CD=%s&DEAL_YMD=%s&_type=json&numOfRows=%d&pageNo=1",
                    encodedServiceKey, lawdCd, dealYmd, numOfRows
            );

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode items = mapper.readTree(response)
                    .path("response").path("body").path("items").path("item");

            if (items.isMissingNode()) return list;

            if (items.isArray()) {
                for (JsonNode node : items) list.add(convertNodeToDto(node));
            } else if (items.isObject()) {
                list.add(convertNodeToDto(items));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	
	
	
	
    private MapDTO convertNodeToDto(JsonNode node) {
    	MapDTO dto = new MapDTO();
        dto.setGu(node.path("sggNm").asText(""));
        dto.setDong(node.path("umdNm").asText(""));
        dto.setBuildingName(node.path("offiNm").asText(""));
        dto.setDeposit(node.path("deposit").asText(""));
        dto.setMonthlyRent(node.path("monthlyRent").asText(""));
        dto.setContractDay(
                node.path("dealYear").asText("") + "-" +
                node.path("dealMonth").asText("") + "-" +
                node.path("dealDay").asText("")
        );

        double[] latlng = getCoordinates(dto.getGu(), dto.getDong(), dto.getBuildingName());
        dto.setLat(latlng[0]);
        dto.setLng(latlng[1]);
        return dto;
    }
	
	

    private double[] getCoordinates(String gu, String dong, String buildingName) {
        // 먼저 DB에서 좌표 조회
    	MapDTO dto = dao.selectCoordinates(gu, dong, buildingName);
        if (dto != null && dto.getLat() != 0 && dto.getLng() != 0) {
            return new double[]{dto.getLat(), dto.getLng()};
        }

        // DB에 없으면 Kakao API로 조회
        double[] latlng = searchAddress(gu + " " + dong);
        if ((latlng[0] == 0 && latlng[1] == 0) && buildingName != null && !buildingName.isEmpty()) {
            latlng = searchAddress(gu + " " + dong + " " + buildingName);
        }

        // 조회한 좌표 DB에 저장 (없으면 insert, 있으면 update)
        MapDTO newDto = new MapDTO();
        newDto.setGu(gu);
        newDto.setDong(dong);
        newDto.setBuildingName(buildingName);
        newDto.setLat(latlng[0]);
        newDto.setLng(latlng[1]);

        if (dao.selectCoordinates(gu, dong, buildingName) == null) {
            dao.insertCoordinates(newDto);
        } else {
            dao.updateCoordinates(newDto);
        }

        return latlng;
    }



    private double[] searchAddress(String address) {
        double[] latlng = new double[]{0,0};
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" +
                    URLEncoder.encode(address, StandardCharsets.UTF_8);

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + KAKAO_REST_KEY);

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getResponseCode() >= 200 && conn.getResponseCode() < 300
                            ? conn.getInputStream()
                            : conn.getErrorStream(),
                    StandardCharsets.UTF_8
            ));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) sb.append(line);
            rd.close();
            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> resultMap = mapper.readValue(sb.toString(), Map.class);
            List<Map<String,Object>> documents = (List<Map<String,Object>>) resultMap.get("documents");

            if (documents != null && !documents.isEmpty()) {
                Map<String,Object> firstDoc = documents.get(0);
                latlng[0] = Double.parseDouble((String) firstDoc.get("y"));
                latlng[1] = Double.parseDouble((String) firstDoc.get("x"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return latlng;
    }


}
