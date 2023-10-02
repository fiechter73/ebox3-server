package com.ebox3.server.service;

import java.util.Map;


import com.ebox3.server.model.AttributeKey;
import com.ebox3.server.model.dto.AttributeKeyDTO;

public interface AttributeKeyService {

	public Map<String, Object> getAllAttributeKeys(String search, int page, int size);

	public Map<String, Object> getById(Long id, int page, int size);

	public Iterable<AttributeKeyDTO> getAttributeKeys();

	public Iterable<Map<String, String>> getAttributeByName(String name);

	public AttributeKeyDTO update(Long id, AttributeKey attributeKeyRequest);

}
