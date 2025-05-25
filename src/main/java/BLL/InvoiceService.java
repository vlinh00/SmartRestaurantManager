/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.InvoiceRepository;
import DTO.Invoice;
import java.util.List;

/**
 *
 * @author lelin
 */
public class InvoiceService {
    private InvoiceRepository repo = new InvoiceRepository();

    public int createInvoice(Invoice invoice) {
        return repo.insertInvoice(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repo.getAllInvoices();
    }
}

