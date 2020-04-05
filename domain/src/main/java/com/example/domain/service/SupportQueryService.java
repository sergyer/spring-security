package com.example.domain.service;

import com.example.domain.dto.SupportQueryDto;

import java.util.List;

public interface SupportQueryService {

	List<SupportQueryDto> getSupportQueriesForUser(String username);

	SupportQueryDto getSupportQueryById(String queryId);

	List<SupportQueryDto> getSupportQueriesForAllUsers();
	
}
