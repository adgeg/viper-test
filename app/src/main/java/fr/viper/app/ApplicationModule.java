package fr.viper.app;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.viper.app.executor.UiThreadExecutor;

public class ApplicationModule {
    private final Executor asyncExecutor;
    private final Executor uiThreadExecutor;

    public ApplicationModule() {
        this.asyncExecutor = Executors.newSingleThreadExecutor();
        this.uiThreadExecutor = new UiThreadExecutor();
    }

    public Executor getAsyncExecutor() {
        return asyncExecutor;
    }

    public Executor getUiThreadExecutor() {
        return uiThreadExecutor;
    }
}
