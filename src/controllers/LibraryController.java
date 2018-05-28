package controllers;

import models.Book;
import models.LibraryDBConnection;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LibraryController {

    // Instance of library as singleton object
    private final LibraryDBConnection library = LibraryDBConnection.getInstance();

    /**
     * adds book to the library if it is NOT exist
     * {name,author,isbn,year} fields of the book
     * @return boolean if added
     */
    public int addBook(String name,String author,String isbn,int year){
        String str = "";
        if(!isBookExist(name,author)){
            String filePath  = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Desktop/"+name+".pdf";
            byte[] data = new byte[0];
                Path path = Paths.get(filePath);
            try {
                data = Files.readAllBytes(path);
                str = new String(data);
            } catch (IOException e) {
            }finally {
                if(data.length != 0) {
                    Book book = new Book(name, author, isbn, year);
                    if (library.addBook(book))
                        return 1;
                    return 0;
                } else
                    return 2;
            }
        }
        else return -1;

    }

    /**
     * deletes book from the library if it is exist
     * @param bookName is the given parameter for finding book
     * @return boolean if it was deleted.
     */
    public int deleteBook(String bookName,String author) {
        if(!isBookExist(bookName,author))
            return -1;
        else {
            if(library.deleteBook(bookName,author))
                return 1;
            else return 0;
        }
    }

    /**
     * updates book if it is exist
     * @return boolean isUpdated or Not
     */
    public  int updateBook(String oldName, String oldAuthor,String name,String author, String isbn,int year) {
        if(isBookExist(oldName,oldAuthor)) {
            String filePath  = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Desktop/"+name+".pdf";
            byte[] data = new byte[0];
            Path path = Paths.get(filePath);
            try {
                data = Files.readAllBytes(path);
            } catch (IOException e) {
            }finally {
                if(data.length != 0){
                    if(library.updateBook(oldName,oldAuthor,name,author,isbn,year,data))
                        return 1;
                    return 0;
                } else
                    return 2;
            }
        }
        else return -1;
    }

    /**
     * Takes all resultsets from db and creates booklist by using them
     * @return arraylist of all books
     */
    public  ArrayList<Book> getAllBooks(){
        ArrayList<Book> books = new ArrayList<>();
        ResultSet rs = library.getAllBooks();
        return getBookFromResultSet(books, rs);

    }

    /**
     * 1- name 2-author 3- year
     */
    public  ArrayList<Book> sortBooks(int type){
        ArrayList<Book> books = getAllBooks();
        switch (type) {
            case  1:
                Collections.sort(books,Book.NameComparator);
                break;
            case 2:
                Collections.sort(books,Book.AuthorComparator);
                break;
            case 3:
                Collections.sort(books,Book.YearComparator);
            default:
                break;
        }
        return books;
    }

    public ArrayList<Book>findByName(String bookName){
        ArrayList<Book> books = new ArrayList<>();
        ResultSet rs = library.findByName(bookName);
        return getBookFromResultSet(books, rs);
    }

    public ArrayList<Book>findByAuthor(String authorName){
        ArrayList<Book> books = new ArrayList<>();
        ResultSet rs = library.findByAuthor(authorName);
        return getBookFromResultSet(books, rs);
    }

    public ArrayList<Book>findByYear(int year){
        ArrayList<Book> books = new ArrayList<>();
        ResultSet rs = library.findByYear(year);
        return getBookFromResultSet(books,rs);

    }

    public int numberOfBooks(){
        ResultSet rs = library.numberOfBooks();
        return getNumberOfBooks(rs);
    }

    private ArrayList<Book> getBookFromResultSet(ArrayList<Book> books, ResultSet rs) {
        if(rs != null){
            try {
                while (rs.next()) {
                    String name  = rs.getString("book_name");
                    String author = rs.getString("author");
                    int year = rs.getInt("book_year");
                    String  isbn = rs.getString("isbn");
                    books.add(new Book(name,author,isbn,year));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return books;
            }
            return  books;
        } else {
            return null;
        }
    }

    /**
     * checks if the item exist
     * @return boolean if it exists or not
     */
    private boolean isBookExist(String name,String author) {
        ResultSet rs =  library.isExist(name,author);
        int numberOfBooks = 0;
        numberOfBooks = getNumberOfBooks(rs);
        return numberOfBooks > 0;
    }

    private int getNumberOfBooks(ResultSet rs) {
        int numberOfBooks = -1;
        if(rs != null) {
            try {
                while (rs.next()){
                    numberOfBooks = rs.getInt("number_of_books");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfBooks;
    }

    public boolean writeFile(){
        ArrayList<Book> allBooks = getAllBooks();
        String bookString = "";
        for(int i = 0 ; i< allBooks.size();i++){
            bookString+= allBooks.get(i).toString();
        }
        byte[] strToBytes = bookString.getBytes();
        String filePath  = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Desktop/AllBooks.txt";
        Path path = Paths.get(filePath);
        try {
            Files.write(path, strToBytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
