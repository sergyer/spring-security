package com.example.domain.service;

import com.example.domain.dto.CreateSupportQueryDto;
import com.example.domain.dto.PostDto;
import com.example.domain.model.Post;
import com.example.domain.model.SupportQuery;
import com.example.domain.repository.SupportQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SupportCommandServiceNoSql implements SupportCommandService {

	private final SupportQueryRepository supportRepository;
	
	@Override
	public void createQuery(CreateSupportQueryDto query, final String username) {
		supportRepository.save(mapModelToEntity(query,username));
	}
	
	@Override
	public void postToQuery(PostDto model, final String username) {
		Post post = new Post(username , model.getContent(), System.currentTimeMillis());
		SupportQuery query = supportRepository.findById(model.getQueryId()).get();
		query.addPost(post);
		if(model.isResolve()) {
			query.resolve();
		}
		supportRepository.save(query);
	}
	
	@Override
	public void resolveQuery(String id) {
		SupportQuery query = supportRepository.findById(id).get();
		query.resolve();
		supportRepository.save(query);
	}
	
	private SupportQuery mapModelToEntity(CreateSupportQueryDto model, final String username) {
		SupportQuery supportQuery = new SupportQuery(username , model.getSubject());
		supportQuery.addPost(model.getContent(), username );
		return supportQuery;
	}
	
}
