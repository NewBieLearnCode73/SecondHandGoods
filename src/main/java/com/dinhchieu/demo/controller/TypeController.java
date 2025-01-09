package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/api/v1/types")
    public List<String> getAllTypes() {
        return typeService.getAllTypeName();
    }


}
