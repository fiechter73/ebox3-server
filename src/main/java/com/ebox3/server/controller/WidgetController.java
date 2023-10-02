package com.ebox3.server.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ebox3.server.model.dto.ChartDataMonthDTO;
import com.ebox3.server.model.dto.ChartDataSeriesDTO;
import com.ebox3.server.model.dto.OutstandPaymentDTO;
import com.ebox3.server.model.dto.WidgetDTO;
import com.ebox3.server.service.doc.WidgetService;

@RestController
@RequestMapping("/api/v1/widget")
public class WidgetController {

	@Autowired
	WidgetService widgetService;

	@GetMapping("/bigChart/{year}")
	public @ResponseBody Iterable<WidgetDTO> getBigChart(@PathVariable("year") String year) {
		return widgetService.getBigChart(year);
	}

	@GetMapping("/ngxChartBar/{year}")
	public @ResponseBody Iterable<ChartDataMonthDTO> getNgxChartBar(@PathVariable("year") String year) {
		return widgetService.getNgxChartBar(year);
	}

	@GetMapping("/miete/ngxChartPie")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getNgxCharPie() {
		return widgetService.getNgxCharPie();
	}

	@GetMapping("/count/objectState/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getCountedObjectStateNgxPieChart(
			@PathVariable("year") String year) throws ParseException {
		return widgetService.getCountedObjectStateNgxPieChart(year);
	}

	@GetMapping("/count/contractState/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getCountedContractStateNgxPieChart(
			@PathVariable("year") String year) throws ParseException {
		return widgetService.getCountedContractStateNgxPieChart(year);
	}

	@GetMapping("/count/customerState/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getCountedCustomerStateNgxPieChart(
			@PathVariable("year") String year) throws ParseException {
		return widgetService.getCountedCustomerStateNgxPieChart(year);
	}

	@GetMapping("detailprice/billing/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getDetailPriceNgxChart(@PathVariable("year") String year) {
		return widgetService.getDetailPriceNgxChart(year);
	}

	@GetMapping("/price/strom/kWatt/{year}/{quarter}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getkWattChart(@PathVariable("year") String year,
			@PathVariable("quarter") String quarter) {
		return widgetService.getkWattChart(year, quarter);
	}

	@GetMapping("/price/strom/totalCosts/{year}/{quarter}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getPricePowerNgxChart1(@PathVariable("year") String year,
			@PathVariable("quarter") String quarter) {
		return widgetService.getPricePowerNgxChart1(year, quarter);
	}

	@GetMapping("detail/bills/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getDetailPriceBill(@PathVariable("year") String year) {
		return widgetService.getDetailPriceBill(year);
	}

	@GetMapping("detail/bills/count/{year}")
	public @ResponseBody Iterable<ChartDataSeriesDTO> getDetailPriceBillCount(@PathVariable("year") String year) {
		return widgetService.getDetailPriceBillCount(year);
	}

	@GetMapping("/miete/pieChart")
	public @ResponseBody Iterable<WidgetDTO> getPieChart() {
		return widgetService.getPieChart();
	}

	@GetMapping("/count/contract/{year}")
	public ResponseEntity<WidgetDTO> getCountedContract(@PathVariable("year") String year) throws ParseException {
		return ResponseEntity.ok(widgetService.getCountedContract(year));
	}

	@GetMapping("/detailprice/ebox")
	public ResponseEntity<WidgetDTO> getDetailPriceEbox() {
		return ResponseEntity.ok(widgetService.getDetailPriceEbox());
	}

	@GetMapping("/detailprice/strom/{year}/{quarter}")
	public ResponseEntity<WidgetDTO> getDetailPricePower(@PathVariable("year") String year,
			@PathVariable("quarter") String quarter) {
		return ResponseEntity.ok(widgetService.getDetailPricePower(year, quarter));
	}

	@GetMapping("detailprice/bills/{year}")
	public ResponseEntity<WidgetDTO> getDetailPriceBills(@PathVariable("year") String year) {
		return ResponseEntity.ok(widgetService.getDetailPriceBills(year));
	}

	@GetMapping("/payment/check/{year}/{month}")
	public @ResponseBody Iterable<OutstandPaymentDTO> getPaymentCheck(@PathVariable("year") String year,
			@PathVariable("month") String month) {
		return widgetService.getPaymentCheck(year, month);
	}

}
