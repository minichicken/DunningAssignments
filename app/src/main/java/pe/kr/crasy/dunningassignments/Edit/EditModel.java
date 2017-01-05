package pe.kr.crasy.dunningassignments.Edit;

import java.util.Date;

/**
 *
 * Created by maybe on 16. 12. 29.
 */

public class EditModel {
    private String title;
    private String location;
    private String metaData;
    private Date   startLine;
    private Date   deadLine;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Date getStartLine() {
        return startLine;
    }

    public void setStartLine(Date startLine) {
        this.startLine = startLine;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
}
