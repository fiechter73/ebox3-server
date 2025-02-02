package com.ebox3.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebox3.server.exception.ResourceNotFoundException;
import com.ebox3.server.model.ElectricMeter;
import com.ebox3.server.model.ElectricPeriod;
import com.ebox3.server.model.dto.ElectricMeterPeriodDTO;
import com.ebox3.server.model.dto.ElectricPeriodDTO;
import com.ebox3.server.repo.ElectricMeterPeriodRepository;
import com.ebox3.server.repo.ElectricMeterRepository;
import com.ebox3.server.service.ElectricMeterPeriodService;

@Service
public class ElectricMeterPeriodServiceImpl implements ElectricMeterPeriodService {

	@Autowired
	ElectricMeterRepository electricMeterRepository;

	@Autowired
	ElectricMeterPeriodRepository electricMeterPeriodRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Iterable<ElectricMeterPeriodDTO> printElPeriods(Long id) {

		List<ElectricMeterPeriodDTO> empDTOList = new ArrayList<ElectricMeterPeriodDTO>();
		ElectricMeterPeriodDTO empDTO = new ElectricMeterPeriodDTO();

		ElectricMeter electricMeter = electricMeterRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Electric Meter by id %d not found", id)));

		empDTO.setId(electricMeter.getId());
		empDTO.setElectricMeterName(electricMeter.getElectricMeterName());
		empDTO.setElectricMeterNumber(electricMeter.getElectricMeterNumber());

		List<ElectricPeriodDTO> goupByElcMeterNumberList = groupByElectricMeterNumber(electricMeter);

		List<ElectricPeriodDTO> listOfOpenElectric = goupByElcMeterNumberList.stream()
				.filter(ePs -> Integer
						.parseInt(new SimpleDateFormat("yyyy")
								.format(ePs.getZaehlerToPeriode())) == Calendar.getInstance().get(Calendar.YEAR) - 1 // Ein
																														// Jahr
																														// zurück
						|| Integer.parseInt(new SimpleDateFormat("yyyy").format(ePs.getZaehlerToPeriode())) == Calendar
								.getInstance().get(Calendar.YEAR))
				.filter(str -> (str.getStatusText().equals("verrechnet") || str.getStatusText().equals("offen")))
				.sorted(Comparator.comparingDouble(ElectricPeriodDTO::getZaehlerStand)).collect(Collectors.toList());

		Optional<ElectricPeriodDTO> lastOfPaymentElectric = goupByElcMeterNumberList.stream()
				.filter(str -> (str.getStatusText().equals("bezahlt")))
				.max(Comparator.comparing(ElectricPeriodDTO::getZaehlerToPeriode));

		List<ElectricPeriodDTO> outputList = new ArrayList<ElectricPeriodDTO>();

		if (lastOfPaymentElectric.isPresent()) {
			outputList.add(lastOfPaymentElectric.get());
		}

		outputList.addAll(listOfOpenElectric);
		empDTO.setElectricPeriodDTO(outputList);
		empDTOList.add(empDTO);

		List<ElectricMeterPeriodDTO> output = empDTOList.stream().distinct().collect(Collectors.toList());

		return output;
	}

	private List<ElectricPeriodDTO> groupByElectricMeterNumber(ElectricMeter em) {

		Map<Long, List<ElectricMeter>> map = em.getElectricPeriods().stream()
				.collect(Collectors.groupingBy(eP -> eP.getElectricMeter().getElectricMeterNumber(),
						Collectors.mapping(eP -> eP.getElectricMeter(), Collectors.toList())));

		List<ElectricMeter> obj = map.get(em.getElectricMeterNumber());
		List<ElectricPeriod> list = new ArrayList<ElectricPeriod>();
		list.addAll(obj.get(0).getElectricPeriods());

		List<ElectricPeriodDTO> dtoList = new ArrayList<ElectricPeriodDTO>();

		list.forEach(e -> {
			ElectricPeriodDTO element = new ElectricPeriodDTO();
			element.setBemerkungen(e.getBemerkungen());
			element.setCustomerId(e.getCustomerId());
			element.setId(e.getId());
			element.setInfo(e.getInfo());
			element.setMarginStart(e.getMarginStart());
			element.setMarginStop(e.getMarginStop());
			element.setMwstPrice(e.getMwstPrice());
			element.setMwstSatz(e.getMwstSatz());
			element.setPreisBearbeitungsgebuehr(e.getPreisBearbeitungsgebuehr());
			element.setKundenGuthaben(e.getKundenGuthaben());
			element.setZahlungEingegangen(e.getZahlungEingegangen());
			element.setPreiskW(e.getPreiskW());
			element.setPrintDate(e.getPrintDate());
			element.setStatusText(e.getStatusText());
			element.setStromAkontoMonat(e.getStromAkontoMonat());
			element.setStromPriceBrutto(e.getStromPriceBrutto());
			element.setStromPriceNetto(e.getStromPriceNetto());
			element.setZaehlerFromPeriode(e.getZaehlerFromPeriode());
			element.setZaehlerStand(e.getZaehlerStand());
			element.setZaehlerToPeriode(e.getZaehlerToPeriode());
			dtoList.add(element);

		});

		return dtoList;

	}

	/**
	 * Diese Funktion würde es erlauben auf eine Rechnung mehrere Zähler
	 * aufzuführen. Das führt aber zu Schwierigkeiten bei der Darstellung der
	 * Rechnungsbeträge. Es gilt jeder Zaähler hat eine eigene Stromabrechnung. Hat
	 * ein Mieter zwei Objekte so bekommt er auch für jedes Objekt eine seperate
	 * Abrechnung. GGF können die Gebürhren bei einer der Abrechnungen erlassen
	 * werden.
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */

//  @GetMapping("/electricperiods/customerId/")
//  public List<ElectricMeterPeriodDTO> findByCustomerId(@RequestParam("id") Long id, Pageable pageable) {
//    List<ElectricPeriod> electricPeriods = electricMeterPeriodsRepository.findByCustomerId(id);
//
//    List<ElectricMeterPeriodDTO> eleList = new ArrayList<ElectricMeterPeriodDTO>();
//
//    electricPeriods.forEach(ePx -> {
//      ElectricMeterPeriodDTO ele = new ElectricMeterPeriodDTO();
//      List<ElectricPeriod> list2 = new ArrayList<ElectricPeriod>();
//      List<ElectricPeriod> list3 = new ArrayList<ElectricPeriod>();
//      List<ElectricPeriod> l2 = groupByElectricMeterNumber(ePx.getElectricMeter());
//      ele.setId(ePx.getElectricMeter().getId());
//      ele.setElectricMeterName(ePx.getElectricMeter().getElectricMeterName());
//      ele.setElectricMeterNumber(ePx.getElectricMeter().getElectricMeterNumber());
//
//      List<ElectricPeriod> list1 = l2.stream()
//          .filter(ePs -> Integer
//              .parseInt(new SimpleDateFormat("yyyy")
//                  .format(ePs.getZaehlerFromPeriode())) == Calendar.getInstance().get(Calendar.YEAR) - 1
//              || Integer.parseInt(new SimpleDateFormat("yyyy").format(ePs.getZaehlerFromPeriode())) == Calendar
//                  .getInstance().get(Calendar.YEAR))
//          .filter(str -> (str.getStatusText().equals("verrechnet") || str.getStatusText().equals("offen")))
//          .sorted(Comparator.comparingDouble(ElectricPeriod::getZaehlerStand)).collect(Collectors.toList());
//
//      Optional<ElectricPeriod> eP = l2.stream().filter(str -> (str.getStatusText().equals("bezahlt")))
//          .max(Comparator.comparingDouble(ElectricPeriod::getZaehlerStand));
//
//      if (eP.isPresent()) {
//        ElectricPeriod e = (ElectricPeriod) eP.get();
//        list2.add(e);
//        list3.addAll(list2);
//      }
//
//      list3.addAll(list1);
//      ele.setElectricPeriods(list3);
//      eleList.add(ele);
//    });
//
//   List<ElectricMeterPeriodDTO> eleList2 = eleList.stream().distinct().collect(Collectors.toList());
//
//   return eleList2;
//
//  }
	@Override
	public ElectricPeriodDTO getElectricMeterPeriodstById(Long id) {
		ElectricPeriod electricPeriod = electricMeterPeriodRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Electric Period by id %d not found", id)));
		return mapper.map(electricPeriod, ElectricPeriodDTO.class);
	}

	@Override
	public ElectricPeriodDTO create(Long id, ElectricPeriodDTO electricPeriodDTO) {
		return electricMeterRepository.findById(id).map(electricMeter -> {
			ElectricPeriod ep = mapper.map(electricPeriodDTO, ElectricPeriod.class);
			ep.setElectricMeter(electricMeter);
			return mapper.map(electricMeterPeriodRepository.save(ep), ElectricPeriodDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("ElectricMeterId " + id + " not found"));
	}

	@Override
	public ElectricPeriodDTO update(Long id, ElectricPeriodDTO electricPeriodDTO) {

		return electricMeterPeriodRepository.findById(id).map(electricPeriod -> {
			electricPeriod.setStatus(electricPeriodDTO.getStatusText().equals("bezahlt") ? true : false);
			ElectricPeriod ep = mapper.map(electricPeriodDTO, ElectricPeriod.class);
			ep.setStatus(electricPeriod.isStatus());
			ep.setElectricMeter(electricPeriod.getElectricMeter());
			return mapper.map(electricMeterPeriodRepository.save(ep), ElectricPeriodDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("Period " + id + " not found"));
	}

	@Override
	public ElectricPeriodDTO updateElectricPeriodMarging(Long id, ElectricPeriodDTO electricPeriodDTO) {

		return electricMeterPeriodRepository.findById(id).map(electricPeriod -> {
			electricPeriod.setMarginStart(electricPeriodDTO.getMarginStart());
			electricPeriod.setMarginStop(electricPeriodDTO.getMarginStop());
			return mapper.map(electricMeterPeriodRepository.save(electricPeriod), ElectricPeriodDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("PeriodId " + id + " not found"));
	}

	@Override
	public ElectricPeriodDTO updateElectricPeriodStatus(Long id, ElectricPeriodDTO electricPeriodDTO) {

		return electricMeterPeriodRepository.findById(id).map(electricPeriod -> {
			electricPeriod.setStatus(electricPeriodDTO.isStatus());
			electricPeriod.setStatusText(electricPeriodDTO.getStatusText());
			electricPeriod.setZahlungEingegangen(electricPeriodDTO.getZahlungEingegangen());
			return mapper.map(electricMeterPeriodRepository.save(electricPeriod), ElectricPeriodDTO.class);
		}).orElseThrow(() -> new ResourceNotFoundException("PeriodId " + id + " not found"));
	}

	@Override
	public Long delete(Long id) throws ResourceNotFoundException {
		ElectricPeriod electricPeriod = electricMeterPeriodRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ElectricPeriod not found on :: " + id));
		if (electricPeriod != null) {
			electricMeterPeriodRepository.delete(electricPeriod);
		}
		return id;
	}

}
