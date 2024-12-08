package com.dinhchieu.demo.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponseDTO<T> {
    private List<T> items;
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int pageSize;
}
