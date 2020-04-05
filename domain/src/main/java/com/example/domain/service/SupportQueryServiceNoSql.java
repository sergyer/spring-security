package com.example.domain.service;

import com.example.domain.dto.PostDto;
import com.example.domain.dto.SupportQueryDto;
import com.example.domain.model.SupportQuery;
import com.example.domain.repository.SupportQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SupportQueryServiceNoSql implements SupportQueryService {

    private final SupportQueryRepository supportRepository;

    @Override
    public List<SupportQueryDto> getSupportQueriesForUser(final String username) {
        List<SupportQuery> supportQueries = supportRepository.findByUsername(username);
        return mapEntityToModel(supportQueries);
    }

    @Override
    public SupportQueryDto getSupportQueryById(String id) {
        return mapEntityToModel(this.supportRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<SupportQueryDto> getSupportQueriesForAllUsers() {
        List<SupportQuery> supportQueries = this.supportRepository.findAll();
        return mapEntityToModel(supportQueries);
    }

    private List<SupportQueryDto> mapEntityToModel(List<SupportQuery> supportQueries) {
        return supportQueries.stream().map(this::mapEntityToModel).collect(Collectors.toList());
    }

    private SupportQueryDto mapEntityToModel(SupportQuery supportQuery) {
        List<PostDto> posts = supportQuery.getPosts().stream().map(post -> new PostDto(post.getId(), post.getContent(), post.getUsername(), supportQuery.isResolved())).collect(Collectors.toList());
        return new SupportQueryDto(supportQuery.getId(), supportQuery.getSubject(), supportQuery.getCreated(),
                supportQuery.getUsername(), supportQuery.isResolved(), posts);

    }

}
