package home.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

// <response> 태그를 담는 최상위 DTO (사용자 요청에 따라 Op 접두사 사용)
@JacksonXmlRootElement(localName = "response") 
public class OpResponseDTO {
    
    // <header> 태그를 위한 단순 Placeholder (간결화를 위해 상세 파싱은 생략)
    private Object header; 
    
    // 오피스텔 Body DTO인 OffiBodyDTO를 참조해야 합니다.
    @JsonProperty("body")
    private OpBodyDTO body;

    public OpResponseDTO() {}

    // --- Getter와 Setter (필수) ---
    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public OpBodyDTO getBody() {
        return body;
    }

    // Setter의 타입도 OffiBodyDTO로 변경
    public void setBody(OpBodyDTO body) {
        this.body = body;
    }
}