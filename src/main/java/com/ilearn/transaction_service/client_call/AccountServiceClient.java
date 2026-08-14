package com.ilearn.transaction_service.client_call;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountServiceClient {
	
	@GetMapping("/accountservice/getAllAccount")
    AccountResponse getAllAccount();
	
}
