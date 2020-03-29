package com.example.domain.service;

import com.example.domain.dto.CreateSupportQueryDto;
import com.example.domain.dto.PostDto;

public interface SupportCommandService {

	void createQuery(CreateSupportQueryDto query);

	void postToQuery(PostDto supportQueryPostModel);

	void resolveQuery(String id);
	
}
