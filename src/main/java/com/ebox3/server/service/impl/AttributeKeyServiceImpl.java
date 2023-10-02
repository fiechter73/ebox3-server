package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.AttributeKey;
import com.ebox3.server.model.dto.AttributeKeyDTO;
import com.ebox3.server.model.dto.AttributeValueDTO;
import com.ebox3.server.repo.AttributeKeyRepository;
import com.ebox3.server.service.AttributeKeyService;

@Service
public class AttributeKeyServiceImpl implements AttributeKeyService {

	@Autowired
	AttributeKeyRepository attributeKeyRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String, Object> getAllAttributeKeys(@RequestParam(required = false) String search,
			@RequestParam("page") int page, @RequestParam("size") int size)

	{
		try {

			List<AttributeKeyDTO> listOfAttributeKeys = new ArrayList<AttributeKeyDTO>();
			List<AttributeKeyDTO> listOfAttributeKeysSorted = new ArrayList<AttributeKeyDTO>();
			Pageable paging = PageRequest.of(page, size, Sort.by("description"));
			List<AttributeKey> attributeKeysList = null;
			Page<AttributeKey> attributeKeys = null;
			PagedListHolder<AttributeKeyDTO> attributeKeyDTO = null;
			Map<String, Object> response = new HashMap<>();

			if (search == null) {

				attributeKeys = attributeKeyRepository.findAll(paging);
				attributeKeys.forEach(attributeKey -> {
					AttributeKeyDTO akDTO = mapper.map(attributeKey, AttributeKeyDTO.class);
					listOfAttributeKeys.add(akDTO);
				});
				response.put("attributeKeys", listOfAttributeKeys);
				response.put("currentPage", attributeKeys.getNumber());
				response.put("totalItems", attributeKeys.getTotalElements());
				response.put("totalPages", attributeKeys.getTotalPages());

			} else {

				attributeKeysList = attributeKeyRepository.findByDescriptionContainingIgnoreCase(search);

				attributeKeysList.forEach(attributeKey -> {
					AttributeKeyDTO akDTO = mapper.map(attributeKey, AttributeKeyDTO.class);
					listOfAttributeKeys.add(akDTO);

				});
				listOfAttributeKeysSorted = listOfAttributeKeys.stream()
						.sorted(Comparator.comparing(AttributeKeyDTO::getId)).collect(Collectors.toList());

				attributeKeyDTO = new PagedListHolder<AttributeKeyDTO>(listOfAttributeKeysSorted);
				attributeKeyDTO.setPageSize(size);
				attributeKeyDTO.setPage(page);

				response.put("attributeKeys", attributeKeyDTO.getPageList());
				response.put("currentPage", attributeKeyDTO.getPage());
				response.put("totalItems", attributeKeyDTO.getNrOfElements());
				response.put("totalPages", attributeKeyDTO.getPageCount());

			}

			return response;
		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public Map<String, Object> getById(@RequestParam("id") Long id, @RequestParam("page") int page,
			@RequestParam("size") int size)

	{
		try {

			Map<String, Object> response = new HashMap<>();
			List<AttributeValueDTO> listOfAttributeValueSorted = new ArrayList<AttributeValueDTO>();
			PagedListHolder<AttributeValueDTO> attributeValueDTO = null;
			AttributeKey attributeKey = attributeKeyRepository.findById(id).orElseThrow(
					() -> new ResourceNotFoundException(String.format("AttributeKex by id %d not found", id)));

			List<AttributeValueDTO> list = new ArrayList<AttributeValueDTO>();
			attributeKey.getAttributeValue().forEach(attributeValueObj -> {
				AttributeValueDTO dto = new AttributeValueDTO();
				dto.setId(attributeValueObj.getId());
				dto.setValue(attributeValueObj.getValue());
				dto.setSortOrder(attributeValueObj.getSortOrder());
				dto.setDescription(attributeValueObj.getDescription());
				dto.setAttributeKeyId(attributeValueObj.getAttributeKey().getId());
				list.add(dto);
			});

			listOfAttributeValueSorted = list.stream().sorted(Comparator.comparing(AttributeValueDTO::getId))
					.collect(Collectors.toList());

			attributeValueDTO = new PagedListHolder<AttributeValueDTO>(listOfAttributeValueSorted);
			attributeValueDTO.setPageSize(size);
			attributeValueDTO.setPage(page);

			response.put("attributeValues", attributeValueDTO.getPageList());
			response.put("currentPage", attributeValueDTO.getPage());
			response.put("totalItems", attributeValueDTO.getNrOfElements());
			response.put("totalPages", attributeValueDTO.getPageCount());

			return response;

		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public Iterable<AttributeKeyDTO> getAttributeKeys() {
		List<AttributeKey> attributeKeyObj = attributeKeyRepository.findAll();
		List<AttributeKeyDTO> list = new ArrayList<AttributeKeyDTO>();
		attributeKeyObj.forEach(attributeKey -> {
			AttributeKeyDTO dto = new AttributeKeyDTO();
			dto.setId(attributeKey.getId());
			dto.setDescription(attributeKey.getDescription());
			list.add(dto);
		});
		return list;
	}

	@Override
	public Iterable<Map<String, String>> getAttributeByName(@RequestParam("name") String name) {
		List<Map<String, String>> values = attributeKeyRepository.valueSelection(name);
		return values;
	}

	@Override
	public AttributeKeyDTO update(@PathVariable("id") Long id,
			@Validated @RequestBody AttributeKey attributeKeyRequest) {

		return attributeKeyRepository.findById(id).map(attKey -> {
			attKey.setDescription(attributeKeyRequest.getDescription());
			return mapper.map(attributeKeyRepository.save(attKey), AttributeKeyDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("AttributeKeyId " + id + "not found"));
	}

}
