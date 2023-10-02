package com.ebox3.server.model.dto;

public class ContractSelectionDTO {
	
	private Long customerId;
	private Long contractId;
	private String viewValue;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getViewValue() {
		return viewValue;
	}
	public void setViewValue(String viewValue) {
		this.viewValue = viewValue;
	}
	
	@Override
	public String toString() {
		return "ContractSelectionDTO [customerId=" + customerId + ", contractId=" + contractId + ", viewValue="
				+ viewValue + "]";
	}
}
