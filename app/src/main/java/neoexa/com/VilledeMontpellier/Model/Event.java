package neoexa.com.VilledeMontpellier.Model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event {
    public String uid;
    public String title;
    public String body;
    public String address;
    public Date begin;
    public Date end;
    public int peopleCount = 0;
    public Map<String, Boolean> people = new HashMap<>();

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("body", body);
        result.put("address", address);
        result.put("begin", begin);
        result.put("end", end);
        result.put("peopleCount", peopleCount);
        result.put("people", people);

        return result;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
