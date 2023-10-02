package com.ebox3.server.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "attributekey")
public class AttributeKey {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "description")
	private String description;

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AttributeValue> getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(Set<AttributeValue> attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		return "AttributeKey [id=" + id + ", description=" + description + ", attributeValue=" + attributeValue + "]";
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "attributeKey")
	private Set<AttributeValue> attributeValue = new HashSet<>();

}
