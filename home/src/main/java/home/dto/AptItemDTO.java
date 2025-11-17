package home.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

// <item> 태그의 개별 거래 정보를 담습니다.
public class AptItemDTO implements Serializable {
	
    private String aptNm;				// 아파트 이름
    @JsonIgnore
    private String buildYear;			// 건축 연도
    @JsonIgnore
    private String contractTerm;		// 전월세 계약기간
    @JsonIgnore
    private String contractType;		// 계약 유형 (신규 계약, 갱신 계약)
    private String dealDay;				// 실거래 날짜(일)
    private String dealMonth;			// 실거래 날짜(월)
    private String dealYear;			// 실거래 날짜(년)
    private String deposit; 			// 전세가 또는 월세보증금
    private String excluUseAr; 			// 주거 전용 면적
    private String floor;				// 주거 층수
    private String jibun;				// 지번
    private String monthlyRent; 		// 월세금액 비어있을 경우 0
    @JsonIgnore
    private String preDeposit;			// 계약 갱신 시, 직전 계약의 보증금
    @JsonIgnore
    private String preMonthlyRent;		// 계약 갱신 시, 직전 계약의 월세액
    @JsonIgnore
    private String sggCd;				// 시군구 코드 (법정동 5자리 코드)
    private String umdNm;				// 읍면동 행정 구역 이름
    @JsonIgnore
    private String useRRRight; 			// 계약갱신요구권 사용 여부
	
    public AptItemDTO() {
    	
    }

	public String getAptNm() {
		return aptNm;
	}


	public void setAptNm(String aptNm) {
		this.aptNm = aptNm;
	}


	public String getBuildYear() {
		return buildYear;
	}


	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}


	public String getContractTerm() {
		return contractTerm;
	}


	public void setContractTerm(String contractTerm) {
		this.contractTerm = contractTerm;
	}


	public String getContractType() {
		return contractType;
	}


	public void setContractType(String contractType) {
		this.contractType = contractType;
	}


	public String getDealDay() {
		return dealDay;
	}


	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}


	public String getDealMonth() {
		return dealMonth;
	}


	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}


	public String getDealYear() {
		return dealYear;
	}


	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}


	public String getDeposit() {
		return deposit;
	}


	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}


	public String getExcluUseAr() {
		return excluUseAr;
	}


	public void setExcluUseAr(String excluUseAr) {
		this.excluUseAr = excluUseAr;
	}


	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}


	public String getJibun() {
		return jibun;
	}


	public void setJibun(String jibun) {
		this.jibun = jibun;
	}


	public String getMonthlyRent() {
		return monthlyRent;
	}


	public void setMonthlyRent(String monthlyRent) {
		this.monthlyRent = monthlyRent;
	}


	public String getPreDeposit() {
		return preDeposit;
	}


	public void setPreDeposit(String preDeposit) {
		this.preDeposit = preDeposit;
	}


	public String getPreMonthlyRent() {
		return preMonthlyRent;
	}


	public void setPreMonthlyRent(String preMonthlyRent) {
		this.preMonthlyRent = preMonthlyRent;
	}


	public String getSggCd() {
		return sggCd;
	}


	public void setSggCd(String sggCd) {
		this.sggCd = sggCd;
	}


	public String getUmdNm() {
		return umdNm;
	}


	public void setUmdNm(String umdNm) {
		this.umdNm = umdNm;
	}


	public String getUseRRRight() {
		return useRRRight;
	}


	public void setUseRRRight(String useRRRight) {
		this.useRRRight = useRRRight;
	}
}