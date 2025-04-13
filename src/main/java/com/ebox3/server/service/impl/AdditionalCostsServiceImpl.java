package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.AdditionalCosts;
import com.ebox3.server.model.dto.AdditionalCostsDTO;
import com.ebox3.server.repo.AdditionalCostsRepository;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.service.AdditionalCostsService;

@Service
public class AdditionalCostsServiceImpl implements AdditionalCostsService {

	@Autowired
	AdditionalCostsRepository additionalCostsRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Iterable<AdditionalCostsDTO> getAllAdditionalCosts(@PathVariable("value") Boolean value) {
		List<AdditionalCosts> additionalCosts = null;
		if (!value) {
			additionalCosts = additionalCostsRepository.findAll();
		} else {
			additionalCosts = additionalCostsRepository.findAllWithoutBezhalt("bezahlt");
		}

		List<AdditionalCostsDTO> list = new ArrayList<AdditionalCostsDTO>();
		additionalCosts.forEach(additionalCost -> {
			AdditionalCostsDTO addCostDTO = mapper.map(additionalCost, AdditionalCostsDTO.class);
			addCostDTO.setContractId(additionalCost.getContract().getId());
			addCostDTO.setContractStartDate(additionalCost.getContract().getStartDate());
			addCostDTO.setContractEndDate(additionalCost.getContract().getEndDate());
			addCostDTO.setContractStatusText(additionalCost.getContract().getStatusText());
			addCostDTO.setContractQuitPeriode(additionalCost.getContract().getFrist());
			addCostDTO.setCustomerId(additionalCost.getContract().getCustomer().getId());
			addCostDTO.setCustomerAndrede(additionalCost.getContract().getCustomer().getAnrede());
			addCostDTO.setCustomerVornameName(additionalCost.getContract().getCustomer().getVorname() + " "
					+ additionalCost.getContract().getCustomer().getName());
			if (additionalCost.getContract().getCustomer().isUseCompanyAddress()
					&& additionalCost.getContract().getCustomer().getFirmenAnschrift() != null) {
				String[] arrayOfStr = additionalCost.getContract().getCustomer().getFirmenAnschrift().split(",");
				if (arrayOfStr.length > 1) {
					addCostDTO.setCustomerStrasse(arrayOfStr[0] != null ? arrayOfStr[0].trim() : "");
					addCostDTO.setCustomerOrtPlz(arrayOfStr[1] != null ? arrayOfStr[1].trim() : "");
				} else {
					addCostDTO.setCustomerStrasse("n/a");
					addCostDTO.setCustomerOrtPlz("n/a");
				}
			} else {
				addCostDTO.setCustomerStrasse(additionalCost.getContract().getCustomer().getStrasse());
				addCostDTO.setCustomerOrtPlz(additionalCost.getContract().getCustomer().getPlz() + " "
						+ additionalCost.getContract().getCustomer().getOrt());
			}
			list.add(addCostDTO);
		});
		return list.stream().collect(Collectors.toList());
	}

	@Override
	public AdditionalCostsDTO getAdditionalCostsById(@PathVariable("id") Long id) {
		AdditionalCosts addCosts = additionalCostsRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("AdditionalCosts not found by id: %d", id)));

		AdditionalCostsDTO addCostsDTO = mapper.map(addCosts, AdditionalCostsDTO.class);

		addCostsDTO.setContractId(addCosts.getContract().getId());
		addCostsDTO.setContractStartDate(addCosts.getContract().getStartDate());
		addCostsDTO.setContractEndDate(addCosts.getContract().getEndDate());
		addCostsDTO.setContractStatusText(addCosts.getContract().getStatusText());
		addCostsDTO.setContractQuitPeriode(addCosts.getContract().getFrist());
		addCostsDTO.setCustomerId(addCosts.getContract().getCustomer().getId());
		addCostsDTO.setCustomerAndrede(addCosts.getContract().getCustomer().getAnrede());
		addCostsDTO.setCustomerVornameName(addCosts.getContract().getCustomer().getVorname() + " "
				+ addCosts.getContract().getCustomer().getName());

		if (addCosts.getContract().getCustomer().isUseCompanyAddress()
				&& addCosts.getContract().getCustomer().getFirmenAnschrift() != null) {
			String[] arrayOfStr = addCosts.getContract().getCustomer().getFirmenAnschrift().split(",");
			if (arrayOfStr.length > 1) {
				addCostsDTO.setCustomerStrasse(arrayOfStr[0] != null ? arrayOfStr[0].trim() : "");
				addCostsDTO.setCustomerOrtPlz(arrayOfStr[1] != null ? arrayOfStr[1].trim() : "");
			} else {
				addCostsDTO.setCustomerStrasse("n/a");
				addCostsDTO.setCustomerOrtPlz("n/a");
			}

		} else {
			addCostsDTO.setCustomerStrasse(addCosts.getContract().getCustomer().getStrasse());
			addCostsDTO.setCustomerOrtPlz(addCosts.getContract().getCustomer().getPlz() + " "
					+ addCosts.getContract().getCustomer().getOrt());
		}
		return addCostsDTO;
	}

	@Override
	public AdditionalCostsDTO create(@PathVariable("id") Long id, @Validated @RequestBody AdditionalCostsDTO addCostDTO)
			throws ResourceNotFoundException {
		return contractRepository.findById(id).map(contract -> {
			AdditionalCosts addCosts = mapper.map(addCostDTO, AdditionalCosts.class);
			addCosts.setContract(contract);
			return mapper.map(additionalCostsRepository.save(addCosts), AdditionalCostsDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("ContractId " + id + " not found"));
	}

	public AdditionalCostsDTO update(@PathVariable("id") Long id,
			@Validated @RequestBody AdditionalCostsDTO additionalCostsDTO) throws ResourceNotFoundException {
		AdditionalCosts addCosts = additionalCostsRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("AdditionalCosts not found by id: %d", id)));

		// Lokaler Mapper, um Konflikte zu vermeiden
		ModelMapper localMapper = new ModelMapper();
		localMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		// Explizites Mapping ohne id und contract
		localMapper.createTypeMap(AdditionalCostsDTO.class, AdditionalCosts.class).addMappings(mapper -> {
			mapper.skip(AdditionalCosts::setId);
			mapper.skip(AdditionalCosts::setContract);
			mapper.skip(AdditionalCosts::setQrReferenceCode);
		});

		localMapper.map(additionalCostsDTO, addCosts);

		return localMapper.map(additionalCostsRepository.save(addCosts), AdditionalCostsDTO.class);
	}

	@Override
	public Long delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
		AdditionalCosts addCosts = additionalCostsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AdditionalCosts not found on : " + id));

		if (!addCosts.getStatusText().equals("verrechnet")) {
			additionalCostsRepository.delete(addCosts);
		}
		return id;
	}
}
