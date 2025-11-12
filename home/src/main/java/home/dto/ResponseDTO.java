package home.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

// <response> 태그를 담는 최상위 DTO
@JacksonXmlRootElement(localName = "response") 
public class ResponseDTO {
    
    // <header> 태그를 위한 단순 Placeholder (간결화를 위해 상세 파싱은 생략)
    private Object header; 
    
    @JsonProperty("body")
    private BodyDTO body;

    public ResponseDTO() {}

    // --- Getter와 Setter (필수) ---
    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public BodyDTO getBody() {
        return body;
    }

    public void setBody(BodyDTO body) {
        this.body = body;
    }
}