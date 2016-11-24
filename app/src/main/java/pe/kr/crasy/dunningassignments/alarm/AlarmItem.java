package pe.kr.crasy.dunningassignments.alarm;

import java.util.Date;

/**
 *
 * Created by maybe on 16. 11. 24.
 */

class AlarmItem {
    private String title;
    private Date date;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }
}
