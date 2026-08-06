package com.ilearn.transaction_service.model;

import java.time.LocalDateTime;

import com.ilearn.transaction_service.enums.TransactionType;
import com.ilearn.transaction_service.util.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "transactiondetail")
public class TransactionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	int transactionId;

	@Column(name = "account_number")
	@NotBlank(message = AppConstants.ACCOUNT_NUMBER)
	String accountNumber;

	@Column(name = "amount")
	@Positive(message = AppConstants.AMOUNT_GREATER_THAN_ZERO)
	double amount;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Column(name = "available_amount")
	double availableAmount;

	@Column(name = "transaction_date")
	LocalDateTime transactionDate;

	@Transient
	private String fromAccountNumber;

	@Transient
	private String toAccountNumber;

	public TransactionModel() {
	}

	public TransactionModel(int transactionId, String accountNumber, double amount, TransactionType transactionType,
			double availableAmount, LocalDateTime transactionDate, String fromAccountNumber, String toAccountNumber) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.transactionType = transactionType;
		this.availableAmount = availableAmount;
		this.transactionDate = transactionDate;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}


	@Override
	public String toString() {
		return "TransactionModel [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", amount="
				+ amount + ", transactionType=" + transactionType + ", availableAmount=" + availableAmount
				+ ", transactionDate=" + transactionDate + ", fromAccountNumber=" + fromAccountNumber
				+ ", toAccountNumber=" + toAccountNumber + "]";
	}
	
}
