package skypro.com.an_sch_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import skypro.com.an_sch_bot.entity.NotificationTask;
import skypro.com.an_sch_bot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnSchBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(AnSchBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private final NotificationRepository notificationRepository;

    public AnSchBotUpdatesListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update ->
            {
                logger.info("Processing update: {}", update);
                if (update.message().photo() != null || update.message().sticker() != null
                        || update.message().video() != null || update.message().audio() != null) {
                    SendMessage errorMessage = new SendMessage(update.message().chat().id(),
                            "Извините, но я умею обрабатывать только текст.");
                    telegramBot.execute(errorMessage);
                    return;
                }
                if (matchMessage("/start", update).matches()) {
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Привет!"));
                } else {
                    var matcher = matchMessage("([\\d\\\\.:\\s]{16})(\\s)([А-яA-z\\s\\d]+)", update);
                    if (matcher.matches()) {
                        saveTask(matcher.group(1), matcher.group(3), update.message().chat().id());
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Я сохранил задачу"));
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Неверный формат"));
                    }

                }
            });
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

            private void saveTask (String dateTime, String description,long chatId){
                var task = new NotificationTask();
                task.setDateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                task.setTextMessage(description);
                task.setChatId(chatId);
                notificationRepository.save(task);
            }
            private Matcher matchMessage (String regex, Update update){
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(update.message().text());
            }


            @Scheduled(cron = "0 0/1 * * * *")
            public void run () {
                logger.info("Метод run ");
                try {
                    LocalDateTime taskTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                    NotificationTask dateTimes = notificationRepository.findByDateTime(taskTime);

                    if (dateTimes != null) {
                        SendMessage message = new SendMessage(dateTimes.getChatId(), "НАПОМИНАНИЕ");
                        telegramBot.execute(message);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }

