package com.example.domain.service;

import com.example.domain.dto.CreateSupportQueryDto;
import com.example.domain.dto.PostDto;

public interface SupportCommandService {

    void createQuery(CreateSupportQueryDto query, String username);

    void postToQuery(PostDto supportQueryPostModel, String username);

    void resolveQuery(String id);

}
