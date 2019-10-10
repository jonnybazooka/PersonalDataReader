package datasoucre;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSource {
    private static final String PROPERTIES_FILE_PATH = "connection.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final ConnectionSource INSTANCE = new ConnectionSource();
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private ConnectionSource(){
        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionSource getInstance() {
        return INSTANCE;
    }

    public static void initialize() throws IOException {
        FileReader fileReader = new FileReader(PROPERTIES_FILE_PATH);
        PROPERTIES.load(fileReader);
        URL = PROPERTIES.getProperty("url");
        USER = PROPERTIES.getProperty("user");
        PASSWORD = PROPERTIES.getProperty("password");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
