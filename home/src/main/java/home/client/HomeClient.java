package home.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; // Spring 빈으로 등록
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import home.dto.ResponseDTO;
import home.dto.BodyDTO; 
import home.dto.ItemDTO; 

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component // 이 클래스가 Spring의 Client 또는 DAO 역할을 담당합니다.
public class HomeClient {

    @Value("${api.service.key}")
    private String serviceKey;

    @Value("${api.apt.lease.url}")
    private String apiLeaseUrl; 

    // RestTemplate과 XML 파싱을 위한 XmlMapper 객체 생성
    private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();

    /**
     * API를 호출하고 데이터를 파싱하여 ItemDTO 리스트를 반환합니다.
     * Service로부터 조회 기준(법정동 코드, 계약 년월)을 전달받습니다.
     */
    public List<ItemDTO> fetchAptLeaseData(String lawdCd, String dealYmd) {
        
        // 1. URL 구성
        String url = UriComponentsBuilder.fromHttpUrl(apiLeaseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("LAWD_CD", lawdCd) // Service로부터 받은 파라미터 사용
                .queryParam("DEAL_YMD", dealYmd) // Service로부터 받은 파라미터 사용
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 50)
                .build(true)
                .toUriString();
        
        try {
            // 2. API 호출
            String xmlResult = restTemplate.getForObject(url, String.class);
            
            // 3. XML 파싱 (최종 파싱 로직)
            ResponseDTO responseDTO = xmlMapper.readValue(xmlResult, ResponseDTO.class);
            
            // 4. 데이터 추출 및 반환 (BodyDTO의 item 리스트를 바로 가져옴)
            return Optional.ofNullable(responseDTO)
                    .map(ResponseDTO::getBody)
                    .map(BodyDTO::getItem)
                    .orElse(Collections.emptyList());

        } catch (Exception e) {
            System.err.println("API 호출 및 XML 파싱 중 오류 발생: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }
}