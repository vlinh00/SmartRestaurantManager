/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Asus VivoBook
 */
public class ProductDTO {
    private int productId;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean isActive;

    public ProductDTO(int productId, String name, String description, double price, String category, boolean isActive) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isActive = isActive;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isActive() { return isActive; }
}
