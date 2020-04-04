package com.herval.booking.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herval.booking.BookingApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerIntegrationTests {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    @Test
    void testGetById() {
        Map<String, Object> response = restTemplate.getForObject("http://localhost:" + port + "/v1/booking/1", Map.class);
        assertNotNull(response);
        String id = response.get("id").toString();
        assertNotNull(id);
        assertEquals("1", id);
        String name = response.get("name").toString();
        assertNotNull(name);
        assertEquals("Booking 1", name);
        boolean isModified = (boolean) response.get("isModified");
        assertEquals(false, isModified);
    }

    @Test
    void testGetById_NoContent() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/booking/99", HttpMethod.GET, entity, Map.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("name", "Booking");
        ResponseEntity<Map[]> response = restTemplate.exchange("http://localhost:" + port + "/v1/booking/?name={name}", HttpMethod.GET, entity, Map[].class, uriVariables);
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
        assertEquals("Booking 1", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
    }

    @Test
    public void testAdd() throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "TestBkng 3");
        requestBody.put("id", "3");
        requestBody.put("userId", "3");
        requestBody.put("restaurantId", "1");
        requestBody.put("tableId", "1");
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        requestBody.put("date", nowDate);
        requestBody.put("time", nowTime);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.findAndRegisterModules();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/v1/booking", HttpMethod.POST, entity, Map.class, Collections.emptyMap());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> responseE = restTemplate.getForObject("http://localhost:" + port + "/v1/booking/3", Map.class);
        assertNotNull(responseE);
        String id = responseE.get("id").toString();
        assertNotNull(id);
        assertEquals("3", id);
        String name = responseE.get("name").toString();
        assertNotNull(name);
        assertEquals("TestBkng 3", name);
        boolean isModified = (boolean) responseE.get("isModified");
        assertEquals(false, isModified);
        String userId = responseE.get("userId").toString();
        assertNotNull(userId);
        assertEquals("3", userId);
        String restaurantId = responseE.get("restaurantId").toString();
        assertNotNull(restaurantId);
        assertEquals("1", restaurantId);
        String tableId = responseE.get("tableId").toString();
        assertNotNull(tableId);
        assertEquals("1", tableId);

        String date1 = responseE.get("date").toString();
        assertNotNull(date1);
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("uuuu-MM-dd").toFormatter(Locale.ENGLISH);
        assertEquals(nowDate, LocalDate.parse(date1, dateTimeFormatter));
        String time1 = responseE.get("time").toString();
        assertNotNull(time1);
        assertEquals(nowTime, LocalTime.parse(time1, dateTimeFormatter.ISO_LOCAL_TIME));
    }

}
