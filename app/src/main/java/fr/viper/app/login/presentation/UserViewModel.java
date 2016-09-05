package fr.viper.app.login.presentation;

public class UserViewModel {
    private final String title;
    private final String description;

    public UserViewModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
