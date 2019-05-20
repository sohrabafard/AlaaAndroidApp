package ir.sanatisharif.android.konkur96.app;

import javax.inject.Singleton;

import dagger.Component;
import ir.sanatisharif.android.konkur96.api.ApiModule;
import ir.sanatisharif.android.konkur96.handler.EncryptedDownloadRepository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;


@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void inject(RepositoryImpl repository);

    void inject(EncryptedDownloadRepository repository);
}
