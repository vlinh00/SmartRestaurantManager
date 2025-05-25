
package BLL;

/**
 *
 * @author tienn
 */


import DAL.CustomerRepository;
import DTO.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private final CustomerRepository repo = new CustomerRepository();

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public boolean add(Customer c) {
        if (c.getFullName() == null || c.getFullName().isBlank())
            return false;
        // Có thể thêm validate phone/email tại đây
        return repo.insert(c);
    }

    public boolean update(Customer c) {
        if (c.getCustomerId() <= 0) return false;
        return repo.update(c);
    }

    public boolean delete(int id) {
        return repo.delete(id);
    }

    /** Live-search (client side) theo tên hoặc phone */
    public List<Customer> search(String keyword) {
        String kw = keyword.toLowerCase();
        return getAll().stream()
                .filter(c -> c.getFullName().toLowerCase().contains(kw)
                        || c.getPhone().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }
}
