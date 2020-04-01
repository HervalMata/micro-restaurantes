package com.herval.userservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herval.userservice.UserServiceApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTests {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void testGetById() {
        Map<String, Object> response = restTemplate.getForObject("http://localhost:" + port + "/v1/user/1", Map.class);
        assertNotNull(response);
        String id = response.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = response.get("name").toString();
        boolean isModified = (boolean) response.get("isModified");
        assertEquals(false, isModified);
    }

    @Test
    void testGetById_NoContent() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/user/99", HttpMethod.GET, entity, Map.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("name", "User");
        ResponseEntity<Map[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/user/?name={name}", HttpMethod.GET, entity, Map[].class, uriVariables);
        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object>[] responses = response.getBody();
        assertNotNull(responses);
        assertTrue(responses.length == 2);

        Map<String, Object> responseE = responses[0];
        String id = responseE.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = responseE.get("name").toString();
        assertNotNull(name);
        assertEquals("User Name 1", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
    }

    @Test
    public void testAdd() throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Testusr 3");
        requestBody.put("id", "3");
        requestBody.put("address", "Address for 3rd User");
        requestBody.put("city", "City");
        requestBody.put("phoneNo", "9999933333");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.findAndRegisterModules();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/user", HttpMethod.POST, entity, Map.class, Collections.emptyMap());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> responseE = restTemplate.getForObject("http://localhost:" + port + "/v1/user/3", Map.class);
        assertNotNull(responseE);
        String id = responseE.get("id").toString();
        assertNotNull(id);
        assertEquals("3", id);
        String name = responseE.get("name").toString();
        assertNotNull(name);
        assertEquals("Testusr 3", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
        String address = responseE.get("address").toString();
        assertNotNull(address);
        assertEquals("Address for 3rd User", address);
        String city = responseE.get("city").toString();
        assertNotNull(city);
        assertEquals("City", city);
        String phoneNo = responseE.get("phoneNo").toString();
        assertNotNull(phoneNo);
        assertEquals("9999933333", phoneNo);
    }

}
