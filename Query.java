import java.util.ArrayList;
import java.util.Scanner;

// interface for queries
interface QueryInterface {
    void execute(ArrayList<Book> books, Scanner scanner);
    String getQueryName();
    int getMenuOption();
}

abstract class BaseQuery implements QueryInterface {
    String queryName;
    int menuOption;
    
    public BaseQuery(String name, int option) {
        this.queryName = name;
        this.menuOption = option;
    }
    
    public String getQueryName() {
        return queryName;
    }
    
    public int getMenuOption() {
        return menuOption;
    }
    
    // helper to compare strings without case
    boolean compareStrings(String s1, String s2) {
        return s1.toLowerCase().equals(s2.toLowerCase());
    }
    
    // helper to compare doubles
    boolean compareDoubles(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.01;
    }
    
    void printSeparator(String title) {
        System.out.println("\n" + title);
        System.out.println("------------------------");
    }
}

// query 1 - count books by author
class CountBooksByAuthorQuery extends BaseQuery {
    public CountBooksByAuthorQuery() {
        super("Total number of books by an author", 1);
    }
    
    public void execute(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        int count = 0;
        for (int i = 0; i < books.size(); i++) {
            if (compareStrings(books.get(i).getAuthor(), author)) {
                count++;
            }
        }
        System.out.println("Total books by " + author + ": " + count);
    }
}

// query 2 - show all authors
class AllAuthorsQuery extends BaseQuery {
    public AllAuthorsQuery() {
        super("All authors in the dataset", 2);
    }
    
    public void execute(ArrayList<Book> books, Scanner scanner) {
        ArrayList<String> authorList = new ArrayList<>();
        
        // get unique authors
        for (int i = 0; i < books.size(); i++) {
            String authorName = books.get(i).getAuthor();
            boolean exists = false;
            for (int j = 0; j < authorList.size(); j++) {
                if (compareStrings(authorList.get(j), authorName)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                authorList.add(authorName);
            }
        }
        
        printSeparator("All authors in the dataset:");
        System.out.println("Total unique authors: " + authorList.size());
        System.out.println("------------------------");
        for (String name : authorList) {
            System.out.println(name);
        }
    }
}

// query 3 - get books by author
class BooksByAuthorQuery extends BaseQuery {
    public BooksByAuthorQuery() {
        super("Names of all books by an author", 3);
    }
    
    public void execute(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        
        printSeparator("Books by " + author + ":");
        boolean foundAny = false;
        for (Book b : books) {
            if (compareStrings(b.getAuthor(), author)) {
                System.out.println(b.getName());
                foundAny = true;
            }
        }
        
        if (!foundAny) {
            System.out.println("No books found by " + author);
        }
    }
}

// query 4 - books by rating
class BooksByRatingQuery extends BaseQuery {
    public BooksByRatingQuery() {
        super("Books with specific user rating", 4);
    }
    
    public void execute(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Enter user rating: ");
        double userRating = scanner.nextDouble();
        
        printSeparator("Books with rating " + userRating + ":");
        boolean foundBooks = false;
        for (int i = 0; i < books.size(); i++) {
            Book currentBook = books.get(i);
            if (compareDoubles(currentBook.getUserRating(), userRating)) {
                System.out.println(currentBook.getName() + " by " + currentBook.getAuthor());
                foundBooks = true;
            }
        }
        
        if (!foundBooks) {
            System.out.println("No books found with rating " + userRating);
        }
    }
}

// query 5 - book prices by author  
class BookPricesByAuthorQuery extends BaseQuery {
    public BookPricesByAuthorQuery() {
        super( "Price of all books by an author", 5);
    }
    
    public void execute(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Enter author name: ");
        String authorName = scanner.nextLine();
        
        printSeparator("Books and prices by " + authorName + ":");
        boolean hasBooks = false;
        for (Book book : books) {
            if (compareStrings(book.getAuthor(), authorName)) {
                System.out.println(book.getName() + " - $" + book.getPrice());
                hasBooks = true;
            }
        }
        
        if (!hasBooks) {
            System.out.println("No books found by " + authorName);
        }
    }
}

// main class to handle all queries
public class Query {
    ArrayList<QueryInterface> queryList;
    
    public Query() {
        queryList = new ArrayList<>();
            queryList.add(new CountBooksByAuthorQuery());
        queryList.add(new AllAuthorsQuery());
        queryList.add(new BooksByAuthorQuery());
        queryList.add(new BooksByRatingQuery());
        queryList.add(new BookPricesByAuthorQuery());
    }
    
    public void addQuery(QueryInterface newQuery) {
        queryList.add(newQuery);
    }
    
    public void displayMenu() {
        System.out.println("=== Book Inventory System ===");
        for (int i = 0; i < queryList.size(); i++) {
            QueryInterface q = queryList.get(i);
            System.out.println(q.getMenuOption() + ". " + q.getQueryName());
        }
        System.out.println((queryList.size() + 1) + ". Exit");
    }
    
    public boolean executeQuery(int userChoice, ArrayList<Book> books, Scanner sc) {
        if (userChoice == queryList.size() + 1) {
            return false; 
        }
        
        for (QueryInterface query : queryList) {
            if (query.getMenuOption() == userChoice) {
                query.execute(books, sc);
                return true;
            }
        }
        
        System.out.println("Wrong choice. Enter 1-" + (queryList.size() + 1) + ".");
        return true;
    }
    
    public int getTotalQueries() {
        return queryList.size();
    }
}