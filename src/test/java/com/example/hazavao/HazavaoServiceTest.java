package com.example.hazavao;

import com.example.hazavao.endpoint.Service.HazavaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.when;

class HazavaoServiceTest {

    @Test
    void testGetDefinition() {
        RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
        HazavaoService service = new HazavaoService();


        String mockResponse = "{\"choices\":[{\"text\":\"Définition du mot test.\"}]}";
        when(mockRestTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn(mockResponse);


        ResponseEntity<Map<String, String>> response = service.getDefinition("test");


        assertEquals("test", response.getBody().get("mot"));
        assertEquals("Définition du mot test.", response.getBody().get("definition"));
    }
}