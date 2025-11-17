package home.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MltItemDTO implements Serializable{
	
	@JsonIgnore
	private String buildYear;			// 건축 연도 (건물이 지어진 해)
	@JsonIgnore
	private String contractTerm;		// 전월세 계약기간 (예: 25.10~27.10)
	@JsonIgnore
	private String contractType;		// 계약 유형 (예: 신규, 갱신)
	private String dealDay;				// 실거래 날짜(일)
	private String dealMonth;			// 실거래 날짜(월)
	private String dealYear;			// 실거래 날짜(년)
	private String deposit; 			// 전세 보증금 또는 월세 보증금 (단위: 만 원)
	private String excluUseAr; 			// 주거 전용 면적 (제곱미터, m²)
	private String floor;				// 주거 층수
	private String houseType;			// 주택 유형 (연립 또는 다세대)
	private String jibun;				// 지번 (번지)
	private String mhouseNm;			// 연립/다세대 주택 이름
	private String monthlyRent; 		// 월세 금액 (전세일 경우 "0" 또는 빈 값)
	@JsonIgnore
	private String preDeposit;			// 계약 갱신 시, 직전 계약의 보증금
	@JsonIgnore
	private String preMonthlyRent;		// 계약 갱신 시, 직전 계약의 월세액
	@JsonIgnore
	private String sggCd;				// 시군구 코드 (법정동 5자리 코드)
	private String umdNm;				// 읍면동 행정 구역 이름 (예: 용문동)
	@JsonIgnore
	private String useRRRight; 			// 계약갱신요구권 사용 여부
	
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
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getMhouseNm() {
		return mhouseNm;
	}
	public void setMhouseNm(String mhouseNm) {
		this.mhouseNm = mhouseNm;
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
