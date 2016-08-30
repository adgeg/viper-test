package fr.viper.app.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class UiThreadExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
