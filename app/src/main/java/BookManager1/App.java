package BookManager1;

public class App {
  public static void main(String[] args) {
    // Create an instance of BookManagerFrame1
    BookManagerFrame1 bookManagerFrame = new BookManagerFrame1();
    
    // Set the frame to be visible
    bookManagerFrame.setVisible(true);
  }

  // method to test a greeting functionality
  public String getGreeting() {
    // Implement logic to return a greeting message
    return "Hello from Book Catalog!";
  }
}
