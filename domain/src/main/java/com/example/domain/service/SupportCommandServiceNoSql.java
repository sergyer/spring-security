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
	public void createQuery(CreateSupportQueryDto query) {
		supportRepository.save(mapModelToEntity(query));
	}
	
	@Override
	public void postToQuery(PostDto model) {
		Post post = new Post(getUsername() , model.getContent(), System.currentTimeMillis());
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
	
	private SupportQuery mapModelToEntity(CreateSupportQueryDto model) {
		SupportQuery supportQuery = new SupportQuery(getUsername() , model.getSubject());
		supportQuery.addPost(model.getContent(), getUsername() );
		return supportQuery;
	}
	
	private String getUsername() {
//		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return ((User)principle).getUsername();
		return null;
	}
	
}
