package com.ilearn.transaction_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ilearn.transaction_service.model.TransactionModel;
import com.ilearn.transaction_service.service.TransactionService;
import com.ilearn.transaction_service.util.ApiResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/transactionservice")
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	@PatchMapping("/deposit/{accountNumber}")
	public ApiResponse deposit(@PathVariable String accountNumber,@RequestBody TransactionModel transactionModel) {
		return transactionService.deposit(accountNumber,transactionModel);
	}
	
	@PatchMapping("/withdraw/{accountNumber}")
	public ApiResponse withdraw(@PathVariable String accountNumber,@RequestBody TransactionModel transactionModel) {
		return transactionService.withdraw(accountNumber,transactionModel);
	}
	
	@PostMapping("/transfer")
	public ApiResponse transfer(@RequestBody TransactionModel transactionModel) {
		return transactionService.transfer(transactionModel);
	}
	
	@GetMapping("/transactionList/{accountNumber}")
	public ApiResponse transactionList(@PathVariable String accountNumber) {
		return transactionService.transactionList(accountNumber);
	}
	
}
