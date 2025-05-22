package DTO;

import java.math.BigDecimal;

public record Order(int orderId, String status, BigDecimal total) {}
