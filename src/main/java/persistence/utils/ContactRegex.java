package persistence.utils;

public class ContactRegex {
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static final String PHONE_REGEX =
            "(\\+\\d{2}\\s?)?(\\d{3}\\s?\\d{3}\\s?\\d{3})|(\\d{3}\\s\\d{2}\\s\\d{2}\\s\\d{2})|(\\d{3}-\\d{3}-\\d{3})|(\\d{3}-\\d{2}-\\d{2}-\\d{2})";

    public static final String JABBER_REGEX =
            "^jbr(:)?.*$";

    private ContactRegex(){}
}
