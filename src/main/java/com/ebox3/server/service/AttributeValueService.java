package com.ebox3.server.service;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.dto.AttributeValueDTO;

public interface AttributeValueService {

	public AttributeValueDTO getAttributeValue(Long id);

	public Long delete(Long id) throws ResourceNotFoundException;

	public AttributeValueDTO create(Long id, AttributeValueDTO attributeValueDTO);

	public AttributeValueDTO update(Long id, AttributeValueDTO attributeValueDTO);
}
