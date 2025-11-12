package home.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

// <body> 태그의 내용을 담는 DTO
public class BodyDTO {
    
    // 1. XML의 <items> 태그가 이 리스트를 감싸고 있음을 명시 (래퍼 지정)
    @JacksonXmlElementWrapper(localName = "items") 
    
    // 2. 리스트의 개별 요소가 XML의 <item> 태그임을 명시
    @JacksonXmlProperty(localName = "item")
    private List<ItemDTO> item; // ItemDTO 리스트를 직접 받습니다.

    // totalCount 등 다른 필드
    private int numOfRows;
    private int pageNo;
    private int totalCount; 
    
    public BodyDTO() {}

    // --- Getter와 Setter (ItemDTO 리스트) ---
    public List<ItemDTO> getItem() {
        return item;
    }
    public void setItem(List<ItemDTO> item) {
        this.item = item;
    }
    
    // --- (나머지 Getter/Setter) ---
    public int getNumOfRows() { return numOfRows; }
    public void setNumOfRows(int numOfRows) { this.numOfRows = numOfRows; }
    public int getPageNo() { return pageNo; }
    public void setPageNo(int pageNo) { this.pageNo = pageNo; }
    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
}