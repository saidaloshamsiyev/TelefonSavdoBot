package uz.pdp.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyBot extends TelegramLongPollingBot {

    public static CommandHandler commandHandler = new CommandHandler();



    public MyBot() {
        super("7002301359:AAHlIRpqamc_PTrWmpqOebUl8LK1q5HBNEI");
    }

    ExecutorService executorService = Executors.newFixedThreadPool(50);


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        executorService.execute(() -> {
            try {
                commandHandler.handle(update);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });


    }



    @Override
    public String getBotUsername() {
        return "b30echo_bot";
    }
}
