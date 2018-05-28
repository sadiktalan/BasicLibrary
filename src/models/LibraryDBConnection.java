package models;
import controllers.LibraryController;

import java.sql.* ;
import java.sql.Statement;

public class LibraryDBConnection {

    //Fields
    private Connection conn = null;
    private Statement stmt = null;

    private static LibraryDBConnection lb = new LibraryDBConnection();

    public static LibraryDBConnection getInstance() {
        return lb;
    }

    /**
     * constructor for initializing the connection.
     * creates connection and addes Mysql Drive Connector.
     * log's in wit given pass and username
     */
    private LibraryDBConnection(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/library?autoReconnect=true&useSSL=false","root","root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Statement creates and execute sql string for adding book
     * @param book is given book parameter
     * @return boolean if it is added.
     */
    public boolean addBook(Book book) {
        String sql = "insert into book (book_name,author,book_year,ISBN) values('" + book.getName() + "','"
                + book.getAuthor()+ "','" + book.getYear() + "','" + book.getAuthor() + "');";

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(String oldName,String oldAuthor, String newName, String newAuthor, String newIsbn, int newYear, byte[] data){
        String sql = "update book  SET book_name = '"+newName+"', " +
                "author = '"+newAuthor+"', book_year = "+newYear+", isbn = '"+newIsbn+"'  WHERE book_name like '"+
                oldName+"' and author like '"+oldAuthor+"' limit 1;";
        try {
            stmt = conn.createStatement();
            return  stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * deletes book from library from given name parameter
     * @param name given parameter
     * @return boolen if it is deleted or not
     */
    public boolean deleteBook(String name,String author) {
        String sql ="delete from book where book_name = '"+name+"' and author = '"+author+"';";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * checks if the book exists by given parameters
     * param {bookName & author} is the given parameters
     * @return boolean if it exist or not
     */
    public ResultSet isExist(String bookName,String author){
        String sql = "Select count(id) as number_of_books from library.book where book_name like '"+bookName+"'AND author LIKE '"+author+"';";
        return getResultSet(sql);
    }

    public ResultSet getAllBooks(){
        String sql = "SELECT * FROM library.book;";
        return getResultSet(sql);
    }


    public ResultSet findByName(String name) {
       String sql = "SELECT book_name,author,book_year,isbn,book_file FROM library.book " +
               "where book_name like '%"+name+"%';";
        return getResultSet(sql);
    }

    public ResultSet findByAuthor(String author) {
        String sql = "Select book_name,author,book_year,isbn,book_file from library.book "+
                "where author like '%" + author +"%';";
        return getResultSet(sql);
    }

    public ResultSet findByYear(int year){
       String sql = "Select book_name, author, book_year, isbn,book_file from library.book " +
               "where book_year = "+ year +";";
        return getResultSet(sql);
    }

    public ResultSet numberOfBooks(){
        String sql = "Select count(id) as number_of_books from library.book;";
        return getResultSet(sql);

    }


    private ResultSet getResultSet(String sql) {
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
