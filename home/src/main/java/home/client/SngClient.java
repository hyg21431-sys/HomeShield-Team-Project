package home.client;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import home.dto.SngBodyDTO;
import home.dto.SngItemDTO;
import home.dto.SngResponseDTO;


@Component
public class SngClient {
	
	@Value("${api.service.key}")
    private String serviceKey;
	
	@Value("${api.single.rent.url}")
    private String apiLeaseUrl;
	
	// RestTemplate과 XML 파싱을 위한 XmlMapper 객체 생성
    private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();
    
    /**
     * API를 호출하고 데이터를 파싱하여 ItemDTO 리스트를 반환합니다.
     * Service로부터 조회 기준(법정동 코드, 계약 년월)을 전달받습니다.
     */
    public List<SngItemDTO> fetchSngLeaseData(String lawdCd, String dealYmd) {
        
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
            SngResponseDTO responseDTO = xmlMapper.readValue(xmlResult, SngResponseDTO.class);
            
            // 4. 데이터 추출 및 반환 (BodyDTO의 item 리스트를 바로 가져옴)
            return Optional.ofNullable(responseDTO)
                    .map(SngResponseDTO::getBody)
                    .map(SngBodyDTO::getItem)
                    .orElse(Collections.emptyList());

        } catch (Exception e) {
            System.err.println("API 호출 및 XML 파싱 중 오류 발생: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }
}
