package pe.kr.crasy.dunningassignments.assignments;

import java.util.Date;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

public class AssignmentsModel {
    private String title;
    private Date deadline;
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}