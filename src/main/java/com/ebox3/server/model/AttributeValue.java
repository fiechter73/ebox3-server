package com.ebox3.server.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attributevalue")
public class AttributeValue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "value")
	private String value;

	@Column(name = "description")
	private String description;

	@Column(name = "sortOrder")
	private Long sortOrder;

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

	public AttributeKey getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(AttributeKey attributeKey) {
		this.attributeKey = attributeKey;
	}

	@Override
	public String toString() {
		return "AttributeValue [id=" + id + ", value=" + value + ", description=" + description + ", sortOrder="
				+ sortOrder + ", attributeKey=" + attributeKey + "]";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attributeKeyId_FK", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private AttributeKey attributeKey;

}
