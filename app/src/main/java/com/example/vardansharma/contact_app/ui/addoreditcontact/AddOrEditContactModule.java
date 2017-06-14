package com.example.vardansharma.contact_app.ui.addoreditcontact;

import com.example.vardansharma.contact_app.data.dataSource.DataSource;

import dagger.Module;
import dagger.Provides;

@Module
public class AddOrEditContactModule {
    private AddOrEditCotactContract.Screen screen;

    public AddOrEditContactModule(AddOrEditCotactContract.Screen screen) {
        this.screen = screen;
    }

    @Provides
    public AddOrEditCotactContract.Screen providesScreen() {
        return screen;
    }

    @Provides
    public AddOrEditCotactContract.Presenter providePresenter(DataSource dataSource) {
        return new AddOrEditContactPresenter(screen, dataSource);
    }
}
