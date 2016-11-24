package pe.kr.crasy.dunningassignments.database;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 *
 * Created by maybe on 16. 11. 24.
 */
@RealmClass
public class Assignments extends RealmObject {
    private String title;
    private String location;
    private Date startLine;
    private Date   deadLine;
    private String metaData;
    private RealmList<People> people;

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

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public RealmList<People> getPeople() {
        return people;
    }

    public void setPeople(RealmList<People> people) {
        this.people = people;
    }
}
