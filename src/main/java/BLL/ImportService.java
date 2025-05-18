/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

/**
 *
 * @author Asus VivoBook
 */
import dto.ImportItemDTO;
import dal.InventoryDAO;
import java.sql.SQLException;
import java.util.List;

public class ImportService {
    private InventoryDAO inventoryDAO = new InventoryDAO();

    public void processImport(List<ImportItemDTO> items) throws SQLException {
        for (ImportItemDTO item : items) {
            inventoryDAO.updateInventory(item.getProduct().getProductId(), item.getQuantity());
        }
    }

    public double calculateTotal(List<ImportItemDTO> items) {
        return items.stream().mapToDouble(ImportItemDTO::getTotalPrice).sum();
    }
}
