package com.ilearn.transaction_service.service;

import java.time.LocalDateTime;
import java.util.Collections;

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

	@Transactional
	public ApiResponse deposit(String accountNumber, TransactionModel transactionModel) {
		AccountModel accountModel = accountRepository.findByAccountNumber(accountNumber);

		if (accountModel == null) {
			logger.error("Account not found with this accountNumber {}", accountNumber);
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.RESULT_NOT_FOUND, Collections.emptyList());
		}
		
		if (transactionModel.getAmount() < 0) {
			logger.error("Amount must be greater than 0");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.AMOUNT_GREATER_THAN_ZERO, Collections.emptyList());
		}
		
		transactionModel.setAccountNumber(accountModel.getAccountNumber());
		transactionModel.setTransactionType(TransactionType.DEPOSIT);
		transactionModel.setAvailableAmount(accountModel.getAvailBalance() + transactionModel.getAmount());
		transactionModel.setTransactionDate(LocalDateTime.now());
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
			return new ApiResponse(AppConstants.NOT_FOUND, AppConstants.RESULT_NOT_FOUND, Collections.emptyList());
		}
		
		if (transactionModel.getAmount() < 0) {
			logger.error("Amount must be greater than 0");
			return new ApiResponse(AppConstants.FAILURE, AppConstants.AMOUNT_GREATER_THAN_ZERO, Collections.emptyList());
		}
		
		transactionModel.setAccountNumber(accountModel.getAccountNumber());
		transactionModel.setTransactionType(TransactionType.WITHDRAW);
		transactionModel.setAvailableAmount(accountModel.getAvailBalance() - transactionModel.getAmount());
		transactionModel.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(transactionModel);
		accountModel.setAvailBalance(accountModel.getAvailBalance() - transactionModel.getAmount());
		accountRepository.save(accountModel);
		
		return new ApiResponse(AppConstants.SUCCESS, AppConstants.WITHDRAW_SUCCESS , Collections.emptyList());
	}
	
}
