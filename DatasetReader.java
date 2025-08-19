import java.io.*;
import java.util.ArrayList;

public class DatasetReader {
    
    // method to read CSV file
    public ArrayList<Book> readBooks(String fileName) {
        ArrayList<Book> books = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            
            // skip first line (header)
            br.readLine();
            
            // read all lines
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    Book book = makeBookFromLine(line);
                    if (book != null) {
                        books.add(book);
                    }
                }
            }
            
            br.close();
            System.out.println("Loaded " + books.size() + " books from file");
            
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
        
        return books;
    }
    
    // make book from one line
    private Book makeBookFromLine(String line) {
        try {
            String[] data = splitLine(line);
            
            if (data.length == 7) {
                String bookName = data[0].replace("\"", "");
                String authorName = data[1].replace("\"", "");
                double rating = Double.parseDouble(data[2]);
                int numReviews = Integer.parseInt(data[3]);
                int bookPrice = Integer.parseInt(data[4]);
                int bookYear = Integer.parseInt(data[5]);
                String bookGenre = data[6].replace("\"", "");
                
                return new Book(bookName, authorName, rating, numReviews, bookPrice, bookYear, bookGenre);
            }
            
        } catch (Exception e) {
           
        }
        return null;
    }
    
    // split CSV line
    private String[] splitLine(String line) {
        ArrayList<String> parts = new ArrayList<>();
        String current = "";
        boolean inQuote = false;
        
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            
            if (ch == '"') {
                inQuote = !inQuote;
            } else if (ch == ',' && !inQuote) {
                parts.add(current.trim());
                current = "";
            } else {
                current = current + ch;
            }
        }
        parts.add(current.trim());
        
        String[] result = new String[parts.size()];
        for (int i = 0; i < parts.size(); i++) {
            result[i] = parts.get(i);
        }
        return result;
    }
}