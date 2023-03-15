package skypro.com.an_sch_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.com.an_sch_bot.entity.NotificationTask;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {
    NotificationTask findByDateTime(LocalDateTime dateTime);

    NotificationTask getAllByChatId(Long chatId);
}
