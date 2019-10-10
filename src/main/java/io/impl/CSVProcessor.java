package io.impl;

import io.FileProcessor;
import persistence.dao.ContactDao;
import persistence.dao.CustomerDao;
import persistence.dao.impl.ContactDaoImpl;
import persistence.dao.impl.CustomerDaoImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CSVProcessor implements FileProcessor {

    private CustomerDao customerDao;
    private ContactDao contactDao;

    public CSVProcessor() {
        this.customerDao = new CustomerDaoImpl();
        this.contactDao = new ContactDaoImpl();
    }

    public void processFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(filePath)), StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split(",");
                long customerId = customerDao.saveNewCustomer(rowData);
                if (customerId != 0) {
                    contactDao.saveNewContacts(rowData, customerId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
