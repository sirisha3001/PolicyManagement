package com.plms.model;

public class Policy {
	
	private Integer policyId;
	private String policyName;
	private String policyDetails;
	private String amountPaid;
	private String policyEndDate;
	private String isPolicyValid;
	
	

	public Policy(Integer policyId,String policyName, String policyDetails) {
		super();
		this.setPolicyId(policyId);
		this.policyName = policyName;
		this.policyDetails = policyDetails;
	}
	
	

	public Policy(String policyName, String amountPaid, String policyEndDate) {
		super();
		this.policyName = policyName;
		this.amountPaid = amountPaid;
		this.policyEndDate = policyEndDate;
	}



	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getPolicyDetails() {
		return policyDetails;
	}

	public void setPolicyDetails(String policyDetails) {
		this.policyDetails = policyDetails;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(String policyEndDate) {
		this.policyEndDate = policyEndDate;
	}



	public String getIsPolicyValid() {
		return isPolicyValid;
	}



	public void setIsPolicyValid(String isPolicyValid) {
		this.isPolicyValid = isPolicyValid;
	}



	public Integer getPolicyId() {
		return policyId;
	}



	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

}
