package BookManager1;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private int year;
    private String genre;
    private List<String> tags;
    private String publisher;

    public Book(String title, String author, int year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.tags = new ArrayList<>();
    }

    // Getters and setters for all attributes

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Add tags to the book
    public void addTag(List<String> newTags) {
        tags.addAll(newTags);
    }

    // Remove a tag from the book
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // Clear all tags from the book
    public void clearTags() {
        tags.clear();
    }

    // Get publisher
    public String getPublisher() {
        return publisher;
    }

    // Set publisher
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    // Other methods as needed
}
