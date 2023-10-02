package com.ebox3.server.model.dto;


public class AttributeValueDTO {

	private Long id;
	private String value;
	private String description;
	private Long sortOrder;
	private Long attributeKeyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Long getAttributeKeyId() {
		return attributeKeyId;
	}

	public void setAttributeKeyId(Long attributeKeyId) {
		this.attributeKeyId = attributeKeyId;
	}
	
	@Override
	public String toString() {
		return "AttributeValueDTO [id=" + id + ", value=" + value + ", description=" + description + ", sortOrder="
				+ sortOrder + ", attributeKeyId=" + attributeKeyId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttributeValueDTO) {
			return ((AttributeValueDTO) obj).sortOrder == this.sortOrder;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.sortOrder.intValue();
	}



}
