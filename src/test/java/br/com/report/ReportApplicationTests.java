package br.com.report;

import br.com.report.models.Problem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/problem",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() {
        Problem problem = restTemplate.getForObject(getRootUrl() + "/problem/1", Problem.class);
        System.out.println(problem.getName());
        Assert.assertNotNull(problem);
    }

    @Test
    public void testCreateUser() {
        Problem problem = new Problem();
        problem.setName("admin@gmail.com");
        problem.setOrigin("admin");
        problem.setHour("admin");
        problem.setDate("admin");

        ResponseEntity<Problem> postResponse = restTemplate.postForEntity(getRootUrl() + "/problem", problem, Problem.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());

    }

    @Test
    public void testUpdatePost() {

        int id = 1;
        Problem problem = restTemplate.getForObject(getRootUrl() + "/problem/" + id, Problem.class);
        problem.setName("admin1");
        problem.setOrigin("admin2");

        restTemplate.put(getRootUrl() + "/problem/" + id, problem);
        Problem updatedUser = restTemplate.getForObject(getRootUrl() + "/problem/" + id, Problem.class);

        Assert.assertNotNull(updatedUser);

    }

    @Test
    public void testDeletePost() {
        int id = 2;

        Problem problem = restTemplate.getForObject(getRootUrl() + "/problem/" + id, Problem.class);
        Assert.assertNotNull(problem);

        restTemplate.delete(getRootUrl() + "/problem/" + id);
        try {
            problem = restTemplate.getForObject(getRootUrl() + "/problems/" + id, Problem.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
