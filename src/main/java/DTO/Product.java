package DTO;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private boolean isActive;

    public Product(int productId, String name, String description,
                   BigDecimal price, String category, boolean isActive) {
        this.productId   = productId;
        this.name        = name;
        this.description = description;
        this.price       = price;
        this.category    = category;
        this.isActive    = isActive;
    }
    public int getProductId()    { return productId; }
    public String getName()      { return name; }
    public String getDescription(){ return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory()  { return category; }
    public boolean isActive()    { return isActive; }
}
