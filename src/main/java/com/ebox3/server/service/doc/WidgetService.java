package com.ebox3.server.service.doc;

import java.text.ParseException;
import com.ebox3.server.model.dto.ChartDataMonthDTO;
import com.ebox3.server.model.dto.ChartDataSeriesDTO;
import com.ebox3.server.model.dto.OutstandPaymentDTO;
import com.ebox3.server.model.dto.WidgetDTO;

public interface WidgetService {

	public Iterable<WidgetDTO> getBigChart(String year);

	public Iterable<ChartDataMonthDTO> getNgxChartBar(String year);

	public Iterable<ChartDataSeriesDTO> getNgxCharPie();

	public Iterable<ChartDataSeriesDTO> getCountedObjectStateNgxPieChart(String year) throws ParseException;

	public Iterable<ChartDataSeriesDTO> getCountedContractStateNgxPieChart(String year) throws ParseException;

	public Iterable<ChartDataSeriesDTO> getCountedCustomerStateNgxPieChart(String year) throws ParseException;

	public Iterable<ChartDataSeriesDTO> getDetailPriceNgxChart(String year);

	public Iterable<ChartDataSeriesDTO> getkWattChart(String year, String quarter);

	public Iterable<ChartDataSeriesDTO> getPricePowerNgxChart1(String year, String quarter);

	public Iterable<ChartDataSeriesDTO> getDetailPriceBill(String year);

	public Iterable<ChartDataSeriesDTO> getDetailPriceBillCount(String year);

	public Iterable<WidgetDTO> getPieChart();

	public WidgetDTO getCountedContract(String year) throws ParseException;

	public WidgetDTO getDetailPriceEbox();

	public WidgetDTO getDetailPricePower(String year, String quarter);

	public WidgetDTO getDetailPriceBills(String year);

	public Iterable<OutstandPaymentDTO> getPaymentCheck(String year, String month);

}
