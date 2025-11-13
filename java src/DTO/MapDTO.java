package com.koreait.project.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapDTO {
	
	private String gu;
    private String dong;
    private String buildingName;
    private String deposit;
    private String monthlyRent;
    private String contractDay;
    private double lat;
    private double lng;
    
    public String getFullAddress() {
        return (gu != null ? gu : "") + " " + (dong != null ? dong : "") + " " + (buildingName != null ? buildingName : "");
    }

}
