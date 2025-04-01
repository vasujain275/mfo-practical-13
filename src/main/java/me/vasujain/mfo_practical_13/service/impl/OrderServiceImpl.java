package me.vasujain.mfo_practical_13.service.impl;

import me.vasujain.mfo_practical_13.dto.InvoiceDto;
import me.vasujain.mfo_practical_13.dto.OrderDto;
import me.vasujain.mfo_practical_13.dto.ProductDto;
import me.vasujain.mfo_practical_13.dto.ReviewDto;
import me.vasujain.mfo_practical_13.model.Invoice;
import me.vasujain.mfo_practical_13.model.Order;
import me.vasujain.mfo_practical_13.model.Product;
import me.vasujain.mfo_practical_13.model.Review;
import me.vasujain.mfo_practical_13.repository.InvoiceRepository;
import me.vasujain.mfo_practical_13.repository.OrderRepository;
import me.vasujain.mfo_practical_13.repository.ProductRepository;
import me.vasujain.mfo_practical_13.repository.ReviewRepository;
import me.vasujain.mfo_practical_13.service.OrderService;
import me.vasujain.mfo_practical_13.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, InvoiceRepository invoiceRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto, InvoiceDto invoiceDto) {
        // Create Order
        Order order = new Order();
        order.setCustomerName(orderDto.getCustomerName());
        order = orderRepository.save(order);

        // Create Invoice and associate with order
        Invoice invoice = new Invoice();
        invoice.setBillingAddress(invoiceDto.getBillingAddress());
        invoice.setInvoiceDate(invoiceDto.getInvoiceDate() != null ? invoiceDto.getInvoiceDate() : LocalDate.now());
        invoice.setOrder(order);
        invoice = invoiceRepository.save(invoice);

        // Set back-reference
        order.setInvoice(invoice);
        order = orderRepository.save(order);

        // Map to DTO and return
        OrderDto responseDto = new OrderDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setCustomerName(order.getCustomerName());
        InvoiceDto responseInvoice = new InvoiceDto();
        responseInvoice.setInvoiceId(invoice.getInvoiceId());
        responseInvoice.setBillingAddress(invoice.getBillingAddress());
        responseInvoice.setInvoiceDate(invoice.getInvoiceDate());
        responseInvoice.setOrderId(order.getOrderId());
        responseDto.setInvoice(responseInvoice);
        return responseDto;
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCustomerName(order.getCustomerName());
        if(order.getInvoice() != null) {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setInvoiceId(order.getInvoice().getInvoiceId());
            invoiceDto.setBillingAddress(order.getInvoice().getBillingAddress());
            invoiceDto.setInvoiceDate(order.getInvoice().getInvoiceDate());
            invoiceDto.setOrderId(order.getOrderId());
            orderDto.setInvoice(invoiceDto);
        }
        if(order.getProductList() != null) {
            List<ProductDto> productDtos = order.getProductList().stream().map(product -> {
                ProductDto dto = new ProductDto();
                dto.setProductId(product.getProductId());
                dto.setProductName(product.getProductName());
                dto.setPrice(product.getPrice());
                return dto;
            }).collect(Collectors.toList());
            orderDto.setProductList(productDtos);
        }
        return orderDto;
    }

    @Override
    public OrderDto addProductsToOrder(UUID orderId, List<ProductDto> productDtoList) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Save products before associating with the order
        List<Product> products = productDtoList.stream()
                .map(dto -> {
                    Product product = new Product();
                    product.setProductName(dto.getProductName());
                    product.setPrice(dto.getPrice());
                    return productRepository.save(product); // Explicitly save each product
                })
                .collect(Collectors.toList());

        order.getProductList().addAll(products); // Associate products with order
        order = orderRepository.save(order); // Save updated order

        return getOrderById(order.getOrderId());
    }

    @Service
    public static class ReviewServiceImpl implements ReviewService {

        private final ReviewRepository reviewRepository;

        @Autowired
        public ReviewServiceImpl(ReviewRepository reviewRepository) {
            this.reviewRepository = reviewRepository;
        }

        @Override
        public ReviewDto getReviewById(UUID reviewId) {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));
            ReviewDto dto = new ReviewDto();
            dto.setReviewId(review.getReviewId());
            dto.setComment(review.getComment());
            dto.setRating(review.getRating());
            dto.setProductId(review.getProduct().getProductId());
            return dto;
        }
    }
}
