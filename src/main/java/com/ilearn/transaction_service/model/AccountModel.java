package com.ilearn.transaction_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_details")
public class AccountModel {

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "avail_balance")
    private Double availBalance;
    
	public AccountModel(String accountNumber, Double availBalance) {
		super();
		this.accountNumber = accountNumber;
		this.availBalance = availBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public Double getAvailBalance() {
		return availBalance;
	}

	public void setAvailBalance(Double availBalance) {
		this.availBalance = availBalance;
	}

	@Override
	public String toString() {
		return "AccountModel [accountNumber=" + accountNumber + ", availBalance=" + availBalance + "]";
	}
   
}
