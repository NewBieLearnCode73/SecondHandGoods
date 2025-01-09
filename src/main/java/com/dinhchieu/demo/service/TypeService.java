package com.dinhchieu.demo.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {
    List<String> getAllTypeName();
}
