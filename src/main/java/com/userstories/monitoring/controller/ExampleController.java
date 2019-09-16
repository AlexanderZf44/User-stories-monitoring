package com.userstories.monitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.userstories.monitoring.util.constants.UserActionUrlCodes.EXAMPLE_REQUEST;

/**
 * API для примера
 */
@RestController
@RequestMapping(path = "/example-api")
@RequiredArgsConstructor
public class ExampleController {

    /**
     * Эндпоинт для тестового запроса
     */
    @GetMapping(EXAMPLE_REQUEST)
    public ResponseEntity startScheduledCronJob() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
