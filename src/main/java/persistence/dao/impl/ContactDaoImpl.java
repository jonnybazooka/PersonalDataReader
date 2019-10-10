package persistence.dao.impl;

import datasoucre.ConnectionSource;
import persistence.dao.ContactDao;
import persistence.models.Contact;
import persistence.utils.ContactRegex;
import persistence.utils.ContactType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDaoImpl implements ContactDao {
    private final String INSERT_QUERY = "INSERT INTO contacts (CUSTOMER_ID, TYPE, CONTACT) VALUES (?, ?, ?)";

    @Override
    public void saveNewContacts(String[] rowData, long customerId) {
        for (int i = 4; i < rowData.length; i++) {
            if (!rowData[i].isEmpty()) {
                int contactType = getContactType(rowData[i]);
                try (
                        Connection connection = ConnectionSource.getInstance().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
                ) {
                    preparedStatement.setLong(1, customerId);
                    preparedStatement.setInt(2, contactType);
                    preparedStatement.setString(3, rowData[i]);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveNewContact(Contact contact) {
        try (
                Connection connection = ConnectionSource.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)
        ) {
            preparedStatement.setLong(1, contact.getCustomerId());
            preparedStatement.setInt(2, contact.getType());
            preparedStatement.setString(3, contact.getContact());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getContactType(String rawContact) {
        if (rawContact.matches(ContactRegex.EMAIL_REGEX)) {
            return ContactType.EMAIL;
        } else if (rawContact.matches(ContactRegex.PHONE_REGEX)) {
            return ContactType.PHONE;
        } else if (rawContact.matches(ContactRegex.JABBER_REGEX)) {
            return ContactType.JABBER;
        } else {
            return ContactType.UNKNOWN;
        }
    }
}
