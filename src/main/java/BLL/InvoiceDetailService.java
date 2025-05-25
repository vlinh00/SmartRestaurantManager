/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.InvoiceDetailRepository;
import DTO.InvoiceDetail;
import java.util.List;

/**
 *
 * @author lelin
 */
public class InvoiceDetailService {
    private InvoiceDetailRepository repo = new InvoiceDetailRepository();

    public boolean addInvoiceDetail(InvoiceDetail detail) {
        return repo.insertInvoiceDetail(detail);
    }

    public List<InvoiceDetail> getDetails(int invoiceId) {
        return repo.getDetailsByInvoiceId(invoiceId);
    }
}
