package view;

import controllers.LibraryController;
import models.Book;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private LibraryController lc = new LibraryController();

    public void run(){
        System.out.println("+--+------------------------------+");
        System.out.println("|  |        Menu                  |");
        System.out.println("+--+------------------------------+");
        System.out.println("| 1|Add Book                      |");
        System.out.println("| 2|Delete Book                   |");
        System.out.println("| 3|Update Book                   |");
        System.out.println("| 4|Get All Books                 |");
        System.out.println("| 5|Sort Books                    |");
        System.out.println("| 6|Search a Book                 |");
        System.out.println("| 7|Write All Book Data To Desktop|");
        System.out.println("+--+------------------------------+");

        Scanner s = new Scanner(System.in);
        System.out.printf("Enter your choice: ");
        int choice = getNumber();
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                deleteBook();
                break;
            case 3:
                updateBook();
                break;
            case 4:
                getAllBooks();
                break;
            case  5:
                sortBooks();
                break;
            case 6:
                searchABook();
                break;
            case 7:
                writeFile();
                break;
             default:
                 run();
                 break;
        }
        System.out.print("Enter 0 to rerun the program : ");
        int zero = getNumber();
        if(zero == 0)
            run();
    }

    private void  addBook(){
        System.out.println("+--------------------------+");
        System.out.println("|      Adding a Book       |");
        System.out.println("+--------------------------+");
        Scanner s = new Scanner(System.in);
        System.out.printf("Name : ");
        String name = s.nextLine();
        System.out.printf("Author : ");
        String author = s.nextLine();
        System.out.printf("Year : ");
        int year = getNumber();
        System.out.printf("ISBN : ");
        String isbn = s.nextLine();
        int result = lc.addBook(name,author,isbn,year);
        if(result == 1){
            System.out.println("+------------------------------+");
            System.out.println("|     Successfully added!      |");
            System.out.println("+------------------------------+");
        } else if(result == 0){
            System.out.println("+---------------------------+");
            System.out.println("|      Adding failed!       |");
            System.out.println("+---------------------------+");
        } else if(result ==2){
            System.out.println("+-------------------------------------+");
            System.out.println("|      Couldn't find book file!       |");
            System.out.println("+-------------------------------------+");
        } else {
                System.out.println("+-----------------------------------+");
                System.out.println("|      Book is already exist!       |");
                System.out.println("+-----------------------------------+");
        }
    }

    private void deleteBook(){
        System.out.println("+----------------------------+");
        System.out.println("|      Deleting a Book       |");
        System.out.println("+----------------------------+");
        Scanner s = new Scanner(System.in);
        System.out.printf("Name : ");
        String name = s.nextLine();
        System.out.printf("Author : ");
        String author = s.nextLine();
        int result = lc.deleteBook(name,author);
        if(result ==1){
            System.out.println("+--------------------------------+");
            System.out.println("|     Successfully deleted!      |");
            System.out.println("+--------------------------------+");
        } else if(result == 0){
            System.out.println("+----------------------------+");
            System.out.println("|     Deleting  failed!      |");
            System.out.println("+----------------------------+");
        } else {
            System.out.println("+----------------------------------+");
            System.out.println("|      Cannot find the book!       |");
            System.out.println("+----------------------------------+");
        }

    }

    private void updateBook(){
        System.out.println("+----------------------------+");
        System.out.println("|      Updating a book       |");
        System.out.println("+----------------------------+");
        Scanner s = new Scanner(System.in);
        System.out.printf("Updating Book Name : ");
        String oldName = s.nextLine();
        System.out.printf("Updating Book Author : ");
        String oldAuthor = s.nextLine();
        System.out.printf("New Book Name : ");
        String newName = s.nextLine();
        System.out.printf("New Author : ");
        String newAuthor = s.nextLine();
        System.out.printf("New Year : ");
        int newYear = getNumber();
        System.out.printf("New ISBN : ");
        String newISBN = s.nextLine();
        int result = lc.updateBook(oldName,oldAuthor,newName,newAuthor,newISBN,newYear);
        if( result > 0 ) {
            System.out.println("+---------------------------------+");
            System.out.println("|      Successfully updated!      |");
            System.out.println("+---------------------------------+");
        }else if(result == 0) {
            System.out.println("+-----------------------------+");
            System.out.println("|      Updating failed!       |");
            System.out.println("+-----------------------------+");
        } else {
            System.out.println("+----------------------------------+");
            System.out.println("|      Cannot find the book!       |");
            System.out.println("+----------------------------------+");
        }
    }
    private void getAllBooks(){
        printBooks(lc.getAllBooks());
    }

    private void sortBooks(){
        System.out.println("+--+-----------------------------+");
        System.out.println("|  |        Sort Books           |");
        System.out.println("+--+-----------------------------+");
        System.out.println("| 1|Book Name                    |");
        System.out.println("| 2|Author Name                  |");
        System.out.println("| 3|Book Year                    |");
        System.out.println("+--+-----------------------------+");
        System.out.printf("Enter the choice (default is random): ");
        int choice = getNumber();
        switch (choice) {
            case 1:
                printBooks(lc.sortBooks(1));
                break;
            case 2:
                printBooks(lc.sortBooks(2));
                break;
            case 3: printBooks(lc.sortBooks(3));
                    break;
            default:
                getAllBooks();
        }
    }
    private void searchABook(){
        System.out.println("+--+-----------------------------+");
        System.out.println("|  |       Search a Book         |");
        System.out.println("+--+-----------------------------+");
        System.out.println("| 1|By Book Name                 |");
        System.out.println("| 2|By Author Name               |");
        System.out.println("| 3|By Book Year                 |");
        System.out.println("+--+-----------------------------+");
        System.out.printf("Enter the choice : ");
        Scanner s = new Scanner(System.in);
        int choice = getNumber();
        switch (choice) {
            case 1:
                System.out.printf("Enter the name of the book : ");
                String name = s.nextLine();
                printBooks(lc.findByName(name));
                break;
            case 2:
                System.out.printf("Enter the author's name : ");
                String authorName = s.nextLine();
                printBooks(lc.findByAuthor(authorName));
                break;
            case 3:
                System.out.printf("Enter the year : ");
                int year = Integer.parseInt(s.nextLine());
                printBooks(lc.findByYear(year));
                break;
            default:
                searchABook();
                break;
        }
    }

    private void writeFile(){
        if(lc.writeFile()){
            System.out.println("+---------------------------------+");
            System.out.println("|     Added to AllBooks.txt!      |");
            System.out.println("+---------------------------------+");
        }
        else{
            System.out.println("+------------------------------+");
            System.out.println("|    Failed while writing!     |");
            System.out.println("+------------------------------+");
        }
    }


    private static void printBooks(ArrayList<Book> books) {
        String leftAlignFormat = "|%3d | %-72s | %-42s | %-9d | %-42s |%n";

        System.out.format("+----+--------------------------------------------------------------------------+--------------------------------------------+-----------+--------------------------------------------+%n");
        System.out.format("| No | Book Name                                                                | Author                                     | Year      | ISBN                                       |%n");
        System.out.format("+----+--------------------------------------------------------------------------+--------------------------------------------+-----------+--------------------------------------------+%n");
        for (int i = 0; i < books.size(); i++) {
            System.out.format(leftAlignFormat, i+1, books.get(i).getName(),books.get(i).getAuthor(),books.get(i).getYear(),books.get(i).getISBN());
        }
        System.out.format("+----+--------------------------------------------------------------------------+--------------------------------------------+-----------+--------------------------------------------+%n");
    }
    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private int getNumber(){
        Scanner s =  new Scanner(System.in);
        int number = 0;
        while (true) {
            String strYear = s.nextLine();
            if(isNumeric(strYear)) {
                number = Integer.parseInt(strYear);
                break;
            }
            System.out.printf("Please enter an integer number! : ");
        }
        return number;
    }

}
