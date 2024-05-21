package uz.pdp.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.bot.MyBot;
import uz.pdp.service.PhoneService;
import uz.pdp.service.UserService;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);
    public static PhoneService phoneService = PhoneService.getInstance();

    public static UserService userService = UserService.getInstance();

    public static void main(String[] args) {


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new MyBot());
            System.out.println("Bot Start");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static String inputStr(String hint) {
        System.out.println(hint);
        return scannerStr.nextLine();
    }

    public static int inputInt(String hint) {
        System.out.println(hint);
        return scannerInt.nextInt();
    }
}