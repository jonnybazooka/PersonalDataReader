package io.impl;

import io.FileProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import persistence.dao.ContactDao;
import persistence.dao.CustomerDao;
import persistence.dao.impl.ContactDaoImpl;
import persistence.dao.impl.CustomerDaoImpl;
import persistence.models.Contact;
import persistence.utils.ContactType;
import persistence.models.Customer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class XMLProcessor implements FileProcessor {
    private Customer customer;
    private List<Contact> contacts;
    private CustomerDao customerDao;
    private ContactDao contactDao;

    public XMLProcessor() {
        this.customerDao = new CustomerDaoImpl();
        this.contactDao = new ContactDaoImpl();
    }

    public void processFile(String filePath) {

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            DefaultHandler defaultHandler = new DefaultHandler() {
                boolean bName = false;
                boolean bSurname = false;
                boolean bAge = false;
                boolean bEmail = false;
                boolean bPhone = false;
                boolean bJabber = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("person")) {
                        customer = new Customer();
                    }
                    if (qName.equalsIgnoreCase("name")) {
                        bName = true;
                    }
                    if (qName.equalsIgnoreCase("surname")) {
                        bSurname = true;
                    }
                    if (qName.equalsIgnoreCase("age")) {
                        bAge = true;
                    }
                    if (qName.equalsIgnoreCase("contacts")) {
                        contacts = new LinkedList<>();
                    }
                    if (qName.equalsIgnoreCase("contact")) {
                        if (attributes.getValue("type").equalsIgnoreCase("email")) {
                            bEmail = true;
                        }
                        if (attributes.getValue("type").equalsIgnoreCase("phone")) {
                            bPhone = true;
                        }
                        if (attributes.getValue("type").equalsIgnoreCase("jabber")) {
                            bJabber = true;
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (bName) {
                        customer.setName(new String(ch, start, length));
                        bName = false;
                    }
                    if (bSurname) {
                        customer.setSurname(new String(ch, start, length));
                        bSurname = false;
                    }
                    if (bAge) {
                        customer.setAge(Integer.parseInt(new String(ch, start, length)));
                        bAge = false;
                    }
                    if (bEmail) {
                        Contact contact = new Contact();
                        contact.setType(ContactType.EMAIL);
                        contact.setContact(new String(ch, start, length));
                        contacts.add(contact);
                        bEmail = false;
                    }
                    if (bPhone) {
                        Contact contact = new Contact();
                        contact.setType(ContactType.PHONE);
                        contact.setContact(new String(ch, start, length));
                        contacts.add(contact);
                        bPhone = false;
                    }
                    if (bJabber) {
                        Contact contact = new Contact();
                        contact.setType(ContactType.JABBER);
                        contact.setContact(new String(ch, start, length));
                        contacts.add(contact);
                        bJabber = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("person")) {
                        long customerId = customerDao.saveNewCustomer(customer);
                        contacts.forEach(e -> e.setCustomerId(customerId));
                        contacts.forEach(e -> contactDao.saveNewContact(e));
                        contacts = null;
                        customer = null;
                    }
                }
            };

            InputStream in = new BufferedInputStream(new FileInputStream(filePath), 1024 * 1024);
            Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            InputSource source = new InputSource(reader);
            source.setEncoding("UTF-8");
            saxParser.parse(source, defaultHandler);

        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
