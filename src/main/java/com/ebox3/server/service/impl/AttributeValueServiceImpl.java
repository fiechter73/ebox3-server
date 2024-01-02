package com.ebox3.server.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.AttributeValue;
import com.ebox3.server.model.dto.AttributeValueDTO;
import com.ebox3.server.repo.AttributeKeyRepository;
import com.ebox3.server.repo.AttributeValueRepository;
import com.ebox3.server.service.AttributeValueService;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {

	@Autowired
	AttributeValueRepository attributeValueRepository;

	@Autowired
	AttributeKeyRepository attributeKeyRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public AttributeValueDTO getAttributeValue(@PathVariable("id") Long id) {

		AttributeValue value = attributeValueRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("AttributeValue by id %d not found", id)));

		AttributeValueDTO response = new AttributeValueDTO();
		response.setId(value.getId());
		response.setValue(value.getValue());
		response.setSortOrder(value.getSortOrder());
		response.setDescription(value.getDescription());
		response.setAttributeKeyId(value.getAttributeKey().getId());

		return response;
	}

	@Override
	public Long delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		AttributeValue value = attributeValueRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AttributeValue not found with id " + id));
		attributeValueRepository.delete(value);
		return id;
	}

	@Override
	public AttributeValueDTO create(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeValueDTO attributeValueDTO) {

		return attributeKeyRepository.findById(id).map(attributeKey -> {
			attributeValueDTO.setAttributeKeyId(attributeKey.getId());
			return mapper.map(attributeValueRepository.save(mapper.map(attributeValueDTO, AttributeValue.class)),
					AttributeValueDTO.class);

		}).orElseThrow(() -> new ResourceNotFoundException("AttributeKey Id " + id + " not found"));
	}

	@Override
	public AttributeValueDTO update(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeValueDTO attributeValueDTO) {

		return attributeValueRepository.findById(id).map(attributeValue -> {

			mapper.typeMap(AttributeValueDTO.class, AttributeValue.class).addMappings(mapper -> {
				mapper.skip(AttributeValue::setId);
				// mapper.skip(AttributeValue::setAttributeKey);
			}).map(attributeValueDTO, attributeValue);
			return mapper.map(attributeValueRepository.save(attributeValue), AttributeValueDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("AttributeKeyId " + id + "not found"));
	}

}
