package home.client;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import home.dto.MltBodyDTO;
import home.dto.MltItemDTO;
import home.dto.MltResponseDTO;

@Component
public class MltClient {
	
	@Value("${api.service.key}")
    private String serviceKey;
	
	@Value("${api.multi.rent.url}")
    private String apiLeaseUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();
    
    public List<MltItemDTO> fetchMltLeaseData(String lawdCd, String dealYmd) {
        
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
            MltResponseDTO responseDTO = xmlMapper.readValue(xmlResult, MltResponseDTO.class);
            
            // 4. 데이터 추출 및 반환 (BodyDTO의 item 리스트를 바로 가져옴)
            return Optional.ofNullable(responseDTO)
                    .map(MltResponseDTO::getBody)
                    .map(MltBodyDTO::getItem)
                    .orElse(Collections.emptyList());

        } catch (Exception e) {
            System.err.println("API 호출 및 XML 파싱 중 오류 발생: " + e.getMessage());
            return Collections.emptyList(); 
        }
    }
}
