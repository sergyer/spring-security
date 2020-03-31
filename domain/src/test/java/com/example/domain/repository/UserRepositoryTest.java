package com.example.domain.repository;

import com.example.domain.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testFindByUsername() {
        repository.save(new User("wally", "Wally", "Lewis", "Straus", "wally@o2.nz", "password"));
        User userDetails = repository.findByUsername("Wally");
        Assert.assertEquals("Wally", userDetails.getUsername());
        Assert.assertEquals("Lewis", userDetails.getFirstName());
        Assert.assertEquals("Straus", userDetails.getLastName());
        Assert.assertEquals("wally@o2.nz", userDetails.getEmail());
        Assert.assertEquals("password", userDetails.getPassword());
    }

    @Test
    public void testFindByEmail() {
        User userDetails = repository.findByEmail("wally@o2.nz");
        Assert.assertEquals("Wally", userDetails.getUsername());
        Assert.assertEquals("Lewis", userDetails.getFirstName());
        Assert.assertEquals("Straus", userDetails.getLastName());
        Assert.assertEquals("wally@o2.nz", userDetails.getEmail());
    }

}
