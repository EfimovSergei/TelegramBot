package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {
    public final String BOT_TOKEN = "6511885114:AAHks1aNEumtWcHygi6vpJyK7_7X2Zi8CzY";
    public final String BOT_USERNAME = "NASAPicturesBot23_bot";
    public final String url = "https://api.nasa.gov/planetary/apod?api_key=LIFSbL8OTGsECIfALkf0cdhCeF7BeqQC1eU4V2JK";
    public static long chatId;

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот! Я высылаю ссылки на картинки NASA по запросу! " +
                            "Картинка обновляется раз в сутки!");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getURL(url));

                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
            }
        }

    }

    public MyTelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
