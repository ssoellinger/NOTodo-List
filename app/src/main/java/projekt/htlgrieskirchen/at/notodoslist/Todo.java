package projekt.htlgrieskirchen.at.notodoslist;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Simon on 11.06.2015.
 */
public class Todo implements Serializable {
    String title,description;
    Priority priority;
    Date deadline;
    boolean done=false;

    public Todo(String title, String description, Priority priority, Date deadline) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;

    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Todo() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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

    public Priority getPriority() {
        return priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return title;
    }
}
