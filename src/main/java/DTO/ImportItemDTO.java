/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Asus VivoBook
 */
public class ImportItemDTO {
    private ProductDTO product;
    private int quantity;
    private double price;

    public ImportItemDTO(ProductDTO product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDTO getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return quantity * price; }
}
