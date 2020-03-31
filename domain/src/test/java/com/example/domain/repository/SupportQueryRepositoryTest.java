package com.example.domain.repository;

import com.example.domain.model.SupportQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class SupportQueryRepositoryTest {

	@Autowired
	private SupportQueryRepository supportRepository;

	@Before
	public void setup() {
		supportRepository.deleteAll();
		SupportQuery query = new SupportQuery("snakamoto","Unable to Remove Transaction");
		supportRepository.save(query);
	}
	
	@Test
	public void testRetrieveSupportQuery() {
		List<SupportQuery> supportQueries = supportRepository.findByUsername("snakamoto");
		assertEquals(1, supportQueries.size());
		assertNotNull(supportQueries.get(0).getId());
	}
	
	@Test
	public void testUpdateSUpportQueryWithNewPost() {
		SupportQuery query = supportRepository.findByUsername("snakamoto").get(0);
		String queryId = query.getId();
		query.addPost("Cannot remove transaction 12345", "snakamoto");
		supportRepository.save(query);
		final SupportQuery supportQuery = supportRepository.findById(queryId).orElseGet(Assertions::fail);
		assertEquals(1, supportQuery.getPosts().size());
		assertEquals("Cannot remove transaction 12345", supportQuery.getPosts().get(0).getContent());
	}
	
}
