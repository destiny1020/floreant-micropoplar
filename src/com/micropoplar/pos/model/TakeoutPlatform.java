package com.micropoplar.pos.model;

/**
 * 外卖平台实体类
 * 
 * @author destiny1020
 *
 */
public class TakeoutPlatform {

	private Integer id;
	private String name;
	private Boolean enabled;
	private Double discount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
