package models;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

public  class Book {
    private String name;
    private String author;
    private String ISBN;
    private int year;

    //Initializing
    public Book(String name, String author, String ISBN, int year) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }


    @Override
    public String toString() {
        return name +"\t\t"+author+"\t\t"+year+"\t\t"+ISBN+"\n";
    }

    public static Comparator<Book> NameComparator = new Comparator<Book>() {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.getName().compareTo(b2.getName());
        }
    };

    public static Comparator<Book> YearComparator = new Comparator<Book>() {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.getYear() - b2.getYear();
        }
    };

    public static Comparator<Book> AuthorComparator = new Comparator<Book>() {
        @Override
        public int compare(Book b1, Book b2) {
            return b1.getAuthor().compareTo(b2.getAuthor());
        }
    };

}