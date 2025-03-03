package bot.telegram.menfess.bot;

import bot.telegram.menfess.config.RulesCofiguration;
import bot.telegram.menfess.entity.Users;
import bot.telegram.menfess.entity.UsersLevel;
import bot.telegram.menfess.repository.UsersRepository;
import bot.telegram.menfess.service.UserService;

import bot.telegram.menfess.utils.command.StartCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramBotMain extends TelegramLongPollingBot {


    @Autowired
    private UserService userService;

    private final String botName;



    public TelegramBotMain(String botToken, String botUsername) {
        super(botToken);
        this.botName = botUsername;
    }

    @Override
    @Bean
    public void onUpdateReceived(Update update) {

        if (update.getMessage() != null) {
            var isRegister = userService.findUsers(update.getMessage().getChatId());
            log.warn("From users {}", update.getMessage().getChatId());
            if (update.getMessage().getText().equals("/start")) {
                if (isRegister != null) {
                    try {
                        execute(new StartCommand().registered(update));
                    } catch (TelegramApiException e){
                        log.warn(e.getMessage());
                    }

                } else {
                    try {
                        execute(new StartCommand().notRegistered(update));
                    } catch (TelegramApiException e) {
                        log.warn(e.getMessage());
                    }
                }

            }


        } else {
            log.info(update.getCallbackQuery().getData());
            if (update.getCallbackQuery().getData() != null) {
                if (update.getCallbackQuery().getData().equals("mendaftar")) {
                    Users registerUser = Users.builder()
                            .id(update.getCallbackQuery().getMessage().getChatId())
                            .limitService(new RulesCofiguration().getLimitFreeUsers())
                            .level(UsersLevel.FREE)
                            .balance(0)
                            .build();
                    registerUser = userService.registerUser(registerUser);
                    if (registerUser != null) {
                        try {
                            execute(new StartCommand().successRegistered(update));
                        } catch (TelegramApiException e) {
                            log.warn(e.getMessage());
                        }
                    } else {
                        log.warn("Error Registering User");
                        try {
                            execute(new StartCommand().errorRegisteredUsers(update));
                        } catch (TelegramApiException e) {
                            log.warn(e.getMessage());
                        }
                    }

                } else if (update.getCallbackQuery().getData().equals("akun")) {
                    Users users = userService.findUsers(update.getCallbackQuery().getMessage().getChatId());
                    try {
                        execute(new StartCommand().accountDetails(users, update));
                    } catch (TelegramApiException e) {
                        log.warn(e.getMessage());
                    }

                }
            }
        }


    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
