package persistence.dao;

import persistence.models.Contact;

public interface ContactDao {
    void saveNewContacts(String[] rowData, long customerId);
    void saveNewContact(Contact contact);
}
