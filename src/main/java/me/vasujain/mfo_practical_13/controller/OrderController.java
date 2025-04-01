package me.vasujain.mfo_practical_13.controller;

import me.vasujain.mfo_practical_13.dto.InvoiceDto;
import me.vasujain.mfo_practical_13.dto.OrderDto;
import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.response.ApiResponse;
import me.vasujain.mfo_practical_13.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<OrderDto> createOrder(@RequestBody OrderDto orderDto,
                                             @RequestParam(required = false) String billingAddress,
                                             @RequestParam(required = false) LocalDate invoiceDate) {
        // invoice details can come as query parameters or inside OrderDto as needed
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setBillingAddress(billingAddress);
        invoiceDto.setInvoiceDate(invoiceDate != null ? invoiceDate : LocalDate.now());
        OrderDto createdOrder = orderService.createOrder(orderDto, invoiceDto);
        return ApiResponse.<OrderDto>builder()
                .status(HttpStatus.CREATED)
                .message("Order and Invoice created successfully")
                .data(createdOrder)
                .timestamp(LocalDate.now())
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDto> getOrder(@PathVariable UUID orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        return ApiResponse.<OrderDto>builder()
                .status(HttpStatus.OK)
                .message("Order retrieved successfully")
                .data(orderDto)
                .timestamp(LocalDate.now())
                .build();
    }

    @PostMapping("/{orderId}/products")
    public ApiResponse<OrderDto> addProductsToOrder(@PathVariable UUID orderId, @RequestBody List<ProductDto> productDtos) {
        OrderDto orderDto = orderService.addProductsToOrder(orderId, productDtos);
        return ApiResponse.<OrderDto>builder()
                .status(HttpStatus.OK)
                .message("Products added to Order successfully")
                .data(orderDto)
                .timestamp(LocalDate.now())
                .build();
    }
}
