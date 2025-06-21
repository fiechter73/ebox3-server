package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ebox3.server.exception.ForbiddenException;
import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.dto.ContractDTO;
import com.ebox3.server.model.dto.ContractHistoryDTO;
import com.ebox3.server.model.dto.ContractSelectionDTO;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.repo.ContractHistoryRepository;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.repo.PaymentRepository;
import com.ebox3.server.service.ContractService;

@Service
public class ContractServiceImpl extends HelpFunctions implements ContractService {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContractHistoryRepository historyRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Iterable<Map<Long, ContractSelectionDTO>> getContractList() {
		List<Map<Long, ContractSelectionDTO>> res = contractRepository.contractSelection();
		return res.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public Iterable<Map<Long, String>> getContractEboxList() {
		List<Map<Long, String>> res = contractRepository.contractSelectionEbox();
		return res.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> getAllContracts(String search, Boolean isContract, int page, int size) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<ContractDTO> listOfContracts = new ArrayList<ContractDTO>();
			List<ContractDTO> listOfContractsSorted = new ArrayList<ContractDTO>();
			Pageable paging = PageRequest.of(page, size, Sort.by("id"));
			List<Customer> customers = null;
			Page<Contract> contracts = null;
			PagedListHolder<ContractDTO> contractsDTO = null;

			if (search == null) {

				// contracts = contractRepository.findAll(paging);
				if (isContract) {
					contracts = contractRepository.findContractNotKündigungAndAbsage(paging);
				} else {
					contracts = contractRepository.findContractKündigungAbsage(paging);
				}

				contracts.getContent().forEach(contract -> {
					ContractDTO coDTO = mapper.map(contract, ContractDTO.class);
					coDTO.setCountedEbox(contractRepository.countBox(contract.getId()));
					coDTO.seteBoxNrs(contractRepository.showBoxNumbers(contract.getId()));

					coDTO.setCustName(contract.getCustomer().getName() + " " + contract.getCustomer().getVorname() + " "
							+ contract.getCustomer().getStrasse() + " " + contract.getCustomer().getPlz() + " "
							+ contract.getCustomer().getOrt());
					coDTO.setCustAnschrift(contract.getCustomer().getStrasse() + " " + contract.getCustomer().getPlz()
							+ " " + contract.getCustomer().getOrt());
					listOfContracts.add(coDTO);
				});
				response.put("contracts", listOfContracts);
				response.put("currentPage", contracts.getNumber());
				response.put("totalItems", contracts.getTotalElements());
				response.put("totalPages", contracts.getTotalPages());

			} else {
				customers = customerRepository.findCustomerFullSearch(search);

				customers.forEach(customer -> {
					customer.getContracts().forEach(contract -> {
						Optional<Contract> con = contractRepository.findById(contract.getId());
						ContractDTO coDTO = mapper.map(con.get(), ContractDTO.class);
						coDTO.setCountedEbox(contractRepository.countBox(contract.getId()));
						coDTO.seteBoxNrs(contractRepository.showBoxNumbers(contract.getId()));

						coDTO.setCustName(customer.getName() + " " + customer.getVorname() + " " + customer.getStrasse()
								+ " " + customer.getPlz() + " " + customer.getOrt());
						coDTO.setCustAnschrift(
								customer.getStrasse() + " " + customer.getPlz() + " " + customer.getOrt());
						listOfContracts.add(coDTO);

					});
				});
				listOfContractsSorted = listOfContracts.stream().sorted(Comparator.comparing(ContractDTO::getCustName))
						.collect(Collectors.toList());

				contractsDTO = new PagedListHolder<ContractDTO>(listOfContractsSorted);
				contractsDTO.setPageSize(size);
				contractsDTO.setPage(page);

				response.put("contracts", contractsDTO.getPageList());
				response.put("currentPage", contractsDTO.getPage());
				response.put("totalItems", contractsDTO.getNrOfElements());
				response.put("totalPages", contractsDTO.getPageCount());

			}

			return response;
		} catch (Exception e) {
			if (e instanceof InvalidDataAccessResourceUsageException) {
				// Hier wird bewusst ein leerer Response zurückgegeben
				// dies im Fall, dass Steuerzeichen FullSearch ohne
				// Paramenterwert verwendet wird; z.b. +/-/>/<
				return response;
			} else {
				throw new InternalServerException("Internal Server Error", e);
			}

		}

	}

	@Override
	public ContractDTO getContractById(Long id) {
	    Contract contract = contractRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException(String.format("Contract by id %d not found", id)));

	    ContractDTO dto = mapper.map(contract, ContractDTO.class);

	    var customer = contract.getCustomer();
	    if (customer != null) {
	        dto.setCustId(customer.getId());
	        dto.setCustName(String.format("%s %s %s",
	                customer.getAnrede(),
	                customer.getVorname(),
	                customer.getName()));

	        dto.setCustAnschrift(String.format("%s %s %s",
	                customer.getStrasse(),
	                customer.getPlz(),
	                customer.getOrt()));
	    }

	    if (contract.getEboxs() != null && !contract.getEboxs().isEmpty()) {
	        List<EboxDTO> eboxes = contract.getEboxs().stream()
	                .map(ebox -> mapper.map(ebox, EboxDTO.class))
	                .collect(Collectors.toList());
	        dto.setEboxs(eboxes);
	    }

	    return dto;
	}

	@Override
	public Iterable<ContractHistoryDTO> getHistory(Long id) {
		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Contract by id %d not found", id)));

		List<ContractHistoryDTO> list = new ArrayList<ContractHistoryDTO>();

		contract.getContractHistory().forEach(contractHistory -> {
			list.add(mapper.map(contractHistory, ContractHistoryDTO.class));
		});

		return list.stream().sorted(Comparator.comparing(ContractHistoryDTO::getId)).distinct()
				.collect(Collectors.toList());
	}

	@Override
	public ContractDTO create(ContractDTO contractDTO) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(contractDTO.getCustId())
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Customer not found for id: %d", contractDTO.getCustId())));
		Contract contract = mapper.map(contractDTO, Contract.class);
		contract.setCustomer(customer);
		return mapper.map(contractRepository.save(contract), ContractDTO.class);
	}

	@Override
	public ContractDTO update(Long id, ContractDTO contractDTO) throws ResourceNotFoundException {
	    Contract contract = contractRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    String.format("Contract not found by id: %d", id)));

	    // Manuelle Aktualisierungen wichtiger Felder
	    //		if (contractDTO.isActive()) {
	    //	        contract.setActive(true);
	    //	    }

	    //    if (contractDTO.getStatusText() != null) {
	    //        contract.setStatusText(contractDTO.getStatusText());
	    //	    }

	    //	    String bemerkungen = contractDTO.getBemerkungen();
	    //	    if (bemerkungen != null && !bemerkungen.isBlank()) {
	    //	        contract.setBemerkungen(bemerkungen);
	    //	    }

	    // Lokaler Mapper für restliches Mapping (ohne id, customer etc.)
	    ModelMapper mapper = new ModelMapper();
	    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	    mapper.createTypeMap(ContractDTO.class, Contract.class)
	          .addMappings(m -> {
	              m.skip(Contract::setId);
	            //  m.skip(Contract::setActive);
	             // m.skip(Contract::setBemerkungen);
	              m.skip(Contract::setCustomer);
	              m.skip(Contract::setQrReferenceCodeRent);
	              m.skip(Contract::setQrReferenceCodeDeposit);
	          });

	    mapper.map(contractDTO, contract);

	    contractRepository.save(contract);
	    return contractDTO;
	}

	
	
	@Override
	public ContractDTO updateContractCheckout(Long id, ContractDTO contractDTO) {

		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Contract not found by id: %d", id)));
		contract.setCheckOutDate(contractDTO.getCheckOutDate());

		try {
			paymentRepository.save(finalizePaymentRecordByContract(contract, contractDTO.getRetKaution()));
		} catch (Exception e) {
			throw new ForbiddenException(e.getMessage());
		}

		contractRepository.save(contract);
		ContractDTO dto = mapper.map(contract, ContractDTO.class);
		dto.setCustId(contractDTO.getCustId());
		return dto;
	}

	public Long delete(Long id) throws ResourceNotFoundException {
		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contract not found on :: " + id));
		if (contract.getEboxs().size() == 0) {
			contractRepository.delete(contract);
		}
		return id;
	}

	@Override
	public ContractDTO updateContractChange(Long id, ContractDTO contractDTO) {

		return contractRepository.findById(id).map(contract -> {

			if (!contract.getStatusText().contentEquals(contractDTO.getStatusText())
					&& contractDTO.getStatusText().equals("Vertrag")) {

				contract.setStatusText(contractDTO.getStatusText());
				contract.setStartDate(contractDTO.getStartDate());
				contract.setEndDate(contractDTO.getEndDate());
				contract.setTerminateDate(null);
				contract.setActive(true);
				contract.getCustomer().setStatusText("Vertragsnehmer");
				try {
					paymentRepository.save(addPaymentRecordByContract(contract, contractDTO.getFristDate()));
				} catch (Exception e) {
					throw new ForbiddenException(e.getMessage());
				}
				historyRepository.save(copyToHistory(contract)); // Save in History

				contract.getEboxs().forEach(ebox -> {
					ebox.setStartDate(contractDTO.getStartDate());
					ebox.setEndDate(contractDTO.getEndDate());
					ebox.setTerminateDate(null);
					ebox.setStatus(true);
					ebox.setStatusText("gebucht");
					ebox.setContract(contract);
				});

			} else if (!contract.getStatusText().contentEquals(contractDTO.getStatusText())
					&& contractDTO.getStatusText().equals("Absage")) {

				contract.setStatusText(contractDTO.getStatusText());
				contract.setStartDate(contractDTO.getStartDate());
				contract.setEndDate(contractDTO.getTerminateDate());
				contract.setActive(false);
				contract.getCustomer().setStatusText("Offertnehmer");
				contract.getEboxs().forEach(ebox -> {
					ebox.setStatus(false);
					if (ebox.getStatusText() != null) {
						ebox.setStatusText(ebox.getStatusText().equals("gekündigt") ? "gekündigt" : "frei");
					}
					ebox.setStartDate(null);
					ebox.setEndDate(null);
					ebox.setContract(null);
				});

			} else if (!contract.getStatusText().contentEquals(contractDTO.getStatusText())
					&& contractDTO.getStatusText().equals("Kündigung")) {

				contract.setStatusText(contractDTO.getStatusText());
				contract.setStartDate(contractDTO.getStartDate());
				contract.setTerminateDate(contractDTO.getTerminateDate());
				contract.setEndDate(contractDTO.getTerminateDate());
				contract.setActive(false);

				Optional<Contract> result = contract.getCustomer().getContracts().stream()
						.filter(con -> con.getStatusText().contentEquals("Vertrag")).findFirst();

				if (result.isPresent()) {
					contract.getCustomer().setStatusText("Vertragsnehmer");
				} else {
					contract.getCustomer().setStatusText("Kündigung");
				}

				try {
					paymentRepository.save(addPaymentRecordByContract(contract, contractDTO.getFristDate()));
				} catch (Exception e) {
					throw new ForbiddenException(e.getMessage());
				}
				historyRepository.save(copyToHistory(contract)); // Save in History

				contract.getEboxs().forEach(ebox -> {
					ebox.setEndDate(contractDTO.getTerminateDate());
					ebox.setTerminateDate(contractDTO.getFristDate());
					// ebox.setTerminateDate(calcLastRentDay(contractRequest.getTerminateDate(),
					// contractRequest.getFrist())); // Letzer Miettag
					ebox.setStatus(false);
					ebox.setStatusText("gekündigt");
					ebox.setContract(null);
				});

			} else if (!contract.getStatusText().contentEquals(contractDTO.getStatusText())
					&& contractDTO.getStatusText().equals("UpdateZahlungsStatus")) {
				try {
					paymentRepository.save(updatePaymentRecordByContract(contract));
				} catch (Exception e) {
					throw new ForbiddenException(e.getMessage());
				}
			} else if (!contract.getStatusText().contentEquals(contractDTO.getStatusText())
					&& contractDTO.getStatusText().equals("UpdateStartDate")) {
				try {
					contract.setStartDate(contractDTO.getStartDate());
					paymentRepository.save(updatePaymentRecordByStartDate(contract));
				} catch (Exception e) {
					throw new ForbiddenException(e.getMessage());
				}
			}
			ContractDTO dto = mapper.map(contractRepository.save(contract), ContractDTO.class);
			dto.setCustId(contractDTO.getCustId());
			return dto;
		}).orElseThrow(() -> new ResourceNotFoundException("ContractId " + id + " not found"));
	}

}
