package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class ConnectionFactory {
    
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "1234");
            properties.setProperty("useSSL", "false");
            properties.setProperty("useTimezone", "true");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("allowPublicKeyRetrieval","true");
            
            String con = "jdbc:mysql://localhost:3306/consultmed";
            return DriverManager.getConnection(con, properties);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection Factory\n" + e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
}
