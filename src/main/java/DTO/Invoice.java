/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;

/**
 *
 * @author lelin
 */
public class Invoice {
    private int invoiceId;
    private int employeeId;
    private int customerId;
    private Timestamp createdAt;
    private double totalAmount;
    private int promotionId;

    public Invoice() {
    }

    public Invoice(int invoiceId, int employeeId, int customerId, Timestamp createdAt, double totalAmount, int promotionId) {
        this.invoiceId = invoiceId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.promotionId = promotionId;
    }


    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }
    
    
}
