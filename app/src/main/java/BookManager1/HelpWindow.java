/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookManager1;

import javax.swing.*;

public class HelpWindow extends JFrame {
    public HelpWindow() {
        // Set window title
        setTitle("Help");

        // Create a label with help text
        JLabel helpLabel = new JLabel("<html><div style='text-align: center;'>The instructions to use the application are:<br><br>"
                                    + "1. Add a new book: Fill in the book details in the provided fields and click the 'Add' button.<br>"
                                    + "2. Edit a book: Select a book from the list, update its details in the fields, and click the 'Edit' button.<br>"
                                    + "3. Delete a book: Select a book from the list and click the 'Delete' button.<br>"
                                    + "4. Search for books: Select by which variable the books will be filtered using the dropdown menu, enter a search term in the search field and click the 'Search' button.<br>"
                                    + "5. Import books: Click the 'Import' button to import books from a JSON file.<br>"
                                    + "6. Export books: Click the 'Export' button to export books to a JSON file.<br>"
                                    + "7. If no JSON file is imported, operations will be performed on a auto generated 'new_books.json' file.<br>"
                                    + "8. Use the 'Display all books / update list' button to update to visual list to reflect the results of the operations to the list visually.<br>"
                                    + "</div></html>");

        // Add the label to the content pane
        getContentPane().add(helpLabel);

        // Set window size
        setSize(500, 300);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Set default close operation
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
