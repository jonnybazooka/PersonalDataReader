package persistence.dao;

import persistence.models.Customer;

public interface CustomerDao {
    long saveNewCustomer(String[] rowData);

    long saveNewCustomer(Customer customer);
}
