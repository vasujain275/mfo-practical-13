package me.vasujain.mfo_practical_13.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class InvoiceDto {
    private UUID invoiceId;
    private String billingAddress;
    private LocalDate invoiceDate;
    private UUID orderId;
}