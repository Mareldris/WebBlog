package Web.WebBlog.models;

import javax.persistence.*;
//<img th:src="el.filename">
@Entity
public class Post {
    @Id
    @GeneratedValue(generator = "SEQUENCE_POST", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQUENCE_POST", sequenceName = "SO.SEQUENCE_POST",allocationSize=1)
    private long id;

    private String title, anons, full_text ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private int views;

    private String filename;

    public Post(String title, String anons, String full_text, User user) {
        this.author = user;
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int viewsCount(int views){
        return views +1;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorName(){
        return  author != null? author.getUsername() : "<none>";
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

