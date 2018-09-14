package com.e21cn.model.account.iface;

import com.e21cn.base.IBaseView;
import com.e21cn.model.account.entity.UserBean;

/**
 * 登录view
 *
 * @author ZhongDaFeng
 */

public interface ILoginView extends IBaseView {

    //显示结果
    void showResult(UserBean bean);
    void showLoading();
    void closeLoading();

}
