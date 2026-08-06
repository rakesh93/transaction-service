package com.ilearn.transaction_service.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ilearn.transaction_service.enums.TransactionType;
import com.ilearn.transaction_service.model.AccountModel;
import com.ilearn.transaction_service.model.TransactionModel;
import com.ilearn.transaction_service.repository.AccountRepository;
import com.ilearn.transaction_service.repository.TransactionRepository;
import com.ilearn.transaction_service.util.ApiResponse;
import com.ilearn.transaction_service.util.AppConstants;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	LocalDateTime now = LocalDateTime.now();
	
	@Transactional
	public ApiResponse deposit(String accountNumber, TransactionModel transactionModel) {
		AccountModel accountModel = accountRepository.findByAccountNumber(accountNumber);

		if (accountModel == null) {
			logger.error("Account not found with this accountNumber {}", accountNumber);
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.ACCOUNT_NOT_FOUND, Collections.emptyList());
		}
		
		if (transactionModel.getAmount() < 0) {
			logger.error("Amount must be greater than 0");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.AMOUNT_GREATER_THAN_ZERO, Collections.emptyList());
		}
		
		transactionModel.setAccountNumber(accountModel.getAccountNumber());
		transactionModel.setTransactionType(TransactionType.DEPOSIT);
		transactionModel.setAvailableAmount(accountModel.getAvailBalance() + transactionModel.getAmount());
		transactionModel.setTransactionDate(now);
		transactionRepository.save(transactionModel);
		accountModel.setAvailBalance(accountModel.getAvailBalance() + transactionModel.getAmount());
		accountRepository.save(accountModel);
		
		return new ApiResponse(AppConstants.SUCCESS, AppConstants.DEPOSIT_SUCCESS , Collections.emptyList());
	}

	@Transactional
	public ApiResponse withdraw(String accountNumber, TransactionModel transactionModel) {
		AccountModel accountModel = accountRepository.findByAccountNumber(accountNumber);

		if (accountModel == null) {
			logger.error("Account not found with this accountNumber {}", accountNumber);
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.ACCOUNT_NOT_FOUND, Collections.emptyList());
		}
		
		if (transactionModel.getAmount() < 0) {
			logger.error("Amount must be greater than 0");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.AMOUNT_GREATER_THAN_ZERO, Collections.emptyList());
		}
		
		transactionModel.setAccountNumber(accountModel.getAccountNumber());
		transactionModel.setTransactionType(TransactionType.WITHDRAW);
		transactionModel.setAvailableAmount(accountModel.getAvailBalance() - transactionModel.getAmount());
		transactionModel.setTransactionDate(now);
		transactionRepository.save(transactionModel);
		accountModel.setAvailBalance(accountModel.getAvailBalance() - transactionModel.getAmount());
		accountRepository.save(accountModel);
		
		return new ApiResponse(AppConstants.SUCCESS, AppConstants.WITHDRAW_SUCCESS , Collections.emptyList());
	}

	@Transactional
	public ApiResponse transfer(TransactionModel transactionModel) {
		
		double minimumBalance = 500;
		
		//Checking From Account Number is Exists or Not
		AccountModel fromAccountModel = accountRepository.findByAccountNumber(transactionModel.getFromAccountNumber());
		if (fromAccountModel == null) {
			logger.error("Sender Account not found with this accountNumber {}", transactionModel.getFromAccountNumber());
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.ACCOUNT_NOT_FOUND, Collections.emptyList());
		}
		
		//Checking To Account Number is Exists or Not
		AccountModel toAccountModel = accountRepository.findByAccountNumber(transactionModel.getToAccountNumber());
		if (toAccountModel == null) {
			logger.error("Receiver Account not found with this accountNumber {}", transactionModel.getToAccountNumber());
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.ACCOUNT_NOT_FOUND, Collections.emptyList());
		}
		
		if(transactionModel.getFromAccountNumber().equals(transactionModel.getToAccountNumber())){
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.SAME_ACCOUNT, Collections.emptyList());
		}
		
		//Checking Amount is Greater than 0
		if (transactionModel.getAmount() <= 0) {
			logger.error("Amount must be greater than 0");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.AMOUNT_GREATER_THAN_ZERO, Collections.emptyList());
		}

		//Checking From Account Balance is Greater than Transfer Amount and minimum Balance keep 500
		if (fromAccountModel.getAvailBalance() - transactionModel.getAmount() < minimumBalance) {
			logger.error("Insufficient balance in Sender account");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.BALANCE_INSUFICIENT, Collections.emptyList());
		}
		
		//Crediting Amount to Receiver Account
		TransactionModel creditTransaction = new TransactionModel();
		creditTransaction.setAccountNumber(toAccountModel.getAccountNumber());
		creditTransaction.setTransactionType(TransactionType.DEPOSIT);
		creditTransaction.setAmount(transactionModel.getAmount());
		creditTransaction.setAvailableAmount(toAccountModel.getAvailBalance() + transactionModel.getAmount());
		creditTransaction.setTransactionDate(now);
		transactionRepository.save(creditTransaction);
		
		//Debiting Amount from Sender Account
		TransactionModel debitTransaction = new TransactionModel();
		debitTransaction.setAccountNumber(fromAccountModel.getAccountNumber());
		debitTransaction.setTransactionType(TransactionType.WITHDRAW);
		debitTransaction.setAmount(transactionModel.getAmount());
		debitTransaction.setAvailableAmount(fromAccountModel.getAvailBalance() - transactionModel.getAmount());
		debitTransaction.setTransactionDate(now);
		transactionRepository.save(debitTransaction);
		
		fromAccountModel.setAvailBalance(fromAccountModel.getAvailBalance() - transactionModel.getAmount());
		accountRepository.save(fromAccountModel);
		
		toAccountModel.setAvailBalance(toAccountModel.getAvailBalance() + transactionModel.getAmount());
		accountRepository.save(toAccountModel);		
		
		return new ApiResponse(AppConstants.SUCCESS, AppConstants.BALANCE_TRANSFER, Collections.emptyList());
	}

	public ApiResponse transactionList(String accountNumber) {
		AccountModel accountModel = accountRepository.findByAccountNumber(accountNumber);
		if (accountModel == null) {
			logger.error("Account not found with this accountNumber {}", accountNumber);
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.ACCOUNT_NOT_FOUND, Collections.emptyList());
		}
		List<TransactionModel> transactionModel = transactionRepository.findAllByAccountNumber(accountNumber);
		transactionModel.sort(Comparator.comparing(TransactionModel::getTransactionId).reversed());
		if(transactionModel.isEmpty()) {
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.RESULT_NOT_FOUND, Collections.emptyList());
		}
		return new ApiResponse(AppConstants.SUCCESS, AppConstants.RESULT_FOUND, transactionModel);
	}
}
