package com.ilearn.transaction_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ilearn.transaction_service.model.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, Long> {
}
