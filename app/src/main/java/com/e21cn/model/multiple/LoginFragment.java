package com.e21cn.model.multiple;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import com.e21cn.R;
import com.e21cn.base.BaseFragment;
import com.e21cn.model.account.entity.UserBean;
import com.e21cn.model.account.iface.ILoginView;
import com.e21cn.model.account.presenter.LoginPresenter;
import com.e21cn.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录Fragment
 *
 * @author ZhongDaFeng
 */
public class LoginFragment extends BaseFragment implements ILoginView {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);



    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

    }



    @OnClick({R.id.login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    return;
                }

                mLoginPresenter.login(userName, password);
                break;
        }
    }


    @Override
    public void showResult(UserBean bean) {
        if (bean == null) {
            return;
        }
        showToast(bean.getUid());
    }


    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
