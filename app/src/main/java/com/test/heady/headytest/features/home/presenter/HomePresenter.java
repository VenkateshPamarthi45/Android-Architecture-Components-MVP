package com.test.heady.headytest.features.home.presenter;

import com.test.heady.headytest.features.home.interactor.HomeInterActor;
import com.test.heady.headytest.features.home.view.HomeActivityInterface;
import com.test.heady.headytest.localdb.ApiResponseDatabase;

public class HomePresenter implements HomePresenterInterface {

    private HomeActivityInterface homeActivityInterface;
    private ApiResponseDatabase apiResponseDatabase;
    private HomeInterActor homeInterActor;

    public HomePresenter(HomeActivityInterface homeActivityInterface, ApiResponseDatabase apiResponseDatabase) {
        this.homeActivityInterface = homeActivityInterface;
        this.apiResponseDatabase = apiResponseDatabase;
        homeInterActor = new HomeInterActor(apiResponseDatabase);

    }

    @Override
    public void getResponse() {
        homeActivityInterface.showProgressBar();
        homeInterActor.getApiResponse(this);
    }

    @Override
    public void responseReceived() {
        homeActivityInterface.hideProgressBar();
        homeActivityInterface.showProductVariantData();
    }
}
