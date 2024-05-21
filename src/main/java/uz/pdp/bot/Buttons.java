package uz.pdp.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public static ReplyKeyboardMarkup getButtons() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("Announcement");
        row.add("Buying a Phone ");
        keyboardRows.add(row);

        KeyboardRow key1 = new KeyboardRow();
        key1.add("Download all File");
        keyboardRows.add(key1);


        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup channelLink() {
        InlineKeyboardButton button = new InlineKeyboardButton("Kanalga obuna bo'lish");
        button.setUrl("https://t.me/telefonsavdotoshken");
        return new InlineKeyboardMarkup(List.of(List.of(button)));
    }
    public static ReplyKeyboardMarkup getAnnouncement() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow key = new KeyboardRow();
        key.add("Phone");
        key.add(" Pad");
        keyboardRows.add(key);
        KeyboardRow key1 = new KeyboardRow();

        key1.add("Back");
        keyboardRows.add(key1);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getDownloadButtons() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow key = new KeyboardRow();
        key.add("Download for Word File");
        key.add("Download for Exel File");
        keyboardRows.add(key);
        KeyboardRow key1 = new KeyboardRow();
        key1.add("Back");
        keyboardRows.add(key1);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;

    }

    public static ReplyKeyboardMarkup getSendChannelRequest() {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow key = new KeyboardRow();
        key.add("Send to Channel");
        key.add("Cancel");
        keyboardRows.add(key);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;

    }
}
