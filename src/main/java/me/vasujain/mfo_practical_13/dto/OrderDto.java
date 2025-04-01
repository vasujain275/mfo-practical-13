package me.vasujain.mfo_practical_13.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID orderId;
    private String customerName;
    private InvoiceDto invoice;
    private List<ProductDto> productList;
}