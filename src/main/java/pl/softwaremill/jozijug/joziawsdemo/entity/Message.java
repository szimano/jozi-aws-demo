package pl.softwaremill.jozijug.joziawsdemo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Entity
@Table(name = "Message")
public class Message implements Serializable {

    @Id
    private String uuid;

    @Column
    private String room;

    @Column
    private String content;

    @Column
    @Temporal(TemporalType.TIME)
    private Date date;

    @Column
    @Temporal(TemporalType.TIME)
    private Date saveDate;

    public Message() {
    }

    public Message(UUID uuid, String room, String content, Date date, Date saveDate) {
        this.uuid = uuid.toString();
        this.room = room;
        this.content = content;
        this.date = date;
        this.saveDate = saveDate;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRoom() {
        return room;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid=" + uuid +
                ", room='" + room + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", saveDate=" + saveDate +
                '}';
    }
}
