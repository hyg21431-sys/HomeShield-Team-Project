package home.dto;

public class MapDTO {
	private long id;
	private String house_type; 
	private String deal_ymd; 
	private int deposit; 
	private int monthly_rent; 
	private double area; 
	private int floor;
	private String address; 
	private String sgg_cd; 
	private String umd_nm; 
	private String house_name; 
	private String build_year; 
	private double latitude; 
	private double longitude; 
	private boolean geocoded;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHouse_type() {
		return house_type;
	}
	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}
	public String getDeal_ymd() {
		return deal_ymd;
	}
	public void setDeal_ymd(String deal_ymd) {
		this.deal_ymd = deal_ymd;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getMonthly_rent() {
		return monthly_rent;
	}
	public void setMonthly_rent(Integer monthly_rent) {
		this.monthly_rent = monthly_rent;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSgg_cd() {
		return sgg_cd;
	}
	public void setSgg_cd(String sgg_cd) {
		this.sgg_cd = sgg_cd;
	}
	public String getUmd_nm() {
		return umd_nm;
	}
	public void setUmd_nm(String umd_nm) {
		this.umd_nm = umd_nm;
	}
	public String getHouse_name() {
		return house_name;
	}
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	public String getBuild_year() {
		return build_year;
	}
	public void setBuild_year(String build_year) {
		this.build_year = build_year;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Boolean getGeocoded() {
		return geocoded;
	}
	public void setGeocoded(Boolean geocoded) {
		this.geocoded = geocoded;
	}
}
