package fr.viper.app;

import android.app.Application;
import android.content.Context;

public class ViperApplication extends Application{
    private ApplicationModule applicationModule;

    public static ApplicationModule getApplicationModule(Context context){
        return ((ViperApplication)context.getApplicationContext()).getApplicationModule();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationModule = new ApplicationModule(this);
    }

    public ApplicationModule getApplicationModule() {
        return applicationModule;
    }
}
