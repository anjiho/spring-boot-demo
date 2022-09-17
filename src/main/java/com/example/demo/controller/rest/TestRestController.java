package com.example.demo.controller.rest;

import com.example.demo.logic.TestLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/api/v1/test")
public class TestRestController {

    @Autowired
    private TestLogic testLogic;

    @GetMapping(value = "/sysconfigs")
    public ResponseEntity<Object> getSysStringList() {
        return ResponseEntity.ok(testLogic.getSysLogic());
    }
}
