package ru.juli.praktikum.client;

public class Credentials {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;
    private String password;

    public static Credentials fromUser(User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
