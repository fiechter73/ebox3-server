package com.ebox3.server.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.ebox3.server.exception.InternalServerException;
import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.Customer;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.ElectricMeter;
import com.ebox3.server.model.dto.ElectricMeterDTO;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;
import com.ebox3.server.model.dto.ElectricPeriodDTO;
import com.ebox3.server.repo.CustomerRepository;
import com.ebox3.server.repo.EBoxRepository;
import com.ebox3.server.repo.ElectricMeterRepository;
import com.ebox3.server.service.ElectricMeterService;

@Service
public class ElectricMeterServiceImpl implements ElectricMeterService {

	@Autowired
	ElectricMeterRepository electricMeterRepository;

	@Autowired
	EBoxRepository eboxRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String, Object> getAllElectricMeters(String search, int page, int size)

	{
		Map<String, Object> response = new HashMap<>();
		try {
			List<ElectricMeterDTO> listOfElectricMeters = new ArrayList<ElectricMeterDTO>();
			Pageable requestedPage = PageRequest.of(page, size, Sort.by("electricMeterNumber"));
			PagedListHolder<ElectricMeterDTO> electricMeterDTO = null;

			if (search == null) {
				Page<ElectricMeter> electricMeters = electricMeterRepository.findAll(requestedPage);
				electricMeters.getContent().forEach(electricMeter -> {
					ElectricMeterDTO obj = mapper.map(electricMeter, ElectricMeterDTO.class);
					listOfElectricMeters.add(obj);
				});

				listOfElectricMeters.stream().sorted(Comparator.comparing(ElectricMeterDTO::getElectricMeterNumber))
						.distinct().collect(Collectors.toList());

				response.put("electricmeters", listOfElectricMeters);
				response.put("currentPage", electricMeters.getNumber());
				response.put("totalItems", electricMeters.getTotalElements());
				response.put("totalPages", electricMeters.getTotalPages());

			} else {
				List<ElectricMeter> electricMeteres = null;
				if (StringUtils.isNumeric(search)) {
					electricMeteres = electricMeterRepository.findByElectricMeterNumber(Long.valueOf(search));
				} else {
					electricMeteres = electricMeterRepository
							.findByElectricMeterNameContainingIgnoreCaseOrElectricMeterBemerkungenContainingIgnoreCase(
									search, search);
				}

				electricMeteres.forEach(electricMeter -> {
					ElectricMeterDTO obj = mapper.map(electricMeter, ElectricMeterDTO.class);
					listOfElectricMeters.add(obj);
				});

				List<ElectricMeterDTO> listOfElectricMetersSorted = listOfElectricMeters.stream()
						.sorted(Comparator.comparing(ElectricMeterDTO::getElectricMeterNumber))
						.collect(Collectors.toList());

				electricMeterDTO = new PagedListHolder<ElectricMeterDTO>(listOfElectricMetersSorted);
				electricMeterDTO.setPageSize(size);
				electricMeterDTO.setPage(page);

				response.put("electricmeters", electricMeterDTO.getPageList());
				response.put("currentPage", electricMeterDTO.getPage());
				response.put("totalItems", electricMeterDTO.getNrOfElements());
				response.put("totalPages", electricMeterDTO.getPageCount());

			}

			return response;
		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public Map<String, Object> getElectricMeterDetails(boolean showAll, Long id, int page, int size) {

		try {

			List<ElectricPeriodDTO> listEP = new ArrayList<ElectricPeriodDTO>();

			List<ElectricMeter> list = new ArrayList<>();
			PagedListHolder<ElectricPeriodDTO> electricPeriodDTO = null;
			Map<String, Object> response = new HashMap<>();

			if (id == null) {
				list = electricMeterRepository.findAll();
			} else {
				electricMeterRepository.findById(id).ifPresent(list::add);
			}
			list.forEach(eM -> {
				eM.getElectricPeriods().forEach(ep -> {
					ElectricPeriodDTO epDTO = mapper.map(ep, ElectricPeriodDTO.class);
					epDTO.setIdElectricMeter(eM.getId());
					epDTO.setGebaeudeInfos(eM.getElectricMeterNumber() + " " + eM.getElectricMeterName());
					epDTO.setElectricMeterNumber(eM.getElectricMeterNumber());
					epDTO.setBemerkungenElektroMeter(eM.getElectricMeterBemerkungen());

					if (ep.getCustomerId() != null) {
						Optional<Customer> customer = customerRepository.findById(ep.getCustomerId());
						if (customer.isPresent()) {
							Customer cust = customer.get();
							if (cust.isUseCompanyAddress() && cust.getFirmenAnschrift() != null) {
								String firma = cust.getFirmenName() != null ? cust.getFirmenName() : "";
								String fAnschrift = cust.getFirmenAnschrift() != null ? cust.getFirmenAnschrift() : "";
								String anrede = cust.getAnrede() != null ? cust.getAnrede() : "";
								String vorname = cust.getVorname() != null ? cust.getVorname() : "";
								String name = cust.getName() != null ? cust.getName() : "";
								String plz = cust.getPlz() != null ? cust.getPlz() : "";
								String ort = cust.getOrt() != null ? cust.getOrt() : "";
								epDTO.setCustomerInformation(firma + " " + fAnschrift + " " + anrede + " " + vorname
										+ " " + name + " " + plz + " " + ort);
							} else {
								String anrede = cust.getAnrede() != null ? cust.getAnrede() : "";
								String vorname = cust.getVorname() != null ? cust.getVorname() : "";
								String name = cust.getName() != null ? cust.getName() : "";
								String plz = cust.getPlz() != null ? cust.getPlz() : "";
								String ort = cust.getOrt() != null ? cust.getOrt() : "";
								epDTO.setCustomerInformation(
										anrede + " " + vorname + " " + name + " " + plz + " " + ort);
							}
						}
					} else {
						epDTO.setCustomerInformation("Zähler hat aktuell keine Mieter!");
					}
					listEP.add(epDTO);

				});
			});

			List<ElectricPeriodDTO> listOfElectricPeriodsSorted = null;
			if (showAll) {
				listOfElectricPeriodsSorted = listEP.stream()
						.sorted(Comparator.comparingDouble(ElectricPeriodDTO::getZaehlerStand))
						.collect(Collectors.toList());
			} else {
				listOfElectricPeriodsSorted = listEP.stream().filter(
						str -> (str.getStatusText().equals("offen")) || (str.getStatusText().equals("verrechnet")))
						.sorted(Comparator.comparingDouble(ElectricPeriodDTO::getZaehlerStand))
						.collect(Collectors.toList());
			}

			List<ElectricPeriodDTO> finalList = listOfElectricPeriodsSorted.stream()
					.sorted(Comparator.comparing(ElectricPeriodDTO::getElectricMeterNumber))
					.collect(Collectors.toList());

			electricPeriodDTO = new PagedListHolder<ElectricPeriodDTO>(finalList);
			electricPeriodDTO.setPage(page);
			electricPeriodDTO.setPageSize(size);

			response.put("electricPeriod", electricPeriodDTO.getPageList());
			response.put("currentPage", electricPeriodDTO.getPage());
			response.put("totalItems", electricPeriodDTO.getNrOfElements());
			response.put("totalPages", electricPeriodDTO.getPageCount());

			return response;

		} catch (Exception e) {
			throw new InternalServerException("Interner Server Error", e);
		}

	}

	@Override
	public Iterable<ElectricMeterPeriodDTO> getAllElectricMeterDetails(boolean showAll) {

		List<ElectricMeter> list = electricMeterRepository.findAll();
		List<ElectricMeterPeriodDTO> electricMeterListWithPeriods = new ArrayList<ElectricMeterPeriodDTO>();

		list.forEach(eM -> {
			ElectricMeterPeriodDTO eMlwP = new ElectricMeterPeriodDTO();
			eMlwP.setId(eM.getId());
			eMlwP.setElectricMeterName(eM.getElectricMeterName());
			eMlwP.setElectricMeterBemerkungen(eM.getElectricMeterBemerkungen());
			eMlwP.setElectricMeterNumber(eM.getElectricMeterNumber());

			List<ElectricPeriodDTO> pList = new ArrayList<ElectricPeriodDTO>();
			eM.getElectricPeriods().forEach(period -> {
				ElectricPeriodDTO epDTO = mapper.map(period, ElectricPeriodDTO.class);
				epDTO.setIdElectricMeter(eM.getId());
				epDTO.setGebaeudeInfos(eM.getElectricMeterNumber() + " " + eM.getElectricMeterName());
				epDTO.setBemerkungenElektroMeter(eM.getElectricMeterBemerkungen());
				if (period.getCustomerId() != null) {
					Optional<Customer> customer = customerRepository.findById(period.getCustomerId());
					if (customer.isPresent()) {
						Customer cust = customer.get();
						if (cust.isUseCompanyAddress() && cust.getFirmenAnschrift() != null) {
							String firma = cust.getFirmenName() != null ? cust.getFirmenName() : "";
							String fAnschrift = cust.getFirmenAnschrift() != null ? cust.getFirmenAnschrift() : "";
							String anrede = cust.getAnrede() != null ? cust.getAnrede() : "";
							String vorname = cust.getVorname() != null ? cust.getVorname() : "";
							String name = cust.getName() != null ? cust.getName() : "";
							String plz = cust.getPlz() != null ? cust.getPlz() : "";
							String ort = cust.getOrt() != null ? cust.getOrt() : "";
							epDTO.setCustomerInformation(firma + " " + fAnschrift + " " + anrede + " " + vorname + " "
									+ name + " " + plz + " " + ort);
						} else {
							String anrede = cust.getAnrede() != null ? cust.getAnrede() : "";
							String vorname = cust.getVorname() != null ? cust.getVorname() : "";
							String name = cust.getName() != null ? cust.getName() : "";
							String plz = cust.getPlz() != null ? cust.getPlz() : "";
							String ort = cust.getOrt() != null ? cust.getOrt() : "";
							epDTO.setCustomerInformation(anrede + " " + vorname + " " + name + " " + plz + " " + ort);
						}
					}
				} else {
					epDTO.setCustomerInformation("Zähler hat aktuell keine Mieter!");
				}
				pList.add(epDTO);

			});

			if (showAll) {
				eMlwP.setElectricPeriodDTO(
						pList.stream().sorted(Comparator.comparingDouble(ElectricPeriodDTO::getZaehlerStand))
								.collect(Collectors.toList()));
			} else {
				eMlwP.setElectricPeriodDTO(pList.stream().filter(
						str -> (str.getStatusText().equals("offen")) || (str.getStatusText().equals("verrechnet")))
						.sorted(Comparator.comparingDouble(ElectricPeriodDTO::getZaehlerStand))
						.collect(Collectors.toList()));
			}
			electricMeterListWithPeriods.add(eMlwP);
		});

		List<ElectricMeterPeriodDTO> eleList = electricMeterListWithPeriods.stream()
				.sorted(Comparator.comparing(ElectricMeterPeriodDTO::getElectricMeterNumber))
				.collect(Collectors.toList());
		return eleList;
	}

	@Override
	public Iterable<ElectricMeterDTO> getAllElectricMeterList() {

		List<ElectricMeterDTO> list = new ArrayList<ElectricMeterDTO>();
		List<ElectricMeter> electricMeters = electricMeterRepository.findAll();

		electricMeters.forEach(electricMeter -> {

			ElectricMeterDTO emDTO = new ElectricMeterDTO();
			emDTO.setId(electricMeter.getId());
			if (electricMeter.getEbox().getContract() != null) {
				emDTO.setAnrede(electricMeter.getEbox().getContract().getCustomer().getAnrede());
				emDTO.setName(electricMeter.getEbox().getContract().getCustomer().getVorname() + " "
						+ electricMeter.getEbox().getContract().getCustomer().getName());

				emDTO.setCustomerId(electricMeter.getEbox().getContract().getCustomer().getId());
				emDTO.setAnschriftMieter(electricMeter.getEbox().getContract().getCustomer().getId() + " - "
						+ electricMeter.getEbox().getContract().getCustomer().getAnrede() + " "
						+ electricMeter.getEbox().getContract().getCustomer().getVorname() + " "
						+ electricMeter.getEbox().getContract().getCustomer().getName());
			} else {
				emDTO.setAnschriftMieter("");
			}

			if (electricMeter.getEbox().getContract() != null) {
				emDTO.setAnschriftMieterAdr(electricMeter.getEbox().getContract().getCustomer().getStrasse());
				emDTO.setAnschriftMieterOrt(electricMeter.getEbox().getContract().getCustomer().getPlz() + " "
						+ electricMeter.getEbox().getContract().getCustomer().getOrt());

				emDTO.setUseCompanyAddress(electricMeter.getEbox().getContract().getCustomer().isUseCompanyAddress());
				emDTO.setFirma(electricMeter.getEbox().getContract().getCustomer().getFirmenName());
				emDTO.setAnschriftMieterFirma(electricMeter.getEbox().getContract().getCustomer().getFirmenAnschrift());
			} else {
				emDTO.setAnschriftMieterAdr("");
				emDTO.setAnschriftMieterOrt("");
				emDTO.setAnschriftMieterFirma("");
			}

			emDTO.setEboxBezeichnung(
					electricMeter.getEbox().getBoxNumber() + " - " + electricMeter.getEbox().getBoxType());
			emDTO.setVertragsdetails(electricMeter.getEbox().getContract() != null
					? electricMeter.getEbox().getContract().getStatusText()
					: "");
			emDTO.setElectricMeterNumber(electricMeter.getElectricMeterNumber());
			emDTO.setElectricMeterName(electricMeter.getElectricMeterName());
			emDTO.setElectricMeterBemerkungen(electricMeter.getElectricMeterBemerkungen());
			emDTO.setStromPrice(electricMeter.getEbox().getStromPrice());
			list.add(emDTO);

		});
		return list;
	}

	@Override
	public ElectricMeterDTO getElectricMetertById(Long id) {
		ElectricMeter electricMeter = electricMeterRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Electric Meter by id %d not found", id)));
		return mapper.map(electricMeter, ElectricMeterDTO.class);
	}

	@Override
	public ElectricMeterDTO create(Long id, ElectricMeterDTO electricMeterDTO) {

		if (id == null) {
			ElectricMeter eleMeter = electricMeterRepository.save(mapper.map(electricMeterDTO, ElectricMeter.class));
			return mapper.map(eleMeter, ElectricMeterDTO.class);
		}

		return eboxRepository.findById(id).map(ebox -> {
			electricMeterDTO.setEbox(ebox);
			ElectricMeter eleMeter = electricMeterRepository.save(mapper.map(electricMeterDTO, ElectricMeter.class));
			return mapper.map(eleMeter, ElectricMeterDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("EboxId " + id + " not found"));
	}

	@Override
	public ElectricMeterDTO updateElectricMeterByEboxId(Long id, ElectricMeterDTO electricMeterDTO) {

		Ebox ebox = eboxRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Ebox not found by id: %d", id)));

		return electricMeterRepository.findById(electricMeterDTO.getId()).map(electricMeter -> {
			mapper.typeMap(ElectricMeterDTO.class, ElectricMeter.class).addMappings(mapper -> {
				mapper.skip(ElectricMeter::setId);
			}).map(electricMeterDTO, electricMeter);
			electricMeter.setEbox(ebox);
			return mapper.map(electricMeterRepository.save(electricMeter), ElectricMeterDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("ElectroMeter " + electricMeterDTO.getId() + " not found"));
	}

	@Override
	public Long delete(Long id) throws ResourceNotFoundException {
		ElectricMeter electricMeter = electricMeterRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CusElectricMetertomer not found on :: " + id));
		if (electricMeter.getElectricPeriods().size() == 0) {
			electricMeterRepository.delete(electricMeter);
		}
		return id;
	}

}
