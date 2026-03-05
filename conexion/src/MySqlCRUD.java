import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCRUD {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3310/testdb?allowPublicKeyRetrieval=true&useSSL=false"; 
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "MiPasswordSegura123";

public void insertCustomer(Customer customers)
{
if (customerExists(customers.getId()))
{
System.out.println(" usuario con ID " + customers.getId() + " ya existe. salta insercion");
return;
}

try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD))
{
// Sentencia SQL corregida para 6 valores
String sql = "INSERT INTO customers (id, first_name, last_name, age, email) VALUES (?, ?, ?, ?, ?)";
try (PreparedStatement stmt = connection.prepareStatement(sql))
{
stmt.setInt(1, customers.getId());
stmt.setString(2, customers.getFirstName());
stmt.setString(3, customers.getLastName());
stmt.setInt(4, customers.getAge());
stmt.setString(5, customers.getEmail());
//stmt.setInt(6, customers.getIdSuscripcion()); // Usa el nuevo getter
stmt.executeUpdate();
System.out.println(" usuario " + customers.getFirstName() + " insertado exitosamente.");
}
}
catch (SQLException e)
{
e.printStackTrace();
}
} 

private boolean customerExists(int id)
{
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD))
    {
        String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery())
            {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    return false;
}

public void readCustomer()
{
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD))
    {
        String sql = "SELECT cus.* FROM customers cus;";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery())
    {
        System.out.println("\n Usuarios en MySQL:");
        while (rs.next())
        {
            System.out.println(new Customer(rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("age"),
            rs.getString("email")).toStringPersonalizado());
            
        }
    }
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }
}

public void updateCustomer(int id, String newFirstName)
{
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD))
    {
        String sql = "UPDATE customers SET first_name = ? WHERE id = ?"; 
        try(PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, newFirstName);
            stmt.setInt(2, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) 
            {
                System.out.println("Customer id : " +id+ "update to: "+newFirstName);
            
        }
        else
        {
            System.out.println("Customer id not found");
        }
    }
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

}

public void deleteCustomer(int id)
{
    try ( Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD))
    {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.print("Customer id: " +id+ "delated"); 
            }
           else {
            System.out.println("Customer id not found");
           }
        }
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
}


}


