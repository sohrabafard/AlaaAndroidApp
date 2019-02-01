package ir.sanatisharif.android.konkur96.app;

import javax.inject.Singleton;
import dagger.Component;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.api.ApiModule;


@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {


    void inject(MainActivity mainActivity);
}
