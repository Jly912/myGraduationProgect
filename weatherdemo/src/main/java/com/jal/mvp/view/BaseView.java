package com.jal.mvp.view;

/**
 * Created by SEELE on 2017/3/9.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showFailedError(String error);

    void showToast(String msg);

}
