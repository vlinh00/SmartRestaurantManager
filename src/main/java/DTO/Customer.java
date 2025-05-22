
package DTO;

/**
 *
 * @author tienn
 */

import java.time.LocalDateTime;

public class Customer {

    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime createdAt;

    public Customer() {}

    public Customer(int customerId, String fullName, String phone,
                    String email, String address, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.fullName   = fullName;
        this.phone      = phone;
        this.email      = email;
        this.address    = address;
        this.createdAt  = createdAt;
    }

    // -------- getters & setters ----------
    public int getCustomerId()          { return customerId; }
    public void setCustomerId(int id)   { this.customerId = id; }

    public String getFullName()         { return fullName; }
    public void setFullName(String n)   { this.fullName = n; }

    public String getPhone()            { return phone; }
    public void setPhone(String p)      { this.phone = p; }

    public String getEmail()            { return email; }
    public void setEmail(String e)      { this.email = e; }

    public String getAddress()          { return address; }
    public void setAddress(String a)    { this.address = a; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }

    @Override
    public String toString() {
        return fullName + " (" + phone + ")";
    }
}
