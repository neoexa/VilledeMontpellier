package neoexa.com.VilledeMontpellier.Model;

import java.util.Date;

public class Event {
    public String uid;
    public String title;
    public String body;
    public String address;
    public Date begin;
    public Date end;

    public Event() {
    }

    public Event(String uid, String title, String body, String address, Date begin, Date end) {
        this.uid = uid;
        this.title = title;
        this.body = body;
        this.address = address;
        this.begin = begin;
        this.end = end;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
