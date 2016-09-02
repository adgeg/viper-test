package fr.viper.app;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.viper.app.executor.UiThreadExecutor;

public class ApplicationModule {
    private final Context context;
    private final Executor asyncExecutor;
    private final Executor uiThreadExecutor;

    public ApplicationModule(Context context) {
        this.context = context;
        this.asyncExecutor = Executors.newSingleThreadExecutor();
        this.uiThreadExecutor = new UiThreadExecutor();
    }

    public Executor getAsyncExecutor() {
        return asyncExecutor;
    }

    public Executor getUiThreadExecutor() {
        return uiThreadExecutor;
    }

    public Context getContext() {
        return context;
    }
}
