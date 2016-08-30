package fr.viper.app;

public interface Attachable<VIEW> {
    void attach(VIEW view);

    void detach();
}
