package com.ebox3.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ebox3.server.exception.ForbiddenException;
import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Contract;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.dto.EboxDTO;
import com.ebox3.server.repo.ContractHistoryRepository;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.EBoxRepository;
import com.ebox3.server.repo.PaymentRepository;
import com.ebox3.server.service.EBoxService;

@Service
public class EBoxServiceImpl extends HelpFunctions implements EBoxService {

	@Autowired
	EBoxRepository eBoxRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	ContractHistoryRepository historyRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String, Object> getAllEBox(String search, int page, int size)

	{
		Map<String, Object> response = new HashMap<>();
		try {
			List<EboxDTO> listOfEboxs = new ArrayList<EboxDTO>();
			Pageable requestedPage = PageRequest.of(page, size, Sort.by("boxnumber"));
			PagedListHolder<EboxDTO> eboxDTO = null;

			if (search == null) {
				Page<Ebox> eboxs = eBoxRepository.findAll(requestedPage);
				eboxs.getContent().forEach(ebox -> {
					EboxDTO obj = mapper.map(ebox, EboxDTO.class);
					listOfEboxs.add(obj);
				});

				listOfEboxs.stream().sorted(Comparator.comparing(EboxDTO::getBoxNumber)).distinct()
						.collect(Collectors.toList());

				response.put("eboxes", listOfEboxs);
				response.put("currentPage", eboxs.getNumber());
				response.put("totalItems", eboxs.getTotalElements());
				response.put("totalPages", eboxs.getTotalPages());

			} else {
				List<Ebox> eboxes = null;
				if (StringUtils.isNumeric(search)) {
					eboxes = eBoxRepository.findByBoxnumber(Long.valueOf(search));
				} else {
					eboxes = eBoxRepository.findByBoxtypeContainingIgnoreCaseOrStatusTextContainingIgnoreCase(search,
							search);
				}

				eboxes.forEach(ebox -> {
					EboxDTO obj = mapper.map(ebox, EboxDTO.class);
					listOfEboxs.add(obj);
				});

				List<EboxDTO> listOfEBoxesSorted = listOfEboxs.stream()
						.sorted(Comparator.comparing(EboxDTO::getBoxNumber)).collect(Collectors.toList());

				eboxDTO = new PagedListHolder<EboxDTO>(listOfEBoxesSorted);
				eboxDTO.setPageSize(size);
				eboxDTO.setPage(page);

				response.put("eboxes", eboxDTO.getPageList());
				response.put("currentPage", eboxDTO.getPage());
				response.put("totalItems", eboxDTO.getNrOfElements());
				response.put("totalPages", eboxDTO.getPageCount());

			}
			return response;
		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public EboxDTO getEBoxById(Long id) {
		Ebox ebox = eBoxRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Ebox by id %d not found", id)));

		SimpleDateFormat formatDatum = new SimpleDateFormat("dd.MM.yyyy");

		EboxDTO eBoxDTO = mapper.map(ebox, EboxDTO.class);
		eBoxDTO.setStartDate(ebox.getStartDate() != null ? ebox.getStartDate() : null);
		eBoxDTO.setEndDate(ebox.getEndDate() != null ? ebox.getEndDate() : null);
		// eBoxDTO.setStartDate(ebox.getContract() != null ?
		// ebox.getContract().getStartDate() : null);
		// eBoxDTO.setEndDate(ebox.getContract() != null ?
		// ebox.getContract().getEndDate() : null);
		eBoxDTO.setTerminateDate(ebox.getTerminateDate() != null ? ebox.getTerminateDate() : null);
		eBoxDTO.setAnschrift(ebox.getContract() != null ? ebox.getContract().getCustomer().getAnrede() + " "
				+ ebox.getContract().getCustomer().getVorname() + " " + ebox.getContract().getCustomer().getName()
				: "");
		eBoxDTO.setContractId(ebox.getContract() != null ? ebox.getContract().getId() : null);

		if (ebox.getContract() != null) {
			eBoxDTO.setLaufzeit(formatDatum.format(ebox.getContract().getStartDate()) + " - "
					+ formatDatum.format(ebox.getContract().getEndDate()));
		} else {
			eBoxDTO.setLaufzeit("Vertrag beendet");
		}

		return eBoxDTO;
	}

	@Override
	public Iterable<EboxDTO> getAllElectricEBox() {
		List<Ebox> list = eBoxRepository.findAll();
		List<Ebox> list1 = list.stream().sorted(Comparator.comparing(Ebox::getBoxNumber))
				.filter(ebox -> ebox.getElectricMeter() == null).collect(Collectors.toList());
		return list1.stream().map(ebox -> mapper.map(ebox, EboxDTO.class)).collect(Collectors.toList());
	}

	@Override
	public Iterable<EboxDTO> getAllEBoxList() {
		List<Ebox> res = eBoxRepository.findAll(Sort.by("boxnumber"));
		return res.stream().map(ebox -> mapper.map(ebox, EboxDTO.class)).collect(Collectors.toList());
	}

	@Override
	public EboxDTO create(EboxDTO eboxDTO) {
		Ebox ebox = eBoxRepository.save(mapper.map(eboxDTO, Ebox.class));
		return mapper.map(ebox, EboxDTO.class);
	}

	@Override
	public EboxDTO update(Long id, EboxDTO eboxDTO) throws ResourceNotFoundException {
		Ebox ebox = eBoxRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Ebox not found by id: %d", id)));
		// ebox.setContract(null);
		// ebox.setStartDate(null);
		// ebox.setEndDate(null);
		// ebox.setStatus(false);
		// ebox.setStatusText("frei");

		mapper.typeMap(EboxDTO.class, Ebox.class).addMappings(mapper -> {
			mapper.skip(Ebox::setId);
			mapper.skip(Ebox::setContract);
			// mapper.skip(Ebox::setStartDate);
			// mapper.skip(Ebox::setEndDate);
			// mapper.skip(Ebox::setStatus);
			// mapper.skip(Ebox::setStatusText);
		}).map(eboxDTO, ebox);
		return mapper.map(eBoxRepository.save(ebox), EboxDTO.class);
	}

	@Override
	public EboxDTO updateEboxByContractId(Long id, EboxDTO eboxDTO) throws ResourceNotFoundException {

		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Contract not found by id: %d", id)));

		return eBoxRepository.findById(eboxDTO.getId()).map(ebox -> {
			ebox.setContract(contract);
			mapper.typeMap(EboxDTO.class, Ebox.class).addMappings(mapper -> {
				mapper.skip(Ebox::setId);
				mapper.skip(Ebox::setContract);
			}).map(eboxDTO, ebox);

			return mapper.map(eBoxRepository.save(ebox), EboxDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("ContractId " + eboxDTO.getId() + " not found"));
	}

	@Override
	public Iterable<EboxDTO> updateAllPurchasingEboxByContractId(Long id, Ebox[] eboxs) {
		if (!contractRepository.existsById(id)) {
			throw new ResourceNotFoundException("ContractId " + id + " not found");
		}
		List<EboxDTO> eBoxListDTO = new ArrayList<EboxDTO>();
		Arrays.stream(eboxs).forEach(ebox -> {
			contractRepository.findById(id).map(contract -> {
				ebox.setContract(contract);
				return eBoxListDTO.add(mapper.map(eBoxRepository.save(ebox), EboxDTO.class));
			}).orElseThrow(() -> new ResourceNotFoundException("EboxId " + ebox.getId() + " not found"));
		});
		return eBoxListDTO;
	}

	@Override
	public EboxDTO updateStatusEbox(Long id, EboxDTO eboxRequest) {

		if (!eBoxRepository.existsById(id)) {
			throw new ResourceNotFoundException("EboxId " + id + " not found");
		}

		return eBoxRepository.findById(id).map(ebox -> {

			if (!eboxRequest.getStatusText().contentEquals(ebox.getStatusText())
					&& eboxRequest.getStatusText().contentEquals("gebucht")) {
				if (!ebox.getStatusText().contentEquals(eboxRequest.getStatusText())) {

					if (eboxRequest.getTerminateDate().before(ebox.getContract().getStartDate())) {
						throw new ResourceNotFoundException(
								"Das Startdatum darf nicht vor dem Vertragsstartdatum liegen!");
					}

					if (eboxRequest.getTerminateDate().after(ebox.getContract().getStartDate())) {
						throw new ResourceNotFoundException(
								"Das Startdatum darf nicht nach dem Vertragsstartdatum liegen!");
					}

					ebox.setStatusText("gebucht");
					ebox.setStatus(true);
					ebox.setStartDate(eboxRequest.getTerminateDate()); // Datum der Box wird neues Startdatum auf Box
																		// und Vertrag
					ebox.setEndDate(eboxRequest.getEndDate());
					ebox.setTerminateDate(null);
					Contract con = ebox.getContract();
					con.setStatusText("Vertrag");
					con.setStartDate(eboxRequest.getTerminateDate());// Datum der Box wird neues Startdatum auf Box und
																		// Vertrag
					con.setActive(true);

					try {
						paymentRepository.save(addPaymentRecordByEbox(ebox));
					} catch (Exception e) {
						throw new ForbiddenException(e.getMessage());
					}
					historyRepository.save(copyToHistory(ebox)); // Save in History
					contractRepository.save(con);
				}
			} else if (!eboxRequest.getStatusText().contentEquals(ebox.getStatusText())
					&& eboxRequest.getStatusText().contentEquals("frei")) {
				if (!ebox.getStatusText().contentEquals("gebucht")) {
					ebox.setStatusText("frei");
					ebox.setStatus(false);
					ebox.setStartDate(null);
					ebox.setEndDate(null);
					ebox.setTerminateDate(null);
					ebox.setContract(null);
				}
			} else if (!eboxRequest.getStatusText().contentEquals(ebox.getStatusText())
					&& eboxRequest.getStatusText().contentEquals("gekündigt")) {
				if (!ebox.getStatusText().contentEquals("reserviert")) {
					ebox.setStatusText("gekündigt");
					ebox.setStartDate(eboxRequest.getStartDate());
					ebox.setEndDate(eboxRequest.getEndDate());
					// ebox.setTerminateDate(calcLastRentDay(eboxRequest.getTerminateDate(),
					// ebox.getContract().getFrist())); // Letzer Miettag
					ebox.setTerminateDate(eboxRequest.getLastRentDate());
					ebox.setStatus(false);

					try {
						paymentRepository.save(addPaymentRecordByEbox(ebox));
					} catch (Exception e) {
						throw new ForbiddenException(e.getMessage());
					}
					historyRepository.save(copyToHistory(ebox)); // Save in History cancel
					ebox.setContract(null);
				}
			}
			return mapper.map(eBoxRepository.save(ebox), EboxDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("EboxId " + id + " not found"));
	}

	@Override
	public Long delete(Long id) throws ResourceNotFoundException {
		Ebox ebox = eBoxRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ebox not found on :: " + id));
		eBoxRepository.delete(ebox);
		return id;
	}

}
