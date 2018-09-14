package com.e21cn.model.multiple;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.e21cn.R;
import com.e21cn.base.BaseFragmentActivity;
import com.e21cn.base.BasePagerAdapter;
import com.ruffian.library.RVPIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Fragment使用示例
 *
 * @author ZhongDaFeng
 */
public class MultipleActivity extends BaseFragmentActivity {

    @BindView(R.id.vp_indicator)
    RVPIndicator vpIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mList = Arrays.asList("用户登录", "号码查询");

    @Override
    public int setContentView() {
        return R.layout.multiple_activity;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {



        mFragmentList.add(new LoginFragment());
        mFragmentList.add(new PhoneAddressFragment());
        mPagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), mFragmentList);

        //设置指示器title
        vpIndicator.setTitleList(mList);

        // 设置关联的ViewPager
        vpIndicator.setViewPager(viewPager, 0);

        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
    }
}
