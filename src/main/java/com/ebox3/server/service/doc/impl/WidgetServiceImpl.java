package com.ebox3.server.service.doc.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ebox3.server.model.AdditionalCosts;
import com.ebox3.server.model.Ebox;
import com.ebox3.server.model.ElectricPeriod;
import com.ebox3.server.model.PaymentDatePrice;
import com.ebox3.server.model.dto.ChartDataMonthDTO;
import com.ebox3.server.model.dto.ChartDataSeriesDTO;
import com.ebox3.server.model.dto.OutstandPaymentDTO;
import com.ebox3.server.model.dto.WidgetDTO;
import com.ebox3.server.repo.AdditionalCostsRepository;
import com.ebox3.server.repo.AttributeKeyRepository;
import com.ebox3.server.repo.ContractRepository;
import com.ebox3.server.repo.EBoxRepository;
import com.ebox3.server.repo.ElectricMeterPeriodRepository;
import com.ebox3.server.repo.PaymentDatePriceRepository;
import com.ebox3.server.repo.PaymentRepository;
import com.ebox3.server.service.doc.WidgetService;

@Service
public class WidgetServiceImpl extends CalcHelper implements WidgetService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PaymentDatePriceRepository paymentDatePriceRepository;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	EBoxRepository eBoxRepository;

	@Autowired
	ElectricMeterPeriodRepository electricMeterPeriodRepository;

	@Autowired
	AdditionalCostsRepository additionalCostsRepository;

	@Autowired
	AttributeKeyRepository attributeKeyRepository;

	@Override
	public Iterable<WidgetDTO> getBigChart(String year) {
		try {
			Long mwStYear = null;
			if (year == null || year.equals("undefined")) {
				Calendar cal = Calendar.getInstance();
				mwStYear = (long) cal.get(Calendar.YEAR);
			} else {
				mwStYear = Long.parseLong(year);
			}

			List<PaymentDatePrice> list = paymentDatePriceRepository.findByYear(year);
			if (list == null) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to retrieve payment data for year: " + year);
			}

			String mwstString = attributeKeyRepository.findMwstSatz("mwst", mwStYear);
			if (mwstString == null || mwstString.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to retrieve MwSt rate for year: " + mwStYear);
			}

			Double mwst;
			try {
				mwst = Double.valueOf(mwstString);
			} catch (NumberFormatException ex) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Invalid MwSt rate format for year: " + mwStYear + ", value: " + mwstString);
			}

			List<WidgetDTO> retValue = new ArrayList<WidgetDTO>();

			WidgetDTO dto = new WidgetDTO();
			WidgetDTO dto1 = new WidgetDTO();

			dto.setName("MwSt");
			dto1.setName("Netto");

			Double resNetto[] = new Double[12];
			Double resMwSt[] = new Double[12];

			Double janBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJanPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJanPrice).sum();
			resNetto[0] = calcMwst(janBruttoVal, mwst);
			Double res = janBruttoVal - resNetto[0];
			resMwSt[0] = Math.round(res * 100) / 100.0;

			Double febBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoFebPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoFebPrice).sum();
			resNetto[1] = calcMwst(febBruttoVal, mwst);
			Double res1 = febBruttoVal - resNetto[1];
			resMwSt[1] = Math.round(res1 * 100) / 100.0;

			Double marBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoMarPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoMarPrice).sum();
			resNetto[2] = calcMwst(marBruttoVal, mwst);
			Double res2 = marBruttoVal - resNetto[2];
			resMwSt[2] = Math.round(res2 * 100) / 100.0;

			Double aprrBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoAprPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoAprPrice).sum();
			resNetto[3] = calcMwst(aprrBruttoVal, mwst);
			Double res3 = aprrBruttoVal - resNetto[3];
			resMwSt[3] = Math.round(res3 * 100) / 100.0;

			Double maiBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoMaiPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoMaiPrice).sum();
			resNetto[4] = calcMwst(maiBruttoVal, mwst);
			Double res4 = maiBruttoVal - resNetto[4];
			resMwSt[4] = Math.round(res4 * 100) / 100.0;

			Double junBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJunPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJunPrice).sum();
			resNetto[5] = calcMwst(junBruttoVal, mwst);
			Double res5 = junBruttoVal - resNetto[5];
			resMwSt[5] = Math.round(res5 * 100) / 100.0;

			Double julBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJulPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJulPrice).sum();
			resNetto[6] = calcMwst(julBruttoVal, mwst);
			Double res6 = julBruttoVal - resNetto[6];
			resMwSt[6] = Math.round(res6 * 100) / 100.0;

			Double augBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoAugPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoAugPrice).sum();
			resNetto[7] = calcMwst(augBruttoVal, mwst);
			Double res7 = augBruttoVal - resNetto[7];
			resMwSt[7] = Math.round(res7 * 100) / 100.0;

			Double sepBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoSepPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoSepPrice).sum();
			resNetto[8] = calcMwst(sepBruttoVal, mwst);
			Double res8 = sepBruttoVal - resNetto[8];
			resMwSt[8] = Math.round(res8 * 100) / 100.0;

			Double octBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoOctPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoOctPrice).sum();
			resNetto[9] = calcMwst(octBruttoVal, mwst);
			Double res9 = octBruttoVal - resNetto[9];
			resMwSt[9] = Math.round(res9 * 100) / 100.0;

			Double novBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoNovPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoNovPrice).sum();
			resNetto[10] = calcMwst(novBruttoVal, mwst);
			Double res10 = novBruttoVal - resNetto[10];
			resMwSt[10] = Math.round(res10 * 100) / 100.0;

			Double decBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoDecPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoDecPrice).sum();
			resNetto[11] = calcMwst(decBruttoVal, mwst);
			Double res11 = decBruttoVal - resNetto[11];
			resMwSt[11] = Math.round(res11 * 100) / 100.0;

			dto.setData(resNetto);
			dto1.setData(resMwSt);

			retValue.add(dto);
			retValue.add(dto1);

			return retValue;

		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid year format: " + year);
		} catch (ResponseStatusException ex) {
			throw ex; // Re-throw ResponseStatusException
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"An unexpected error occurred while generating chart data");
		}
	}

	@Override
	public Iterable<ChartDataMonthDTO> getNgxChartBar(String year) {
		try {
			Long mwStYear = null;
			if (year == null || year.equals("undefined")) {
				Calendar cal = Calendar.getInstance();
				mwStYear = (long) cal.get(Calendar.YEAR);
			} else {
				mwStYear = Long.parseLong(year);
			}

			List<PaymentDatePrice> list = paymentDatePriceRepository.findByYear(mwStYear.toString());
			if (list == null) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to retrieve payment data for year: " + mwStYear);
			}

			String mwstString = attributeKeyRepository.findMwstSatz("mwst", mwStYear);
			if (mwstString == null || mwstString.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to retrieve MwSt rate for year: " + mwStYear);
			}

			Double mwst;
			try {
				mwst = Double.valueOf(mwstString);
			} catch (NumberFormatException ex) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Invalid MwSt rate format for year: " + mwStYear + ", value: " + mwstString);
			}

			List<ChartDataMonthDTO> retValue = new ArrayList<ChartDataMonthDTO>();

			/** Januar **/
			ChartDataMonthDTO cdmDTOJanuar = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOJanuarNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOJanuarMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistJanuar = new ArrayList<ChartDataSeriesDTO>();

			Double janBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJanPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJanPrice).sum();
			cdsDTOJanuarMwst.setName("MwSt");
			cdsDTOJanuarMwst.setValue(calcMwst(janBruttoVal, mwst));

			Double resJan = janBruttoVal - cdsDTOJanuarMwst.getValue();
			cdsDTOJanuarNetto.setName("Netto");
			cdsDTOJanuarNetto.setValue(Math.round(resJan * 100) / 100.0);

			cdsDTOlistJanuar.add(cdsDTOJanuarNetto);
			cdsDTOlistJanuar.add(cdsDTOJanuarMwst);

			cdmDTOJanuar.setName("Januar");
			cdmDTOJanuar.setSeries(cdsDTOlistJanuar);
			retValue.add(cdmDTOJanuar);

			/** Februar **/
			ChartDataMonthDTO cdmDTOFebruar = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOFebruarNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOFebruarMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistFebruar = new ArrayList<ChartDataSeriesDTO>();

			Double febBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoFebPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoFebPrice).sum();
			cdsDTOFebruarMwst.setName("MwSt");
			cdsDTOFebruarMwst.setValue(calcMwst(febBruttoVal, mwst));

			Double resFeb = febBruttoVal - cdsDTOFebruarMwst.getValue();
			cdsDTOFebruarNetto.setName("Netto");
			cdsDTOFebruarNetto.setValue(Math.round(resFeb * 100) / 100.0);

			cdsDTOlistFebruar.add(cdsDTOFebruarNetto);
			cdsDTOlistFebruar.add(cdsDTOFebruarMwst);

			cdmDTOFebruar.setName("Februar");
			cdmDTOFebruar.setSeries(cdsDTOlistFebruar);

			retValue.add(cdmDTOFebruar);

			/** März **/
			ChartDataMonthDTO cdmDTOMärz = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOMärzNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOMärzMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistMärz = new ArrayList<ChartDataSeriesDTO>();

			Double marBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoMarPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoMarPrice).sum();
			cdsDTOMärzMwst.setName("MwSt");
			cdsDTOMärzMwst.setValue(calcMwst(marBruttoVal, mwst));

			Double resMar = marBruttoVal - cdsDTOMärzMwst.getValue();
			cdsDTOMärzNetto.setName("Netto");
			cdsDTOMärzNetto.setValue(Math.round(resMar * 100) / 100.0);

			cdsDTOlistMärz.add(cdsDTOMärzNetto);
			cdsDTOlistMärz.add(cdsDTOMärzMwst);

			cdmDTOMärz.setName("März");
			cdmDTOMärz.setSeries(cdsDTOlistMärz);

			retValue.add(cdmDTOMärz);

			/** April **/
			ChartDataMonthDTO cdmDTOApril = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOAprilNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOAprilMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistApril = new ArrayList<ChartDataSeriesDTO>();

			Double aprBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoAprPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoAprPrice).sum();
			cdsDTOAprilMwst.setName("MwSt");
			cdsDTOAprilMwst.setValue(calcMwst(aprBruttoVal, mwst));

			Double resApr = aprBruttoVal - cdsDTOAprilMwst.getValue();
			cdsDTOAprilNetto.setName("Netto");
			cdsDTOAprilNetto.setValue(Math.round(resApr * 100) / 100.0);

			cdsDTOlistApril.add(cdsDTOAprilNetto);
			cdsDTOlistApril.add(cdsDTOAprilMwst);

			cdmDTOApril.setName("April");
			cdmDTOApril.setSeries(cdsDTOlistApril);

			retValue.add(cdmDTOApril);

			/** Mai **/
			ChartDataMonthDTO cdmDTOMai = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOMaiNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOMaiMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistMai = new ArrayList<ChartDataSeriesDTO>();

			Double maiBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoMaiPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoMaiPrice).sum();
			cdsDTOMaiMwst.setName("MwSt");
			cdsDTOMaiMwst.setValue(calcMwst(maiBruttoVal, mwst));

			Double resMai = maiBruttoVal - cdsDTOMaiMwst.getValue();
			cdsDTOMaiNetto.setName("Netto");
			cdsDTOMaiNetto.setValue(Math.round(resMai * 100) / 100.0);

			cdsDTOlistMai.add(cdsDTOMaiNetto);
			cdsDTOlistMai.add(cdsDTOMaiMwst);

			cdmDTOMai.setName("Mai");
			cdmDTOMai.setSeries(cdsDTOlistMai);

			retValue.add(cdmDTOMai);

			/** June **/
			ChartDataMonthDTO cdmDTOJuni = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOJuniNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOJuniMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistJuni = new ArrayList<ChartDataSeriesDTO>();

			Double junBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJunPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJunPrice).sum();
			cdsDTOJuniMwst.setName("MwSt");
			cdsDTOJuniMwst.setValue(calcMwst(junBruttoVal, mwst));

			Double resJuni = junBruttoVal - cdsDTOJuniMwst.getValue();
			cdsDTOJuniNetto.setName("Netto");
			cdsDTOJuniNetto.setValue(Math.round(resJuni * 100) / 100.0);

			cdsDTOlistJuni.add(cdsDTOJuniNetto);
			cdsDTOlistJuni.add(cdsDTOJuniMwst);

			cdmDTOJuni.setName("Juni");
			cdmDTOJuni.setSeries(cdsDTOlistJuni);

			retValue.add(cdmDTOJuni);

			/** July **/
			ChartDataMonthDTO cdmDTOJuli = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOJuliNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOJuliMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistJuli = new ArrayList<ChartDataSeriesDTO>();

			Double juliBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoJulPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoJulPrice).sum();
			cdsDTOJuliMwst.setName("MwSt");
			cdsDTOJuliMwst.setValue(calcMwst(juliBruttoVal, mwst));

			Double resJuli = juliBruttoVal - cdsDTOJuliMwst.getValue();
			cdsDTOJuliNetto.setName("Netto");
			cdsDTOJuliNetto.setValue(Math.round(resJuli * 100) / 100.0);

			cdsDTOlistJuli.add(cdsDTOJuliNetto);
			cdsDTOlistJuli.add(cdsDTOJuliMwst);

			cdmDTOJuli.setName("Juli");
			cdmDTOJuli.setSeries(cdsDTOlistJuli);

			retValue.add(cdmDTOJuli);

			/** August **/
			ChartDataMonthDTO cdmDTOAugust = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOAugustNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOAugustMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistAugust = new ArrayList<ChartDataSeriesDTO>();

			Double augustBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoAugPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoAugPrice).sum();
			cdsDTOAugustMwst.setName("MwSt");
			cdsDTOAugustMwst.setValue(calcMwst(augustBruttoVal, mwst));

			Double resAugust = augustBruttoVal - cdsDTOAugustMwst.getValue();
			cdsDTOAugustNetto.setName("Netto");
			cdsDTOAugustNetto.setValue(Math.round(resAugust * 100) / 100.0);

			cdsDTOlistAugust.add(cdsDTOAugustNetto);
			cdsDTOlistAugust.add(cdsDTOAugustMwst);

			cdmDTOAugust.setName("August");
			cdmDTOAugust.setSeries(cdsDTOlistAugust);

			retValue.add(cdmDTOAugust);

			/** September **/
			ChartDataMonthDTO cdmDTOSeptember = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOSeptemberNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOSeptemberMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistSeptember = new ArrayList<ChartDataSeriesDTO>();

			Double septemberBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoSepPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoSepPrice).sum();
			cdsDTOSeptemberMwst.setName("MwSt");
			cdsDTOSeptemberMwst.setValue(calcMwst(septemberBruttoVal, mwst));

			Double resSeptember = septemberBruttoVal - cdsDTOSeptemberMwst.getValue();
			cdsDTOSeptemberNetto.setName("Netto");
			cdsDTOSeptemberNetto.setValue(Math.round(resSeptember * 100) / 100.0);

			cdsDTOlistSeptember.add(cdsDTOSeptemberNetto);
			cdsDTOlistSeptember.add(cdsDTOSeptemberMwst);

			cdmDTOSeptember.setName("September");
			cdmDTOSeptember.setSeries(cdsDTOlistSeptember);

			retValue.add(cdmDTOSeptember);

			/** Oktober **/
			ChartDataMonthDTO cdmDTOOktober = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTOOktoberNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTOOktoberMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistOktober = new ArrayList<ChartDataSeriesDTO>();

			Double oktoberBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoOctPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoOctPrice).sum();
			cdsDTOOktoberMwst.setName("MwSt");
			cdsDTOOktoberMwst.setValue(calcMwst(oktoberBruttoVal, mwst));

			Double resOktober = oktoberBruttoVal - cdsDTOOktoberMwst.getValue();
			cdsDTOOktoberNetto.setName("Netto");
			cdsDTOOktoberNetto.setValue(Math.round(resOktober * 100) / 100.0);

			cdsDTOlistOktober.add(cdsDTOOktoberNetto);
			cdsDTOlistOktober.add(cdsDTOOktoberMwst);

			cdmDTOOktober.setName("Oktober");
			cdmDTOOktober.setSeries(cdsDTOlistOktober);

			retValue.add(cdmDTOOktober);

			/** November **/
			ChartDataMonthDTO cdmDTONovember = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTONovemberNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTONovemberMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistNovember = new ArrayList<ChartDataSeriesDTO>();

			Double novemberBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoNovPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoNovPrice).sum();
			cdsDTONovemberMwst.setName("MwSt");
			cdsDTONovemberMwst.setValue(calcMwst(novemberBruttoVal, mwst));

			Double resNovember = novemberBruttoVal - cdsDTONovemberMwst.getValue();
			cdsDTONovemberNetto.setName("Netto");
			cdsDTONovemberNetto.setValue(Math.round(resNovember * 100) / 100.0);

			cdsDTOlistNovember.add(cdsDTONovemberNetto);
			cdsDTOlistNovember.add(cdsDTONovemberMwst);

			cdmDTONovember.setName("November");
			cdmDTONovember.setSeries(cdsDTOlistNovember);

			retValue.add(cdmDTONovember);

			/** Dezember **/
			ChartDataMonthDTO cdmDTODezember = new ChartDataMonthDTO();
			ChartDataSeriesDTO cdsDTODezemberNetto = new ChartDataSeriesDTO();
			ChartDataSeriesDTO cdsDTODezemberMwst = new ChartDataSeriesDTO();
			List<ChartDataSeriesDTO> cdsDTOlistDezember = new ArrayList<ChartDataSeriesDTO>();

			Double dezemberBruttoVal = list.stream()
					.filter(payment -> payment != null && payment.getBruttoDecPrice() != null)
					.mapToDouble(PaymentDatePrice::getBruttoDecPrice).sum();
			cdsDTODezemberMwst.setName("MwSt");
			cdsDTODezemberMwst.setValue(calcMwst(dezemberBruttoVal, mwst));

			Double resDezember = dezemberBruttoVal - cdsDTODezemberMwst.getValue();
			cdsDTODezemberNetto.setName("Netto");
			cdsDTODezemberNetto.setValue(Math.round(resDezember * 100) / 100.0);

			cdsDTOlistDezember.add(cdsDTODezemberNetto);
			cdsDTOlistDezember.add(cdsDTODezemberMwst);

			cdmDTODezember.setName("Dezember");
			cdmDTODezember.setSeries(cdsDTOlistDezember);

			retValue.add(cdmDTODezember);

			return retValue;

		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid year format: " + year);
		} catch (ResponseStatusException ex) {
			throw ex; // Re-throw ResponseStatusException
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"An unexpected error occurred while generating chart data");
		}
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getNgxCharPie() {

		List<Ebox> list = eBoxRepository.findByStatusText("gebucht");

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();

		ChartDataSeriesDTO basiskosten = new ChartDataSeriesDTO();
		basiskosten.setName("Miete");
		basiskosten.setValue(list.stream() // Basispreis
				.filter(ebox -> ebox != null && ebox.getPrice() != null).mapToDouble(Ebox::getPrice).sum());
		retValue.add(basiskosten);

		ChartDataSeriesDTO nebenkosten = new ChartDataSeriesDTO();
		nebenkosten.setName("NK");
		nebenkosten.setValue(list.stream() // Nebenkosten
				.filter(ebox -> ebox != null && ebox.getAdditionalCosts() != null).mapToDouble(Ebox::getAdditionalCosts)
				.sum());
		retValue.add(nebenkosten);

		ChartDataSeriesDTO stromkosten = new ChartDataSeriesDTO();
		stromkosten.setName("Strom");
		stromkosten.setValue(list.stream() // Stromkosten
				.filter(ebox -> ebox != null && ebox.getStromPrice() != null).mapToDouble(Ebox::getStromPrice).sum());
		retValue.add(stromkosten);

		ChartDataSeriesDTO heizungskosten = new ChartDataSeriesDTO();
		heizungskosten.setName("Heizung");
		heizungskosten.setValue(list.stream() // Heizungskosten
				.filter(ebox -> ebox != null && ebox.getHeizungPrice() != null).mapToDouble(Ebox::getHeizungPrice)
				.sum());
		retValue.add(heizungskosten);

		ChartDataSeriesDTO wasserkosten = new ChartDataSeriesDTO();
		wasserkosten.setName("Wasser");
		wasserkosten.setValue(list.stream() // Wasserkosten
				.filter(ebox -> ebox != null && ebox.getWasserPrice() != null).mapToDouble(Ebox::getWasserPrice).sum());
		retValue.add(wasserkosten);

		ChartDataSeriesDTO internetkosten = new ChartDataSeriesDTO();
		internetkosten.setName("Internet");
		internetkosten.setValue(list.stream().filter(ebox -> ebox != null && ebox.getInternetPrice() != null)
				.mapToDouble(Ebox::getInternetPrice).sum());
		retValue.add(internetkosten);

		ChartDataSeriesDTO wifikosten = new ChartDataSeriesDTO();
		wifikosten.setName("Wifi");
		wifikosten.setValue(list.stream().filter(ebox -> ebox != null && ebox.getInternetWifiPrice() != null)
				.mapToDouble(Ebox::getInternetWifiPrice).sum());
		retValue.add(wifikosten);

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getCountedObjectStateNgxPieChart(String year) throws ParseException {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO gebuchtDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO freiDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO reserviertDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO gekuendigtDTO = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			gebuchtDTO.setName("gebucht");
			gebuchtDTO
					.setValue(Double.valueOf(eBoxRepository.findByStatusTexAndStartDate("gebucht", start, end).size()));
			retValue.add(gebuchtDTO);

			freiDTO.setName("frei");
			freiDTO.setValue(Double.valueOf(eBoxRepository.findByStatusTexAndStartDate("frei", start, end).size()));
			retValue.add(freiDTO);

			reserviertDTO.setName("frei");
			reserviertDTO.setValue(
					Double.valueOf(eBoxRepository.findByStatusTexAndStartDate("reserviert", start, end).size()));
			retValue.add(reserviertDTO);

			gekuendigtDTO.setName("gekündigt");
			gekuendigtDTO.setValue(
					Double.valueOf(eBoxRepository.findByStatusTexAndStartDate("gekündigt", start, end).size()));
			retValue.add(gekuendigtDTO);

		} else {
			gebuchtDTO.setName("gebucht");
			gebuchtDTO.setValue(Double.valueOf(eBoxRepository.findByStatusText("gebucht").size()));
			retValue.add(gebuchtDTO);

			freiDTO.setName("frei");
			freiDTO.setValue(Double.valueOf(eBoxRepository.findByStatusText("frei").size()));
			retValue.add(freiDTO);

			reserviertDTO.setName("reserviert");
			reserviertDTO.setValue(Double.valueOf(eBoxRepository.findByStatusText("reserviert").size()));
			retValue.add(reserviertDTO);

			gekuendigtDTO.setName("gekündigt");
			gekuendigtDTO.setValue(Double.valueOf(eBoxRepository.findByStatusText("gekündigt").size()));
			retValue.add(gekuendigtDTO);
		}

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getCountedContractStateNgxPieChart(String year) throws ParseException {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO vertragDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO offerteDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO absageDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO kuendigungDTO = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			vertragDTO.setName("Vertrag");
			vertragDTO.setValue(
					Double.valueOf(contractRepository.findByStatusTexAndStartDate("Vertrag", start, end).size()));
			retValue.add(vertragDTO);

			offerteDTO.setName("Offerte");
			offerteDTO.setValue(
					Double.valueOf(contractRepository.findByStatusTexAndStartDate("Offerte", start, end).size()));
			retValue.add(offerteDTO);

			absageDTO.setName("Absage");
			absageDTO.setValue(
					Double.valueOf(contractRepository.findByStatusTexAndStartDate("Absage", start, end).size()));
			retValue.add(absageDTO);

			kuendigungDTO.setName("Kündigung");
			kuendigungDTO.setValue(
					Double.valueOf(contractRepository.findByStatusTexAndStartDate("Kündigung", start, end).size()));
			retValue.add(kuendigungDTO);

		} else {
			vertragDTO.setName("Vertrag");
			vertragDTO.setValue(Double.valueOf(contractRepository.findByStatusText("Vertrag").size()));
			retValue.add(vertragDTO);

			offerteDTO.setName("Offerte");
			offerteDTO.setValue(Double.valueOf(contractRepository.findByStatusText("Offerte").size()));
			retValue.add(offerteDTO);

			absageDTO.setName("Absage");
			absageDTO.setValue(Double.valueOf(contractRepository.findByStatusText("Absage").size()));
			retValue.add(absageDTO);

			kuendigungDTO.setName("Kündigung");
			kuendigungDTO.setValue(Double.valueOf(contractRepository.findByStatusText("Kündigung").size()));
			retValue.add(kuendigungDTO);
		}

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getCountedCustomerStateNgxPieChart(String year) throws ParseException {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO vertragDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO offerteDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO intressentDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO kuendigungDTO = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			vertragDTO.setName("Vertragsnehmer");
			vertragDTO.setValue(Double
					.valueOf(customerRepository.findByStatusTexAndStartDate("Vertragsnehmer", start, end).size()));
			retValue.add(vertragDTO);

			offerteDTO.setName("Offertnehmer");
			offerteDTO.setValue(
					Double.valueOf(customerRepository.findByStatusTexAndStartDate("Offertnehmer", start, end).size()));
			retValue.add(offerteDTO);

			intressentDTO.setName("Intressent");
			intressentDTO.setValue(
					Double.valueOf(customerRepository.findByStatusTexAndStartDate("Intressent", start, end).size()));
			retValue.add(intressentDTO);

			kuendigungDTO.setName("Kündigung");
			kuendigungDTO.setValue(
					Double.valueOf(customerRepository.findByStatusTexAndStartDate("Kündigung", start, end).size()));
			retValue.add(kuendigungDTO);

		} else {
			vertragDTO.setName("Vertragsnehmer");
			vertragDTO.setValue(Double.valueOf(customerRepository.findByStatusText("Vertragsnehmer").size()));
			retValue.add(vertragDTO);

			offerteDTO.setName("Offertnehmer");
			offerteDTO.setValue(Double.valueOf(customerRepository.findByStatusText("Offertnehmer").size()));
			retValue.add(offerteDTO);

			intressentDTO.setName("Intressent");
			intressentDTO.setValue(Double.valueOf(customerRepository.findByStatusText("Intressent").size()));
			retValue.add(intressentDTO);

			kuendigungDTO.setName("Kündigung");
			kuendigungDTO.setValue(Double.valueOf(customerRepository.findByStatusText("Kündigung").size()));
			retValue.add(kuendigungDTO);
		}

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getDetailPriceNgxChart(String year) {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO mwstDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO bruttoDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO bruttoOffenDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO bruttoClosedDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO bruttoOffeneAnzahlDTO = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCostsByStartEndDate(start, end,
					"bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			mwstDTO.setName("Mwst");
			mwstDTO.setValue(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());
			retValue.add(mwstDTO);

			bruttoDTO.setName("Brutto");
			bruttoDTO.setValue(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());
			retValue.add(bruttoDTO);

			Double openPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double verrechnetPrice = listVer.stream() // Bruttopreis verrechnet
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totalPrice = openPrice + verrechnetPrice;
			bruttoOffenDTO.setName("Offene Rechnungen");
			bruttoOffenDTO.setValue(totalPrice);
			retValue.add(bruttoOffenDTO);

			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount;
			bruttoOffeneAnzahlDTO.setName("Total offene Rechnungen");
			bruttoOffeneAnzahlDTO.setValue(totCount.doubleValue());
			retValue.add(bruttoOffeneAnzahlDTO);

		} else {

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCost("bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			mwstDTO.setName("Mwst");
			mwstDTO.setValue(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());
			retValue.add(mwstDTO);

			bruttoDTO.setName("Brutto");
			bruttoDTO.setValue(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());
			retValue.add(bruttoDTO);

			Double verBruttoPrice = listVer.stream() // Bruttopreis verrechnet
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double openBruttoPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totPriceBrutto = verBruttoPrice + openBruttoPrice;
			bruttoOffenDTO.setName("Offene Rechnungen");
			bruttoOffenDTO.setValue(totPriceBrutto);
			retValue.add(bruttoOffenDTO);

			bruttoOffenDTO.setName("Bezahlte Rechnungen");
			bruttoClosedDTO.setValue(Double.valueOf(additionalCostsRepository.findAdditionalCost("bezahlt").size()));
			retValue.add(bruttoClosedDTO);

			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount;
			bruttoOffeneAnzahlDTO.setName("Total offene Rechnungen");
			bruttoOffeneAnzahlDTO.setValue(totCount.doubleValue());
			retValue.add(bruttoOffeneAnzahlDTO);

		}

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getkWattChart(String year, String quarter) {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO stromKwWattDTO = new ChartDataSeriesDTO();

		Date start;
		Date end;

		if (year.equals("Alle")) {
			Calendar cal = new GregorianCalendar();
			year = Integer.toString(cal.get(Calendar.YEAR));
		}

		start = calcFirstDayOfYear(year);
		end = calcLastDayOfYear(year);
		int pJahr = Integer.parseInt(year);

		// System.out.println("Jahr: " + pJahr);

		if (quarter.contentEquals("Q1: Jan. - Mar.")) {
			start = new GregorianCalendar(pJahr, 0, 1).getTime();
			end = new GregorianCalendar(pJahr, 2, 31).getTime();
		} else if (quarter.contentEquals("Q2: Apr. - Jun.")) {
			start = new GregorianCalendar(pJahr, 3, 1).getTime();
			end = new GregorianCalendar(pJahr, 5, 30).getTime();
		} else if (quarter.contentEquals("Q3: Jul. - Sep.")) {
			start = new GregorianCalendar(pJahr, 6, 1).getTime();
			end = new GregorianCalendar(pJahr, 8, 30).getTime();
		} else if (quarter.contentEquals("Q4: Okt. - Dez.")) {
			start = new GregorianCalendar(pJahr, 9, 1).getTime();
			end = new GregorianCalendar(pJahr, 11, 31).getTime();
		} else if (quarter.contentEquals("Q1+Q2: Jan. - Jun.")) {
			start = new GregorianCalendar(pJahr, 0, 1).getTime();
			end = new GregorianCalendar(pJahr, 5, 30).getTime();
		} else if (quarter.contentEquals("Q3+Q4: Jul. - Dez.")) {
			start = new GregorianCalendar(pJahr, 6, 1).getTime();
			end = new GregorianCalendar(pJahr, 11, 31).getTime();
		}

		Double diffKwatt = electricMeterPeriodRepository.findByKWatt(start, end, "bezahlt") != null
				? electricMeterPeriodRepository.findByKWatt(start, end, "bezahlt")
				: 0.00;

		stromKwWattDTO.setName("Stromverbrauch in [kW]: ");
		stromKwWattDTO.setValue(diffKwatt);
		retValue.add(stromKwWattDTO);

		return retValue;

	}

	@Override
	public Iterable<ChartDataSeriesDTO> getPricePowerNgxChart1(String year, String quarter) {

		stromKostenBrutto = 0.00;
		stromKostenNetto = 0.00;
		stromMwst = 0.00;
		sumStormVorausZahlung = 0.00;
		sumSonstigeGuthabenNetto = 0.00;
		sumStromKostenNetto = 0.00;
		stromGebuehren = 0.00;

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();

		ChartDataSeriesDTO stromTotalKosten = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromBruttoPriceDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromNettoPriceDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromMwstDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromBearbeitungsgebuehrDTO = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromGuthaben = new ChartDataSeriesDTO();
		ChartDataSeriesDTO stromVorauszahlungen = new ChartDataSeriesDTO();

		Date start;
		Date end;

		if (year.equals("Alle")) {
			Calendar cal = new GregorianCalendar();
			year = Integer.toString(cal.get(Calendar.YEAR));
		}

		start = calcFirstDayOfYear(year);
		end = calcLastDayOfYear(year);
		int pJahr = Integer.parseInt(year);

		if (quarter.contentEquals("Q1: Jan. - Mar.")) {
			start = new GregorianCalendar(pJahr, 0, 1).getTime();
			end = new GregorianCalendar(pJahr, 2, 31).getTime();
		} else if (quarter.contentEquals("Q2: Apr. - Jun.")) {
			start = new GregorianCalendar(pJahr, 3, 1).getTime();
			end = new GregorianCalendar(pJahr, 5, 30).getTime();
		} else if (quarter.contentEquals("Q3: Jul. - Sep.")) {
			start = new GregorianCalendar(pJahr, 6, 1).getTime();
			end = new GregorianCalendar(pJahr, 8, 30).getTime();
		} else if (quarter.contentEquals("Q4: Okt. - Dez.")) {
			start = new GregorianCalendar(pJahr, 9, 1).getTime();
			end = new GregorianCalendar(pJahr, 11, 31).getTime();
		} else if (quarter.contentEquals("Q1+Q2: Jan. - Jun.")) {
			start = new GregorianCalendar(pJahr, 0, 1).getTime();
			end = new GregorianCalendar(pJahr, 5, 30).getTime();
		} else if (quarter.contentEquals("Q3+Q4: Jul. - Dez.")) {
			start = new GregorianCalendar(pJahr, 6, 1).getTime();
			end = new GregorianCalendar(pJahr, 11, 31).getTime();
		}

		Double priceBrutto = 0.00;
		Double priceNetto = 0.00;
		Double priceMwst = 0.00;
		Double priceBearbeitungsgebuehr = 0.00;

		priceBrutto = electricMeterPeriodRepository.findByBruttoPrice(start, end, "bezahlt") != null
				? electricMeterPeriodRepository.findByBruttoPrice(start, end, "bezahlt")
				: 0.00;

		priceNetto = electricMeterPeriodRepository.findByNettoPrice(start, end, "bezahlt") != null
				? electricMeterPeriodRepository.findByNettoPrice(start, end, "bezahlt")
				: 0.00;

		priceMwst = electricMeterPeriodRepository.findByMwStPrice(start, end, "bezahlt") != null
				? electricMeterPeriodRepository.findByMwStPrice(start, end, "bezahlt")
				: 0.00;

		priceBearbeitungsgebuehr = electricMeterPeriodRepository.findByBearbeitungsGebuehrPrice(start, end,
				"bezahlt") != null ? electricMeterPeriodRepository.findByBearbeitungsGebuehrPrice(start, end, "bezahlt")
						: 0.00;

		List<ElectricPeriod> list = electricMeterPeriodRepository.findElectricPeriodByStartEndDate(start, end,
				"bezahlt");

		list.forEach(item -> {
			if (item.getDiffKwatt() != null) {
				if (item.getZaehlerFromPeriode() != null) {
					// Long diff = 0L;
					Double kundenGuthaben = 0.00;

					LocalDate localToPeriod = item.getZaehlerToPeriode().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					LocalDate localFromPeriod = item.getZaehlerFromPeriode().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();

					Long diff = ChronoUnit.MONTHS.between(YearMonth.from(localFromPeriod),
							YearMonth.from(localToPeriod)) + 1;

					setStromVorausZahlung(item.getStromAkontoMonat() != null ? item.getStromAkontoMonat() : 0.00,
							diff != null ? diff : 0);

					setSumStromKostenNetto(item.getPreiskW() != null ? item.getPreiskW() : 0.00,
							item.getDiffKwatt() != null ? item.getDiffKwatt() : 0.00);

					kundenGuthaben = item.getKundenGuthaben() != null ? item.getKundenGuthaben() : 0.00;
					setSumSonstigeGuthabenNetto(kundenGuthaben);
				}
			}
		});

		stromTotalKosten.setName("Total Stromkosten");
		stromTotalKosten.setValue(sumStromKostenNetto);
		retValue.add(stromTotalKosten);

		stromBruttoPriceDTO.setName("Brutto Preis in [CHF]:");
		stromBruttoPriceDTO.setValue(priceBrutto);
		retValue.add(stromBruttoPriceDTO);

		stromNettoPriceDTO.setName("Netto Preis in [CHF]: ");
		stromNettoPriceDTO.setValue(priceNetto);
		retValue.add(stromNettoPriceDTO);

		stromMwstDTO.setName("Mwst in [CHF]: ");
		stromMwstDTO.setValue(priceMwst);
		retValue.add(stromMwstDTO);

		stromBearbeitungsgebuehrDTO.setName("Bearbeitungsgebühren in [CHF]: ");
		stromBearbeitungsgebuehrDTO.setValue(priceBearbeitungsgebuehr);
		retValue.add(stromBearbeitungsgebuehrDTO);

		stromGuthaben.setName("Sonstige Guthaben");
		stromGuthaben.setValue(sumSonstigeGuthabenNetto * -1);
		retValue.add(stromGuthaben);

		stromVorauszahlungen.setName("Vorauszahlung");
		stromVorauszahlungen.setValue(sumStormVorausZahlung * -1);
		retValue.add(stromVorauszahlungen);

		return retValue;

	}

	@Override
	public Iterable<ChartDataSeriesDTO> getDetailPriceBill(String year) {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();

		ChartDataSeriesDTO billingMwstClosed = new ChartDataSeriesDTO();
		ChartDataSeriesDTO billingBruttoClosed = new ChartDataSeriesDTO();
		ChartDataSeriesDTO billingBruttoOpen = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCostsByStartEndDate(start, end,
					"bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			billingMwstClosed.setName("Mwst");
			billingMwstClosed.setValue(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());
			retValue.add(billingMwstClosed);

			billingBruttoClosed.setName("Verrechnet");
			billingBruttoClosed.setValue(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());

			retValue.add(billingBruttoClosed);

			Double openPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double verrechnetPrice = listVer.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totalPrice = openPrice + verrechnetPrice;
			billingBruttoOpen.setName("Offen");
			billingBruttoOpen.setValue(totalPrice);
			retValue.add(billingBruttoOpen);

		} else {

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCost("bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			billingMwstClosed.setName("Mwst");
			billingMwstClosed.setValue(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());
			retValue.add(billingMwstClosed);

			billingBruttoClosed.setName("Verrechnet");
			billingBruttoClosed.setValue(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());

			retValue.add(billingBruttoClosed);

			Double verBruttoPrice = listVer.stream() // Bruttopreis verrechnet
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double openBruttoPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totPriceBrutto = verBruttoPrice + openBruttoPrice;
			billingBruttoOpen.setName("Offen");
			billingBruttoOpen.setValue(totPriceBrutto);

			retValue.add(billingBruttoOpen);

		}

		return retValue;
	}

	@Override
	public Iterable<ChartDataSeriesDTO> getDetailPriceBillCount(String year) {

		List<ChartDataSeriesDTO> retValue = new ArrayList<ChartDataSeriesDTO>();
		ChartDataSeriesDTO totCountBilling = new ChartDataSeriesDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			Long closedCount = Long
					.valueOf(additionalCostsRepository.findAdditionalCostsByStartEndDate(start, end, "bezahlt").size());
			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount + closedCount;
			totCountBilling.setName("Total Rechnungen");
			totCountBilling.setValue(totCount.doubleValue());
			retValue.add(totCountBilling);

		} else {

			Long closedCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("bezahlt").size());
			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount + closedCount;
			totCountBilling.setName("Total Rechnungen");
			totCountBilling.setValue(totCount.doubleValue());
			retValue.add(totCountBilling);
		}

		return retValue;
	}

	@Override
	public Iterable<WidgetDTO> getPieChart() {

		List<Ebox> list = eBoxRepository.findByStatusText("gebucht");

		List<WidgetDTO> retValue = new ArrayList<WidgetDTO>();

		WidgetDTO dto = new WidgetDTO();
		WidgetDTO dto1 = new WidgetDTO();
		WidgetDTO dto2 = new WidgetDTO();
		WidgetDTO dto3 = new WidgetDTO();
		WidgetDTO dto4 = new WidgetDTO();
		WidgetDTO dto5 = new WidgetDTO();
		WidgetDTO dto6 = new WidgetDTO();

		dto.setName("Basiskosten");
		dto.setY(list.stream() // Basispreis
				.filter(ebox -> ebox != null && ebox.getPrice() != null).mapToDouble(Ebox::getPrice).sum());
		retValue.add(dto);

		dto1.setName("Nebenkosten");
		dto1.setY(list.stream() // Nebenkosten
				.filter(ebox -> ebox != null && ebox.getAdditionalCosts() != null).mapToDouble(Ebox::getAdditionalCosts)
				.sum());
		retValue.add(dto1);

		dto2.setName("Stromkosten");
		dto2.setY(list.stream() // Stromkosten
				.filter(ebox -> ebox != null && ebox.getStromPrice() != null).mapToDouble(Ebox::getStromPrice).sum());
		retValue.add(dto2);

		dto3.setName("Heizungskosten");
		dto3.setY(list.stream() // Heizungskosten
				.filter(ebox -> ebox != null && ebox.getHeizungPrice() != null).mapToDouble(Ebox::getHeizungPrice)
				.sum());
		retValue.add(dto3);

		dto4.setName("Wasserkosten");
		dto4.setY(list.stream() // Wasserkosten
				.filter(ebox -> ebox != null && ebox.getWasserPrice() != null).mapToDouble(Ebox::getWasserPrice).sum());
		retValue.add(dto4);

		dto5.setName("Internetkosten");
		dto5.setY(list.stream().filter(ebox -> ebox != null && ebox.getInternetPrice() != null)
				.mapToDouble(Ebox::getInternetPrice).sum());
		retValue.add(dto5);

		dto6.setName("Wifikosten");
		dto6.setY(list.stream().filter(ebox -> ebox != null && ebox.getInternetWifiPrice() != null)
				.mapToDouble(Ebox::getInternetWifiPrice).sum());
		retValue.add(dto6);

		return retValue;
	}

	@Override
	public WidgetDTO getCountedContract(String year) throws ParseException {

		WidgetDTO dto = new WidgetDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			dto.setCountVertrag(
					Long.valueOf(contractRepository.findByStatusTexAndStartDate("Vertrag", start, end).size()));
			dto.setCountOfferte(
					Long.valueOf(contractRepository.findByStatusTexAndStartDate("Offerte", start, end).size()));
			dto.setCountAbsage(
					Long.valueOf(contractRepository.findByStatusTexAndStartDate("Absage", start, end).size()));
			dto.setCountKuendigung(
					Long.valueOf(contractRepository.findByStatusTexAndStartDate("Kündigung", start, end).size()));

			dto.setCountGebucht(Long.valueOf(eBoxRepository.findByStatusTexAndStartDate("gebucht", start, end).size()));
			dto.setCountFrei(Long.valueOf(eBoxRepository.findByStatusTexAndStartDate("frei", start, end).size()));
			dto.setCountReserviert(
					Long.valueOf(eBoxRepository.findByStatusTexAndStartDate("reserviert", start, end).size()));
			dto.setCountGekuendigt(
					Long.valueOf(eBoxRepository.findByStatusTexAndStartDate("gekündigt", start, end).size()));
			dto.setCountTotalBox(
					dto.getCountGebucht() + dto.getCountFrei() + dto.getCountReserviert() + dto.getCountGekuendigt());

		} else {
			dto.setCountVertrag(Long.valueOf(contractRepository.findByStatusText("Vertrag").size()));
			dto.setCountOfferte(Long.valueOf(contractRepository.findByStatusText("Offerte").size()));
			dto.setCountAbsage(Long.valueOf(contractRepository.findByStatusText("Absage").size()));
			dto.setCountKuendigung(Long.valueOf(contractRepository.findByStatusText("Kündigung").size()));

			dto.setCountGebucht(Long.valueOf(eBoxRepository.findByStatusText("gebucht").size()));
			dto.setCountFrei(Long.valueOf(eBoxRepository.findByStatusText("frei").size()));
			dto.setCountReserviert(Long.valueOf(eBoxRepository.findByStatusText("reserviert").size()));
			dto.setCountGekuendigt(Long.valueOf(eBoxRepository.findByStatusText("gekündigt").size()));
			dto.setCountTotalBox(
					dto.getCountGebucht() + dto.getCountFrei() + dto.getCountReserviert() + dto.getCountGekuendigt());
		}

		return dto;
	}

	@Override
	public WidgetDTO getDetailPriceEbox() {

		WidgetDTO dto = new WidgetDTO();

		List<Ebox> list = eBoxRepository.findByStatusText("gebucht");

		dto.setNettoPerMonth(list.stream() // Nettopreis
				.filter(ebox -> ebox != null && ebox.getTotalPriceNetto() != null).mapToDouble(Ebox::getTotalPriceNetto)
				.sum());

		dto.setPrice(list.stream() // Basispreis
				.filter(ebox -> ebox != null && ebox.getPrice() != null).mapToDouble(Ebox::getPrice).sum());

		dto.setAdditionalCosts(list.stream() // Nebenkosten
				.filter(ebox -> ebox != null && ebox.getAdditionalCosts() != null).mapToDouble(Ebox::getAdditionalCosts)
				.sum());

		dto.setStromPrice(list.stream() // Stromkosten
				.filter(ebox -> ebox != null && ebox.getStromPrice() != null).mapToDouble(Ebox::getStromPrice).sum());

		dto.setHeizungPrice(list.stream() // Heizungskosten
				.filter(ebox -> ebox != null && ebox.getHeizungPrice() != null).mapToDouble(Ebox::getHeizungPrice)
				.sum());

		dto.setWasserPrice(list.stream() // Wasserkosten
				.filter(ebox -> ebox != null && ebox.getWasserPrice() != null).mapToDouble(Ebox::getWasserPrice).sum());

		dto.setInternetPrice(list.stream().filter(ebox -> ebox != null && ebox.getInternetPrice() != null)
				.mapToDouble(Ebox::getInternetPrice).sum());

		dto.setInternetWifiPrice(list.stream().filter(ebox -> ebox != null && ebox.getInternetWifiPrice() != null)
				.mapToDouble(Ebox::getInternetWifiPrice).sum());

		return dto;
	}

	@Override
	public WidgetDTO getDetailPricePower(String year, String quarter) {

		WidgetDTO dto = new WidgetDTO();

		stromKostenBrutto = 0.00;
		stromKostenNetto = 0.00;
		stromMwst = 0.00;
		sumStormVorausZahlung = 0.00;
		sumStromEinnahmenNetto = 0.00;
		sumStromKostenNetto = 0.00;
		sumSonstigeGuthabenNetto = 0.00;
		stromGebuehren = 0.00;
		diffStromKwatt = 0.00;

		Date start;
		Date end;

		if (year.equals("Alle")) {
			Calendar cal = new GregorianCalendar();
			year = Integer.toString(cal.get(Calendar.YEAR));
		}

		start = calcFirstDayOfYear(year);
		end = calcLastDayOfYear(year);
		int pJahr = Integer.parseInt(year);

		if (quarter.contentEquals("Q1: Jan. - Mar.")) {
			start = new GregorianCalendar(pJahr, 0, 1).getTime();
			end = new GregorianCalendar(pJahr, 2, 31).getTime();
		} else if (quarter.contentEquals("Q2: Apr. - Jun.")) {
			start = new GregorianCalendar(pJahr, 3, 1).getTime();
			end = new GregorianCalendar(pJahr, 5, 30).getTime();
		} else if (quarter.contentEquals("Q3: Jul. - Sep.")) {
			start = new GregorianCalendar(pJahr, 6, 1).getTime();
			end = new GregorianCalendar(pJahr, 8, 30).getTime();
		} else if (quarter.contentEquals("Q4: Okt. - Dez.")) {
			start = new GregorianCalendar(pJahr, 9, 1).getTime();
			end = new GregorianCalendar(pJahr, 11, 31).getTime();
		}

		List<ElectricPeriod> list = electricMeterPeriodRepository.findElectricPeriodByStartEndDate(start, end,
				"bezahlt");

		list.forEach(item -> {

			if (item.getDiffKwatt() != null) {

				if (item.getZaehlerFromPeriode() != null) {

					LocalDate localToPeriod = item.getZaehlerToPeriode().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					LocalDate localFromPeriod = item.getZaehlerFromPeriode().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();

					Long monthsBetween = ChronoUnit.MONTHS.between(YearMonth.from(localFromPeriod),
							YearMonth.from(localToPeriod)) + 1;

					System.out.println(monthsBetween);

					Double stromKostenAkontoMonat = item.getStromAkontoMonat() != null ? item.getStromAkontoMonat()
							: 0.00;
					Double stromPriceNetto = item.getStromPriceNetto() != null ? item.getStromPriceNetto() : 0.00;
					Double stromPreisKw = item.getPreiskW() != null ? item.getPreiskW() : 0.00;
					Double diffKwatt = item.getDiffKwatt() != null ? item.getDiffKwatt() : 0.00;
					Double kundenGuthaben = item.getKundenGuthaben() != null ? item.getKundenGuthaben() : 0.00;
					Double gebuehren = item.getPreisBearbeitungsgebuehr() != null ? item.getPreisBearbeitungsgebuehr()
							: 0.00;

					Double stromKostenBrutto = item.getStromPriceBrutto() != null ? item.getStromPriceBrutto() : 0.00;
					Double stromKostenNetto = item.getStromPriceNetto() != null ? item.getStromPriceNetto() : 0.00;
					Double mwstKosten = item.getMwstPrice() != null ? item.getMwstPrice() : 0.00;

					setStromKostenBrutto(stromKostenBrutto);
					setStromKostenNetto(stromKostenNetto);
					setStromMwst(mwstKosten);
					setStromGebuehren(gebuehren);
					setStromVorausZahlung(stromKostenAkontoMonat, monthsBetween);
					setSumStromEinnahmenNetto(stromPriceNetto);
					setSumStromKostenNetto(stromPreisKw, diffKwatt);
					setSumSonstigeGuthabenNetto(kundenGuthaben);
					setDiffStromKwatt(diffKwatt);
				}

			}

		});

		dto.setSumStromKwWatt(diffStromKwatt);
		dto.setSumStromKostenNetto(kaufRunden(sumStromKostenNetto));

		dto.setSumStromEinnahmenNetto(sumStromEinnahmenNetto);
		dto.setSumStromVorausZahlungenNetto(sumStormVorausZahlung * -1);
		dto.setSumSonstigeGuthabenNetto(sumSonstigeGuthabenNetto * -1);
		dto.setStromGebuehren(stromGebuehren);
		dto.setStromMwst(stromMwst);
		dto.setStromKostenBrutto(stromKostenBrutto);

		return dto;

	}

	@Override
	public WidgetDTO getDetailPriceBills(String year) {

		WidgetDTO dto = new WidgetDTO();

		if (!year.equals("Alle")) {

			Date start = calcFirstDayOfYear(year);
			Date end = calcLastDayOfYear(year);

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCostsByStartEndDate(start, end,
					"bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			dto.setSumBillMwstClosed(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());

			dto.setSumBillBruttoClosed(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());

			Double openPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double verrechnetPrice = listVer.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totalPrice = openPrice + verrechnetPrice;
			dto.setSumBillBruttoOpen(totalPrice);

			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount;
			dto.setCountedBillsOpen(totCount);

		} else {

			List<AdditionalCosts> listBez = additionalCostsRepository.findAdditionalCost("bezahlt");
			List<AdditionalCosts> listOpen = additionalCostsRepository.findAdditionalCost("offen");
			List<AdditionalCosts> listVer = additionalCostsRepository.findAdditionalCost("verrechnet");

			dto.setSumBillMwstClosed(listBez.stream() // Mwst Price
					.filter(item -> item != null && item.getMwstPrice() != null)
					.mapToDouble(AdditionalCosts::getMwstPrice).sum());

			dto.setSumBillBruttoClosed(listBez.stream() // Bruttopreis closed
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum());

			Double verBruttoPrice = listVer.stream() // Bruttopreis verrechnet
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double openBruttoPrice = listOpen.stream() // Bruttopreis open
					.filter(item -> item != null && item.getSumBruttoPrice() != null)
					.mapToDouble(AdditionalCosts::getSumBruttoPrice).sum();

			Double totPriceBrutto = verBruttoPrice + openBruttoPrice;
			dto.setSumBillBruttoOpen(totPriceBrutto);

			dto.setCountedBillsClosed(Long.valueOf(additionalCostsRepository.findAdditionalCost("bezahlt").size()));

			Long openCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("offen").size());
			Long verCount = Long.valueOf(additionalCostsRepository.findAdditionalCost("verrechnet").size());
			Long totCount = openCount + verCount;
			dto.setCountedBillsOpen(totCount);

		}

		return dto;
	}

	@Override
	public Iterable<OutstandPaymentDTO> getPaymentCheck(String year, String month) {
		List<PaymentDatePrice> pdp = paymentDatePriceRepository.findByYear(year);

		ArrayList<OutstandPaymentDTO> outPayList = new ArrayList<OutstandPaymentDTO>();

		pdp.forEach(item -> {

			int startDate = calcMonthStartDate(item.getPayment().getContract().getStartDate());
			int endDate = Integer.valueOf(month);

			// System.out.println("Name: " +
			// item.getPayment().getContract().getCustomer().getName() +
			// " StartDate: "+item.getPayment().getContract().getStartDate() +
			// " EndDate: " +item.getPayment().getContract().getEndDate() );

			for (int i = startDate; i <= endDate; i++) {

				switch (i) {
					case 0:

						if (!item.isExcludeInPaymentList() && (item.getBruttoJanPrice() == null)
								&& item.getBruttoJanDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Jan.", item, outPay));
						}
						break;
					case 1:
						if (!item.isExcludeInPaymentList() && (item.getBruttoFebPrice() == null)
								&& item.getBruttoFebDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Feb.", item, outPay));
						}
						break;
					case 2:
						if (!item.isExcludeInPaymentList() && (item.getBruttoMarPrice() == null)
								&& item.getBruttoMarDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Mar.", item, outPay));
						}
						break;
					case 3:
						if (!item.isExcludeInPaymentList() && (item.getBruttoAprPrice() == null)
								&& item.getBruttoAprDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Apr.", item, outPay));
						}
						break;
					case 4:
						if (!item.isExcludeInPaymentList() && (item.getBruttoMaiPrice() == null)
								&& item.getBruttoMaiDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Mai.", item, outPay));
						}
						break;
					case 5:
						if (!item.isExcludeInPaymentList() && (item.getBruttoJunPrice() == null)
								&& item.getBruttoJunDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Jun.", item, outPay));
						}
						break;
					case 6:
						if (!item.isExcludeInPaymentList() && (item.getBruttoJulPrice() == null)
								&& item.getBruttoJulDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Jul.", item, outPay));
						}
						break;
					case 7:
						if (!item.isExcludeInPaymentList() && (item.getBruttoAugPrice() == null)
								&& item.getBruttoAugDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Aug.", item, outPay));
						}
						break;
					case 8:
						if (!item.isExcludeInPaymentList() && (item.getBruttoSepPrice() == null)
								&& item.getBruttoSepDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Sep.", item, outPay));
						}
						break;
					case 9:
						if (!item.isExcludeInPaymentList() && (item.getBruttoOctPrice() == null)
								&& item.getBruttoOctDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Okt.", item, outPay));
						}
						break;
					case 10:
						if (!item.isExcludeInPaymentList() && (item.getBruttoNovPrice() == null)
								&& item.getBruttoNovDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Nov.", item, outPay));
						}
						break;
					case 11:
						if (!item.isExcludeInPaymentList() && (item.getBruttoDecPrice() == null)
								&& item.getBruttoDecDate() == null
								&& item.getPayment().getAktBruttoPrice() != null
								&& item.getPayment().getAktBruttoPrice() != 0.00) {
							OutstandPaymentDTO outPay = new OutstandPaymentDTO();
							outPayList.add(setBasisData("Dez.", item, outPay));
						}
						break;
					default:
				}
			}

		});
		return outPayList;
	}

	private static OutstandPaymentDTO setBasisData(String month, PaymentDatePrice item, OutstandPaymentDTO outPay) {
		outPay.setCustomerId(item.getPayment().getContract().getCustomer().getId());

		String anrede = item.getPayment().getContract().getCustomer().getAnrede();
		String name = item.getPayment().getContract().getCustomer().getVorname() + " "
				+ item.getPayment().getContract().getCustomer().getName();
		String fimenName = item.getPayment().getContract().getCustomer().getFirmenName() != null
				? item.getPayment().getContract().getCustomer().getFirmenName()
				: "";
		outPay.setCustomer(anrede + " " + name + " " + fimenName);
		outPay.setMonth(month);
		outPay.setContractId(item.getPayment().getContract().getId());
		outPay.setPayememtDatePriceId(item.getId());
		outPay.setBoxNumbers(item.getPayment().getBoxNumbers());
		outPay.setPayment(item.getPayment().getAktBruttoPrice());
		return outPay;

	}

	// Ausgabe des Monats
	private static int calcMonthStartDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal.setTime(startDate);
		int year = cal.get(Calendar.YEAR);
		if (year != cal1.get(Calendar.YEAR)) {
			return 0; // Start from January if contract is not from actual year
		}

		return cal.get(Calendar.MONTH);
	}

	private static Date calcFirstDayOfYear(String jahr) {

		// Erster und letzter Tag im Jahr berechnen!
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(jahr));
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date start = cal.getTime();
		return start;
	}

	private static Date calcLastDayOfYear(String jahr) {

		// Erster und letzter Tag im Jahr berechnen!
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(jahr));
		cal.set(Calendar.MONTH, 11); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
		Date end = cal.getTime();
		return end;
	}

	static Double stromKostenBrutto = 0.00;

	private static void setStromKostenBrutto(Double value) {
		stromKostenBrutto += value;
	}

	static Double stromKostenNetto = 0.00;

	private static void setStromKostenNetto(Double value) {
		stromKostenNetto += value;
	}

	static Double stromMwst = 0.00;

	private static void setStromMwst(Double value) {
		stromMwst += value;
	}

	static Double stromGebuehren = 0.00;

	private static void setStromGebuehren(Double value) {
		stromGebuehren += value;
	}

	static Double sumStormVorausZahlung = 0.00;

	private static void setStromVorausZahlung(Double value, Long value1) {
		sumStormVorausZahlung += value * value1;
	}

	static Double sumStromEinnahmenNetto = 0.00;

	private static void setSumStromEinnahmenNetto(Double value) {
		sumStromEinnahmenNetto += value;
	}

	static Double sumStromKostenNetto = 0.00;

	private static void setSumStromKostenNetto(Double value, Double value1) {
		sumStromKostenNetto += value * value1;
	}

	static Double sumSonstigeGuthabenNetto = 0.00;

	private static void setSumSonstigeGuthabenNetto(Double value) {
		sumSonstigeGuthabenNetto += value;
	}

	static Double diffStromKwatt = 0.00;

	private static void setDiffStromKwatt(Double value) {
		diffStromKwatt += value;
	}

}
