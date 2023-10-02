package com.ebox3.server.model.dto;

import java.util.List;

public class ChartDataMonthDTO {

	private String name;
	private List<ChartDataSeriesDTO> series;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChartDataSeriesDTO> getSeries() {
		return series;
	}

	public void setSeries(List<ChartDataSeriesDTO> serie) {
		this.series = serie;
	}

}
