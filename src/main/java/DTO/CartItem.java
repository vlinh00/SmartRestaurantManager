package DTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItem {
    private int cartItemId;
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public CartItem(int cartItemId, int productId, String productName, BigDecimal price, int quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getCartItemId()   { return cartItemId; }
    public int getProductId()    { return productId; }
    public String getProductName(){ return productName; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity()     { return quantity; }

    public String toLine() {
        return "• " + productName + " x" + quantity + " = " + price.multiply(BigDecimal.valueOf(quantity));
    }

    public static CartItem fromResultSet(ResultSet rs) throws SQLException {
        return new CartItem(
            rs.getInt("cart_item_id"),
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            rs.getInt("quantity")
        );
    }
}
