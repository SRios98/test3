package data;

import java.util.ArrayList;
import java.sql.SQLException;

import model.Book;


public class BookDataManager {
    private static BookDataManager instance;
    private ArrayList<Book> bookList = new ArrayList<>();
    private static OracleDBConnection dbManager = new OracleDBConnection();

    public static BookDataManager getInstance() {
        if (instance == null) {
            instance = new BookDataManager();
        }
        return instance;
    }

    public ArrayList<Book> getBooks() {
        if (bookList.isEmpty()) {
        	getBooksFromDatabase();  // Load from database if list is empty
        }
        return bookList;
    }

    private void getBooksFromDatabase() {
        try {
            ArrayList<Book> booksFromDb = dbManager.fetchBooks();
            bookList.clear();
            bookList.addAll(booksFromDb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addBook(Book book) {
        bookList.add(book);
    }
}
