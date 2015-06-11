package projekt.htlgrieskirchen.at.notodoslist;

import java.util.Date;

/**
 * Created by Simon on 11.06.2015.
 */
public class Todos {
    String title,description;
    Prority prority;
    Date deadline;

    public Todos(String title, String description, Prority prority, Date deadline) {
        this.title = title;
        this.description = description;
        this.prority = prority;
        this.deadline = deadline;
    }

    public Todos() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrority(Prority prority) {
        this.prority = prority;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Prority getPrority() {
        return prority;
    }

    public Date getDeadline() {
        return deadline;
    }
}
