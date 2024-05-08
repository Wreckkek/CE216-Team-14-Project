/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author efe
 */

package BookManager1;

import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class BookManagerFrame1 extends javax.swing.JFrame {
    private List<Book> books; // List to store books
    /**
     * Creates new form BookManagerFrame1
     */
    /*might reuse these variable later if needed trying different approaches for now */
    /*private boolean fileImported = false; // Declare fileImported variable
    // variable to keep track of the currently imported file path
    private String importedFilePath = null;*/
    public BookManagerFrame1() {
        initComponents();
        books = new ArrayList<>(); // We initialize the list of books
    }
    private boolean fileImported = false; // fileImported variable
    // variable to track the currently imported file path
    private String importedFilePath = null;
    private String getBasePath() {
    // This returns the user's home directory path
    return System.getProperty("user.home");
}

  // Method to save books to JSON file
    /*private void saveBooksToJson(String importedFilePath1) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            String filePath;
            // to check if any file has been imported
            if (importedFilePath != null) {
                filePath = importedFilePath;
            } else {
                // If no file has been imported, we use the default "books.json" file in the project folder.
                filePath = "books.json";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(books, writer);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving books to JSON file!");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error accessing the file system!");
        }
    }*/
    private void saveBooksToJson(String filePath) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (FileWriter writer = new FileWriter(filePath)) {
        gson.toJson(books, writer);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving books to JSON file!");
    }
    }
    private void loadBooksFromJson(String filePath) {
        Gson gson = new Gson();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "File does not exis");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            java.lang.reflect.Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
            books = gson.fromJson(new InputStreamReader(fileInputStream), listType);
            fileImported = true; // Update the flag indicating that a file has been imported
            JOptionPane.showMessageDialog(null, "Books loaded successfully, from " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading the books from JSON file!");
        }
    }





    /* might go back to these methods later, attemting another approach to fix consecutive imports.
/*private void addBook() {
    try {
        String title = jTextField1.getText();
        String author = jTextField2.getText();
        int year = Integer.parseInt(jTextField3.getText());
        String genre = jTextField4.getText();
        String publisher = jTextField8.getText();
        String[] tags = jTextField5.getText().split(",");
        Book book = new Book(title, author, year, genre);
        book.setPublisher(publisher);
        book.addTag(Arrays.asList(tags)); // Changed from addTags to addTag
        books.add(book);
        // Save changes back to JSON file
        saveBooksToJson();
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a valid integer for the year.");
    }
}*/
/*private void addBook() {
    if (!fileImported) {
        createNewJsonFile(); // Function to create a new JSON file
    }
    
    // Proceed with adding the book as usual
    try {
        String title = jTextField1.getText();
        String author = jTextField2.getText();
        int year = Integer.parseInt(jTextField3.getText());
        String genre = jTextField4.getText();
        String publisher = jTextField8.getText();
        String[] tags = jTextField5.getText().split(",");
        Book book = new Book(title, author, year, genre);
        book.setPublisher(publisher);
        book.addTag(Arrays.asList(tags));
        books.add(book);
        // Save changes back to JSON file
        saveBooksToJson();
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a valid integer for the year.");
    }
}*/
    private void addBook() {
        if (!fileImported) {
            createNewJsonFile(); // Function to create a new JSON file
        }
        
        // Checking if some input fields are empty and alerting the user.
        if (jTextField1.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty() ||
            jTextField4.getText().isEmpty() || jTextField8.getText().isEmpty() || jTextField5.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Missing input, please check all text fields.");
        return;
        }
        // Proceed with adding the book as usual
        try {
            String title = jTextField1.getText();
            String author = jTextField2.getText();
            int year = Integer.parseInt(jTextField3.getText());
            String genre = jTextField4.getText();
            String publisher = jTextField8.getText();
            String[] tags = jTextField5.getText().split(",");
            Book book = new Book(title, author, year, genre);
            book.setPublisher(publisher);
            book.addTag(Arrays.asList(tags));
            books.add(book);
            // Save changes back to the imported JSON file
            saveBooksToJson(importedFilePath);
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid year format, please enter an integer for the year.");
        }
    }

    private void editBook() {
    int selectedIndex = jList2.getSelectedIndex();
    if (selectedIndex != -1) {
        Book selectedBook = books.get(selectedIndex);
        selectedBook.setTitle(jTextField1.getText());
        selectedBook.setAuthor(jTextField2.getText());
        selectedBook.setYear(Integer.parseInt(jTextField3.getText()));
        selectedBook.setGenre(jTextField4.getText());
        selectedBook.setPublisher(jTextField8.getText());
        selectedBook.setTags(Arrays.asList(jTextField5.getText().split(",")));
        try {
            saveBooksToJson(importedFilePath);
            JOptionPane.showMessageDialog(null, "Book edited successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to edit book: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a book to edit.");
    }
}

// Add a method to create a new JSON file
    private void createNewJsonFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filePath = getBasePath() + File.separator + "new_books.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(new ArrayList<>(), writer);
            fileImported = true;
            importedFilePath = filePath;
            jTextField9.setText(filePath); // Display the path in the UI
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating new JSON file.");
        }
    }


/*private void editBook() {
    int selectedIndex = jList2.getSelectedIndex();
    if (selectedIndex != -1) {
        Book selectedBook = books.get(selectedIndex);
        selectedBook.setTitle(jTextField1.getText());
        selectedBook.setAuthor(jTextField2.getText());
        selectedBook.setYear(Integer.parseInt(jTextField3.getText()));
        selectedBook.setGenre(jTextField4.getText());
        selectedBook.clearTags(); // Clear existing tags before adding new ones
        selectedBook.addTag(Arrays.asList(jTextField5.getText().split(","))); 
        selectedBook.setPublisher(jTextField8.getText());
        // Save changes back to JSON file
        saveBooksToJson();
        JOptionPane.showMessageDialog(null, "Book edited successfully.");
    } else {
        JOptionPane.showMessageDialog(null, "Please select a book to edit.");
    }
}*/

    // Method to delete a book
    private void deleteBook() {
    int selectedIndex = jList2.getSelectedIndex();
    if (selectedIndex != -1) {
        books.remove(selectedIndex);
        JOptionPane.showMessageDialog(null, "Book deleted successfully.");
    } else {
        JOptionPane.showMessageDialog(null, "Please select a book to delete.");
    }
    }
    // Method to search for books
    private void searchBooks() {
        // Checking if any books have been imported or added so the operation can be performed!
            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No file imported or created.\nImport a file to work on, or add books to the autogenerated file.", "No Books Found", JOptionPane.INFORMATION_MESSAGE);
                return; // Exit the method
            }
        
        // search by criteria logic (for now)
        String searchText = jTextField6.getText().toLowerCase();
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchText)
                    || book.getAuthor().toLowerCase().contains(searchText)
                    || String.valueOf(book.getYear()).contains(searchText)
                    || book.getGenre().toLowerCase().contains(searchText)
                    || book.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(searchText))) {
                searchResults.add(book);
            }
        }
        // Display search results
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Book book : searchResults) {
            model.addElement(book.getTitle());
        }
        jList2.setModel(model);
    }
   // Update the JTextField when a file is imported or created
private void importBooks() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Import Books");
    int userSelection = fileChooser.showOpenDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            // Pass the reader object to fromJson method
            List<Book> importedBooks = gson.fromJson(reader, new TypeToken<List<Book>>(){}.getType());
            books.addAll(importedBooks);
            reader.close(); // Close the BufferedReader after reading
            importedFilePath = selectedFile.getAbsolutePath(); // Update the imported file path
            jTextField9.setText(importedFilePath); // Update the file path text field
            JOptionPane.showMessageDialog(null, "Books imported successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error importing books: " + e.getMessage());
        }
    }
}
   private void exportBooks() {
    // export to a JSON file logic (simple version)
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Export Books");
    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(selectedFile);
            gson.toJson(books, writer);
            writer.close();
            JOptionPane.showMessageDialog(null, "Books exported successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting books: " + e.getMessage());
        }
    }
}

    // Method to list books based on tags
    private void listBooksByTags() {
        // logic to list by tags (this one is depreciated -> currently using dropdown menu + user input to search by tag)
        // but the GUI elements related to this still exist, might change later before delivery.
        String selectedTag = jTextField6.getText().trim();
        List<Book> taggedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTags().contains(selectedTag)) {
                taggedBooks.add(book);
            }
        }
        // Display tagged books
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Book book : taggedBooks) {
            model.addElement(book.getTitle());
        }
        jList2.setModel(model);
    }
    private void displayAllBooks() {
    DefaultListModel<String> model = new DefaultListModel<>();
    for (Book book : books) {
        model.addElement(book.getTitle());
    }
    jList2.setModel(model);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 227, 227));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loupe.png"))); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/queue.png"))); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trash.png"))); // NOI18N
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Book Catalog 0.1");

        jLabel2.setText("Name:");

        jLabel3.setText("Author:");

        jLabel4.setText("Year:");

        jLabel5.setText("Genre: ");

        jLabel6.setText("Tag:");

        jLabel7.setText("Search by: ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Author", "Year", "Genre", "Tag" }));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setText("enter input");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton6.setText("Help");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jList2.setBackground(new java.awt.Color(232, 230, 230));
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList2);

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel9.setText("Publisher:");

        jButton7.setBackground(new java.awt.Color(225, 244, 221));
        jButton7.setText("Import");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(251, 251, 222));
        jButton8.setText("Export");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTextField9.setText("current file*");
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jLabel10.setText("Current File:");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/book-open-in-the-middle_1.png"))); // NOI18N
        jButton9.setText("Display all books / Update list");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))
                                .addGap(194, 194, 194))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField5)
                                        .addComponent(jTextField4)
                                        .addComponent(jTextField3)
                                        .addComponent(jTextField2)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton9)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(93, 93, 93)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8)
                            .addComponent(jButton7))
                        .addGap(171, 171, 171)))
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton6)
                .addGap(341, 341, 341)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jLabel1))
                        .addGap(178, 178, 178)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jButton3)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(jButton2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jButton4))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        searchBooks();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        addBook();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
        exportBooks();
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         // Create and display the Help window
                HelpWindow helpWindow = new HelpWindow();
                helpWindow.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        importBooks();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         editBook();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        deleteBook();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
       if (!evt.getValueIsAdjusting()) {
        String selectedTitle = (String) jList2.getSelectedValue();
        if (selectedTitle != null) {
            // Find the book with the matching title in the books list
            for (Book book : books) {
                if (book.getTitle().equals(selectedTitle)) {
                    // Display the information of the matched book
                    jTextField1.setText(book.getTitle());
                    jTextField2.setText(book.getAuthor());
                    jTextField3.setText(String.valueOf(book.getYear()));
                    jTextField4.setText(book.getGenre());
                    jTextField5.setText(String.join(",", book.getTags()));
                    jTextField8.setText(book.getPublisher());
                    break; // Exit loop when we find the book 
                }
            }
        }
    }
    }//GEN-LAST:event_jList2ValueChanged

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        displayAllBooks();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        exportBooks();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

     /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookManagerFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookManagerFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookManagerFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookManagerFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookManagerFrame1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    
}
