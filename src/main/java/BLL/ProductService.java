/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import java.util.List;
import DAL.ProductRepository;
import DTO.Product;
import javax.swing.JTable;

/**
 *
 * @author lelin
 */
public class ProductService {

    private final ProductRepository productRepository = new ProductRepository();

    public List<Product> getAllProducts() {
        return productRepository.getAllData();
    }

    public List<Product> getAllProductsActive() {
        return productRepository.getListProductActive();
    }

    public List<Product> getAllProductsActiveByType(int type) {
        return productRepository.getListProductActiveByType(type);
    }

    public boolean addProduct(Product product) {
        return productRepository.insertData(product);
    }

    public boolean updateProductById(Product product) {
        return productRepository.updateDataById(product);
    }

    public boolean updateStockById(int id, int qty) {
        return productRepository.updateQtyById(id, qty);
    }

    public void importProducts(JTable tbl) {
        productRepository.importProductsFromTable(tbl);
    }

    public boolean deleteProduct(int productId) {
        return productRepository.deleteData(productId);
    }

    public List<Product> getAllProductByName(String name) {
        return productRepository.getAllDataByName(name);
    }
}
