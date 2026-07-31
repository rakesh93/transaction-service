package com.ilearn.transaction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ilearn.transaction_service.model.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer>{

}
