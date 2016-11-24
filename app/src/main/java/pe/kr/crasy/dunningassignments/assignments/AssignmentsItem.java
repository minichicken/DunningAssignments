package pe.kr.crasy.dunningassignments.assignments;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

import java.util.Date;

public class AssignmentsItem {
    private String title;
    private Date deadline;
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    Date getDeadline() {
        return deadline;
    }

    void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }
}