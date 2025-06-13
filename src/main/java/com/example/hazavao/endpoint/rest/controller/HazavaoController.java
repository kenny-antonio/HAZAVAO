
package com.example.hazavao.endpoint.rest.controller;

import com.example.hazavao.endpoint.Service.HazavaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HazavaoController {


    @GetMapping("/hazavao")
    public ResponseEntity<Map<String, String>> getDefinition(@RequestParam String teny) {
        return new HazavaoService().getDefinition(teny);
    }
}
