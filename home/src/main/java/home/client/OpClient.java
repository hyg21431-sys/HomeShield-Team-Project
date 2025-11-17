package home.client;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import home.dto.OpBodyDTO;
import home.dto.OpItemDTO;
import home.dto.OpResponseDTO;

@Component
public class OpClient {
	
	@Value("${api.service.key}")
    private String serviceKey;
	
	@Value("${api.officetel.rent.url}")
    private String apiLeaseUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();
    private final XmlMapper xmlMapper = new XmlMapper();
    
    public List<OpItemDTO> fetchOpLeaseData(String lawdCd, String dealYmd) {
    	String url =  UriComponentsBuilder.fromHttpUrl(apiLeaseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("LAWD_CD", lawdCd)
                .queryParam("DEAL_YMD", dealYmd)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 50)
                .build(true)
                .toUriString();
    
	    try {
	        // 2. API 호출
	        String xmlResult = restTemplate.getForObject(url, String.class);
	        
	        // 3. XML 파싱 (최종 파싱 로직)
	        OpResponseDTO responseDTO = xmlMapper.readValue(xmlResult, OpResponseDTO.class);
	        
	        // 4. 데이터 추출 및 반환 (BodyDTO의 item 리스트를 바로 가져옴)
	        return Optional.ofNullable(responseDTO)
	                .map(OpResponseDTO::getBody)
	                .map(OpBodyDTO::getItem)
	                .orElse(Collections.emptyList());
	
	    } catch (Exception e) {
	        System.err.println("API 호출 및 XML 파싱 중 오류 발생: " + e.getMessage());
	        return Collections.emptyList(); 
    	  }
	}
	
}
