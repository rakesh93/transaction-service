package com.ilearn.transaction_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ilearn.transaction_service.model.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer>{

	List<TransactionModel> findAllByAccountNumber(String accountNumber);

}
