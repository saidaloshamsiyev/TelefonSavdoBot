package uz.pdp.bot;


import lombok.extern.slf4j.Slf4j;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.model.Phone;
import uz.pdp.model.User;
import uz.pdp.model.UserState;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


import static uz.pdp.bot.Buttons.*;
import static uz.pdp.bot.Main.*;

@Slf4j
public class CommandHandler extends MyBot {

    private static final String channelLink = "https://t.me/telefonsavdotoshken";

    public void handle(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();


        if (Objects.equals(text, "/start")) {
            getSendMessage(chatId, message.getFrom());
            return;
        }

        GetChatMember getChatMember = GetChatMember.builder().chatId("@telefonsavdotoshken").userId(chatId).build();
        ChatMember chatMember = execute(getChatMember);
        if (!chatMember.getStatus().equals("member") && !chatMember.getStatus().equals("creator")) {
            SendMessage sendMessage = new SendMessage(chatId.toString(), "Please subscribe to our channel");
            sendMessage.setReplyMarkup(Buttons.channelLink());
            send(sendMessage);
            return;
        }


        User currentUser = userService.findByChatId(chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (Objects.equals(text, "Announcement")) {
            send(getSendMessage1(chatId));
            return;
        }

        if (Objects.equals(text, "Buying a Phone")) {
            send(getMessage(chatId));
            return;
        }
        if (Objects.equals(text, "Download all File")) {
            send(getDownload(chatId));
            return;
        }

        if (Objects.equals(text, "Back")) {
            getSendMessage(chatId, message.getFrom());
            return;
        }

        if (Objects.equals(text, "Download for Word File")) {
            send(handleWordFile(chatId));
            return;
        }
        if (Objects.equals(text, "Download for Exel File")) {
            send(handleExelFile(chatId));
            return;
        }

        if (Objects.equals(text, "Phone")) {
            Phone phone = Phone.builder().isCreating(true).ownerId(currentUser.getId()).build();
            phoneService.add(phone);

            currentUser.setUserState(UserState.ASKING_OWNER);
            userService.update(currentUser.getId(), currentUser);

            sendMessage.setText("Owner 👤");
            send(sendMessage);
            return;
        } else if (Objects.equals(text, "Pad")) {
            Phone phone = Phone.builder().isCreating(true).ownerId(currentUser.getId()).build();
            phoneService.add(phone);

            currentUser.setUserState(UserState.ASKING_OWNER);
            userService.update(currentUser.getId(), currentUser);

            sendMessage.setText("Owner 👤");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_OWNER)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setOwner(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_MODEL);
            userService.update(currentUser.getId(), currentUser);

            sendMessage.setText("Model 📱");
            send(sendMessage);
            return;
        }


        if (currentUser.getUserState().equals(UserState.ASKING_MODEL)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setModel(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_RAM);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Ram/Operativka 🗂️");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_RAM)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setRam(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_DOCUMENT);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Document 📕");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_DOCUMENT)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setDocument(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_ADDRESS);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Address 📍");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_ADDRESS)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setAddress(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_PRICE);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Price 💸");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_PRICE)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setPrice(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_TEL);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Tel 🔠");
            send(sendMessage);
            return;

        }


        if (currentUser.getUserState().equals(UserState.ASKING_TEL)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setPhoneNumber(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_EXTRA);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" Extra info 📭");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_EXTRA)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setExtra(message.getText());
            phoneService.update(phone.getId(), phone);

            currentUser.setUserState(UserState.ASKING_REQUEST);
            userService.update(currentUser.getId(), currentUser);
            sendMessage.setText(" For Reference 🖋️  ");
            send(sendMessage);
            return;
        }

        if (currentUser.getUserState().equals(UserState.ASKING_REQUEST)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setForReference(message.getText());
            phoneService.update(phone.getId(), phone);


            currentUser.setUserState(UserState.ASKING_PHOTO);
            userService.update(currentUser.getId(), currentUser);

            sendMessage.setText("Send Photo");
            send(sendMessage);
            return;

           /* currentUser.setUserState(UserState.SEND_REQUEST);
            userService.update(currentUser.getId(), currentUser);

            sendMessage.setText("Successfully ✅");
            sendMessage.setReplyMarkup(getSendChannelRequest());
            send(sendMessage);
            return;*/
        }
        if (currentUser.getUserState().equals(UserState.ASKING_PHOTO)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phoneService.update(phone.getId(), phone);
            if (message.hasPhoto()) {

                currentUser.setUserState(UserState.SEND_REQUEST);
                userService.update(currentUser.getId(), currentUser);
                List<PhotoSize> photo = message.getPhoto();
                //String fileId = photo.stream().sorted(Comparator.comparing(PhotoSize::getFileId).reversed()).findFirst().orElse(null).getFileId();
                ArrayList<String> photos = new ArrayList<>();
                for (PhotoSize photoSize : photo) {
                    photos.add(photoSize.getFileId());
                }

                phone.setPhotoId(photos);
                phoneService.update(phone.getId(), phone);
                sendMessage.setText("Successfully ✅");
                sendMessage.setReplyMarkup(getSendChannelRequest());
                send(sendMessage);
                return;
            }


               /* phoneService.update(phone.getId(), phone);

                currentUser.setUserState(UserState.SEND_REQUEST);
                userService.update(currentUser.getId(), currentUser);

                sendMessage.setText("Successfully ✅");
                sendMessage.setReplyMarkup(getSendChannelRequest());
                send(sendMessage);
                return;*/


        }


        if (sendRequest(currentUser, message, text, sendMessage)) return;


        sendMessage.setText("Please Choose Right Menu");
        send(sendMessage);
    }

    private boolean sendRequest(User currentUser, Message message, String text, SendMessage sendMessage) {
        if (currentUser.getUserState().equals(UserState.SEND_REQUEST)) {
            Phone phone = phoneService.getCreatingPhone(currentUser.getId());
            phone.setSend(message.getText());
            phoneService.update(phone.getId(), phone);

            if (Objects.equals(text, "Send to Channel")) {
                sendMessageToChannel(phone, phone.toString(), phone.getPhotoId().getFirst());
                sendMessage.setText("Main menu");
                sendMessage.setReplyMarkup(getButtons());
                send(sendMessage);
                return true;
            } else if (Objects.equals(text, "Cancel")) {
                sendMessage.setText("Ok");
                sendMessage.setReplyMarkup(getButtons());
                send(sendMessage);
                return true;
            }
        }
        return false;
    }


    private void sendMessageToChannel(Phone phone, String text, String input) {
        ArrayList<InputMedia> inputMedias = new ArrayList<>();
        for (String s : phone.getPhotoId()) {
            InputMedia inputs = new InputMediaPhoto();
            inputs.setCaption(text);
            inputs.setMedia(s);
            inputMedias.add(inputs);
        }
        SendMediaGroup sendMediaGroup = new SendMediaGroup("@telefonsavdotoshken", inputMedias);

        try {
            execute(sendMediaGroup);
            execute(new SendMessage("@telefonsavdotoshken", text));
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }


    }


    private SendMessage getMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("""
                 Xurmatli Mijoz siz O'zingiz va Xamyoningiz uchun 
                 Qulay va Sifatli bo'lgan Telefonlarni Shu Kanala orqali Topa Olasiz ☺\uFE0F
                """);

        InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    private SendMediaGroup multipleSendPhoto(Message message) {
        Long chatId = message.getChatId();

        ArrayList<InputMedia> inputMedias = new ArrayList<>();

        InputMedia inputMedia1 = new InputMediaPhoto();


        inputMedia1.setCaption("go on");
        inputMedia1.setMedia("AgACAgIAAxkBAAIBK2ZGzVSlf5iwOBaxbyGnGQpo1E1BAAIX1DEb-9k4SjMluUJ_V9RuAQADAgADcwADNQQ");

        inputMedias.add(inputMedia1);
        inputMedias.add(inputMedia1);
        inputMedias.add(inputMedia1);
        SendMediaGroup sendMediaGroup = new SendMediaGroup(chatId.toString(), inputMedias);
        return sendMediaGroup;
    }


    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Kanalga o'tish uchun Bosing");
        button.setUrl(channelLink);
        rowInline.add(button);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }


    private void getSendMessage(Long chatId, org.telegram.telegrambots.meta.api.objects.User from) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getButtons());


        try {
            User user = userService.findByChatId(chatId);
            sendMessage.setText("Welcome back @" + user.getUsername());
            send(sendMessage);
        } catch (RuntimeException e) {
            userService.add(new User(from.getFirstName(), from.getUserName(), chatId, null));
            sendMessage.setText("Welcome ");
            send(sendMessage);
        }
    }

    private static SendMessage getSendMessage1(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), " Choose Announcement Menu  🪄 ");
        sendMessage.setReplyMarkup(getAnnouncement());
        return sendMessage;
    }


    private static SendMessage getDownload(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Choose Download Menu ");
        sendMessage.setReplyMarkup(getDownloadButtons());
        return sendMessage;
    }

    private void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePhoneWordFile() {
        List<Phone> phones = phoneService.getAll();
        String fileName = "src/main/resources/phone.docx";
        try (XWPFDocument document = new XWPFDocument()) {
            for (Phone phone : phones) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("Owner: " + phone.getOwner());
                run.addBreak();
                run.setText("Model: " + phone.getModel());
                run.addBreak();
                run.setText("Ram: " + phone.getRam());
                run.addBreak();
                run.setText("Document: " + phone.getDocument());
                run.addBreak();
                run.setText("Address: " + phone.getDocument());
                run.addBreak();
                run.setText("Price: " + phone.getPrice());
                run.addBreak();
                run.setText("Tel: " + phone.getPhoneNumber());
                run.addBreak();
                run.setText("Extra: " + phone.getExtra());
                run.addBreak();
                run.setText("For Reference: " + phone.getForReference());
                run.addBreak();
                run.addBreak();
                try (FileOutputStream out = new FileOutputStream(fileName)) {
                    document.write(out);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }


    private String generateExelFile() {
        List<Phone> phones = phoneService.getAll();
        String fileName = "src/main/resources/phone.xlsx";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Phone");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Owner", "Model", "Ram", "Document", "Address", "Price", "Tel", "Extra", "For Reference "};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (Phone phone : phones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(phone.getOwner());
                row.createCell(1).setCellValue(phone.getModel());
                row.createCell(2).setCellValue(phone.getRam());
                row.createCell(3).setCellValue(phone.getDocument());
                row.createCell(4).setCellValue(phone.getAddress());
                row.createCell(5).setCellValue(phone.getPrice());
                row.createCell(6).setCellValue(phone.getPhoneNumber());
                row.createCell(7).setCellValue(phone.getExtra());
                row.createCell(8).setCellValue(phone.getForReference());
                try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                    workbook.write(fileOutputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    private void sendExelFile(Long chatId, String fileName) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(new java.io.File(fileName)));
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private SendMessage handleExelFile(Long chatId) {
        String fileName = generateExelFile();
        sendExelFile(chatId, fileName);
        return new SendMessage(chatId.toString(), "Exel file with all Announcement has been send to you ☺\uFE0F\"");
    }

    private void sendWordFile(Long chatId, String filePath) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId.toString());
        sendDocument.setDocument(new InputFile(new java.io.File(filePath)));

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage handleWordFile(Long chatId) {
        String filePath = generatePhoneWordFile();
        sendWordFile(chatId, filePath);
        return new SendMessage(chatId.toString(), "Word file with all Announcement has been send to you ☺️");
    }


    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
};



