package com.e21cn.model.other.iface;

import com.e21cn.base.IBaseView;
import com.e21cn.model.other.entity.AddressBean;

/**
 * 手机归属地页面view接口
 *
 * @author ZhongDaFeng
 */

public interface IPhoneAddressView extends IBaseView {

    //显示结果
    void showResult(AddressBean bean);

}
