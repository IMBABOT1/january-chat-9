package com.geekbrains.chat.server;

public interface AuthManager {
    String getNicknameByLoginAndPassword(String login, String password);

    void changeNick(String newNick, String oldNick);

    void start();
    void stop();
}
