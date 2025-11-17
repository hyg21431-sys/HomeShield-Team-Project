package home.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MltBodyDTO {
	
	// 1. XML의 <items> 태그가 이 리스트를 감싸고 있음을 명시 (래퍼 지정)
    @JacksonXmlElementWrapper(localName = "items") 
    
    // 2. 리스트의 개별 요소가 XML의 <item> 태그임을 명시
    @JacksonXmlProperty(localName = "item")
    private List<MltItemDTO> item; // ItemDTO 리스트를 직접 받습니다.

    // totalCount 등 다른 필드
    private int numOfRows;
    private int pageNo;
    private int totalCount; 
    
    public MltBodyDTO() {}

    // --- Getter와 Setter (ItemDTO 리스트) ---
    public List<MltItemDTO> getItem() {
        return item;
    }
    
    public void setItem(List<MltItemDTO> item) {
        this.item = item;
    }
    
    public int getNumOfRows() {
    	return numOfRows; 
	}
    
    public void setNumOfRows(int numOfRows) {
    	this.numOfRows = numOfRows; 
	}
    
    public int getPageNo() {
    	return pageNo; 
	}
    
    public void setPageNo(int pageNo) {
    	this.pageNo = pageNo; 
	}
    
    public int getTotalCount() {
    	return totalCount; 
	}
    
    public void setTotalCount(int totalCount) {
    	this.totalCount = totalCount; 
	}
}