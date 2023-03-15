package skypro.com.an_sch_bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateTime;
    private Long chatId;
    private String textMessage;
    private String text;

    public NotificationTask(Long id, LocalDateTime dateTime, Long chatId, String textMessage, String text) {
        this.id = id;
        this.dateTime = dateTime;
        this.chatId = chatId;
        this.textMessage = textMessage;
        this.text = text;
    }

    public NotificationTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id.equals(that.id) && dateTime.equals(that.dateTime) && chatId.equals(that.chatId) && textMessage.equals(that.textMessage) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, chatId, textMessage, text);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", chatId=" + chatId +
                ", textMessage='" + textMessage + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
