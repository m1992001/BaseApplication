package com.e21cn.model.multiple;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e21cn.R;
import com.e21cn.base.BaseFragment;
import com.e21cn.model.other.entity.AddressBean;
import com.e21cn.model.other.iface.IPhoneAddressView;
import com.e21cn.model.other.presenter.PhoneAddressPresenter;
import com.e21cn.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手机号码归属地查询Fragment
 *
 * @author ZhongDaFeng
 */
public class PhoneAddressFragment extends BaseFragment implements IPhoneAddressView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_operator)
    TextView tvOperator;

    private PhoneAddressPresenter mPhonePst = new PhoneAddressPresenter(this, this);


    @Override
    public int setContentView() {
        return R.layout.activity_phone_address;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                attemptSubmit();
                break;
        }
    }

    /**
     * 尝试提交
     */
    private void attemptSubmit() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, getString(R.string.hint_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        mPhonePst.phoneQuery(phone);

    }

    @Override
    public void showResult(AddressBean bean) {
        if (bean == null) {
            return;
        }
        tvPhone.setText(bean.getMobileNumber());
        tvCity.setText(bean.getCity());
        tvProvince.setText(bean.getProvince());
        tvOperator.setText(bean.getOperator());
    }


    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
