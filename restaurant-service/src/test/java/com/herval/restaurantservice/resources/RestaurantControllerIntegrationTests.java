package com.herval.restaurantservice.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herval.restaurantservice.RestaurantApplication;
import com.herval.restaurantservice.domain.model.entity.Table;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                 properties = {"management.server.port=0", "management.context-path=/admin"})
class RestaurantControllerIntegrationTests extends AbstractRestaurantController {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void testGetById() {
        Map<String, Object> response = restTemplate.getForObject("http://localhost:" + port + "/v1/restaurants/1", Map.class);
        assertNotNull(response);
        String id = response.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = response.get("name").toString();
        assertNotNull(name);
        assertEquals("Le Maurice", name);
        boolean isModified = (boolean) response.get("isModified");
        assertEquals(false, isModified);
        List<Table> tableList = (List<Table>) response.get("tables");
        assertNull(tableList);
    }

    @Test
    void testGetById_NoContent() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/restaurants/99", HttpMethod.GET, entity, Map.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("name", "Meurice");
        ResponseEntity<Map[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/restaurants?name={name}", HttpMethod.GET, entity, Map[].class, uriVariables);
        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object>[] responses = response.getBody();
        assertNotNull(responses);
        assertTrue(responses.length == 1);

        Map<String, Object> responseE = responses[0];
        String id = responseE.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = responseE.get("name").toString();
        assertNotNull(name);
        assertEquals("Le Maurice", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
        List<Table> tableList = (List<Table>) responseE.get("tables");
        assertNull(tableList);
    }

    @Test
    public void testAdd() throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "La Plaza Restaurant");
        requestBody.put("id", "11");
        requestBody.put("address", "Address of La Plaza Restaurant");
        Map<String, Object> table1 = new HashMap<>();
        table1.put("name", "Table 1");
        table1.put("id", BigInteger.ONE);
        table1.put("capacity", Integer.valueOf(6));
        Map<String, Object> table2 = new HashMap<>();
        table2.put("name", "Table 2");
        table2.put("id", BigInteger.valueOf(2));
        table2.put("capacity", Integer.valueOf(4));
        Map<String, Object> table3 = new HashMap<>();
        table3.put("name", "Table 3");
        table3.put("id", BigInteger.valueOf(3));
        table3.put("capacity", Integer.valueOf(2));
        List<Map<String, Object>> tableList = new ArrayList<>();
        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        requestBody.put("tables", tableList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/restaurants", HttpMethod.POST, entity, Map.class, Collections.emptyMap());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> responseE = restTemplate.getForObject("http://localhost:" + port + "/v1/restaurants/11", Map.class);
        assertNotNull(responseE);
        String id = responseE.get("id").toString();
        assertNotNull(id);
        assertEquals("11", id);
        String name = responseE.get("name").toString();
        assertNotNull(name);
        assertEquals("La Plaza Restaurant", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
        String address = responseE.get("address").toString();
        assertNotNull(address);
        assertEquals("Address of La Plaza Restaurant", address);
        List<Map<String, Object>> tableList2 = (List<Map<String, Object>>) responseE.get("tables");
        assertNotNull(tableList2);
        assertEquals(tableList2.size(), 3);
        tableList2.stream().forEach((table) -> {
            assertNotNull(table);
            assertNotNull(table.get("name"));
            assertNotNull(table.get("id"));
            assertTrue((Integer) table.get("capacity") > 0);
        });
    }

}
