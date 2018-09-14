package com.e21cn.model.account.presenter;

import com.e21cn.base.BasePresenter;
import com.e21cn.model.account.biz.UserBiz;
import com.e21cn.model.account.entity.UserBean;
import com.e21cn.model.account.iface.ILoginView;
import com.e21cn.model.other.presenter.PhoneAddressPresenter;
import com.e21cn.utils.LogUtils;
import com.e21cn.vender.http.RHttpCallback;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 登录Presenter
 *
 * @author ZhongDaFeng
 */

public class LoginPresenter extends BasePresenter<ILoginView, LifecycleProvider> {

    private final String TAG = PhoneAddressPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     *
     * @author ZhongDaFeng
     */
    public void login(String userName, String password) {

        if (getView() != null)
            getView().showLoading();



        RHttpCallback httpCallback = new RHttpCallback<UserBean>() {

            @Override
            public UserBean convert(JsonElement data) {
                return new Gson().fromJson(data, UserBean.class);
            }

            @Override
            public void onSuccess(UserBean object) {
                if (getView() != null) {
                    getView().showLoading();
                    getView().showResult(object);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showToast(desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtils.e("请求取消了");
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        };

        new UserBiz().login(userName, password, getActivity(), httpCallback);

    }

}
