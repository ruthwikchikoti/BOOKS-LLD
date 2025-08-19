import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    
    public static void main(String[] args) {
        DatasetReader reader = new DatasetReader();
        ArrayList<Book> bookList = reader.readBooks("data.csv");
        
        if (bookList.size() == 0) {
            System.out.println("No books found");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        Query querySystem = new Query();
        
        while (true) {
            querySystem.displayMenu();
            System.out.print("\nEnter your choice (1-" + (querySystem.getTotalQueries() + 1) + "): ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            if (!querySystem.executeQuery(choice, bookList, sc)) {
                System.out.println("Thank you!");
                sc.close();
                break;
            }
        }
    }
}