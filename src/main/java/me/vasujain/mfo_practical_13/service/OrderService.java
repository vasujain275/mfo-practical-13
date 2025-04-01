package me.vasujain.mfo_practical_13.service;

import me.vasujain.mfo_practical_13.dto.InvoiceDto;
import me.vasujain.mfo_practical_13.dto.OrderDto;
import me.vasujain.mfo_practical_13.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto, InvoiceDto invoiceDto);
    OrderDto getOrderById(UUID orderId);
    OrderDto addProductsToOrder(UUID orderId, List<ProductDto> productDtoList);
}