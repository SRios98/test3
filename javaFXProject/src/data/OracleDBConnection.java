package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Book;
import model.User;

public class OracleDBConnection {
	private final String username="ajerez";
	private final String password="4j3r3z";
	private final String host = "192.168.254.215";
	private final String port = "1521";
	private final String service = "orcl";


    // Method to get row count
    public void getRowCount(String tableName) {
        String sql = "SELECT COUNT(*) AS row_count FROM " + tableName;

        try  (Connection connection = DriverManager.getConnection(getConnectionString(), username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                int rowCount = resultSet.getInt("row_count");
                System.out.println("Table '" + tableName + "' has " + rowCount + " rows.");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching row count: " + e.getMessage());
        }
    }

    // Method to get column details
    public void getColumnDetails(String tableName) {
        String sql = "SELECT column_name, data_type, data_length "
                   + "FROM user_tab_columns "
                   + "WHERE table_name = '" + tableName + "'";

        try  (Connection connection = DriverManager.getConnection(getConnectionString(), username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Column details for table '" + tableName + "':");
            while (resultSet.next()) {
                String columnName = resultSet.getString("column_name");
                String dataType = resultSet.getString("data_type");
                int dataLength = resultSet.getInt("data_length");
                System.out.println("Column: " + columnName + ", Type: " + dataType + ", Length: " + dataLength);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching column details: " + e.getMessage());
        }
    }
    
    // Method to fetch books from the database
    public ArrayList<Book> fetchBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT Title, Author, ISBN, Year, Available FROM Book";
        
        try (Connection conn = DriverManager.getConnection(getConnectionString(), username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                long isbn = rs.getLong("ISBN");
                int year = rs.getInt("Year");
                boolean available = rs.getBoolean("Available");
                
                Book book = new Book(title, author, isbn, year, available);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching books from the database.");
        }

        return books;
    }
    
    
    
    // Method to add books from the database
    public void addBooks() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        String query = "INSERT INTO Book (Title, Author, ISBN, Year, Available");
        
        try (Connection conn = DriverManager.getConnection(getConnectionString(), username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                long isbn = rs.getLong("ISBN");
                int year = rs.getInt("Year");
                boolean available = rs.getBoolean("Available");
                
                Book book = new Book(title, author, isbn, year, available);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching books from the database.");
        }


    }
    
    // Method to fetch users from the database
    public ArrayList<User> fetchUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT nombreUsuario, contraseña FROM UserAdmin";
   
        

        
        try (Connection conn = DriverManager.getConnection(getConnectionString(), username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String userName = rs.getString("nombreUsuario");
                String password = rs.getString("contraseña");
                
                User user = new User(userName, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching users from the database.");
        }

        return users;
    }
    
	public String getConnectionString() {
		return String.format("jdbc:oracle:thin:@%s:%s:%s", this.host, this.port, this.service);
	}
	
	
}


