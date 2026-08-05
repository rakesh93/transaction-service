package com.ilearn.transaction_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accountdetail")
public class AccountModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "available_balance")
    private Double availBalance;
    
    public AccountModel() {
	}
    
	public AccountModel(Long accountId, String accountNumber, Double availBalance) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.availBalance = availBalance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(Double availBalance) {
		this.availBalance = availBalance;
	}

	@Override
	public String toString() {
		return "AccountModel [accountId=" + accountId + ", accountNumber=" + accountNumber + ", availBalance="
				+ availBalance + "]";
	}
   
}
