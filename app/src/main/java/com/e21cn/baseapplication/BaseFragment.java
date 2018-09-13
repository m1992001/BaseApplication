package com.e21cn.baseapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liangminqiang on 2018/6/26.
 */

public abstract class BaseFragment extends Fragment {

    public abstract void initView();
    public abstract  int setContentView();
    private View mView;
    public Context mcontext;
    public Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(mView==null){
            try{
                mView=inflater.inflate(setContentView(),null);

                unbinder = ButterKnife.bind(this, mView);

            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
//

        mcontext=getActivity();
        initView();
        return mView;
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public void showToast(String mes){
        Toast.makeText(getActivity(),mes,Toast.LENGTH_SHORT).show();
    }
    private ProgressDialog pdialog ;
    public void showProDialog(final String mes) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                pdialog = new ProgressDialog(getActivity());
                pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pdialog.setTitle("");
                pdialog.setMessage(mes);
                pdialog.setIndeterminate(false);
                pdialog.setCancelable(true);


                pdialog.show();
                Looper.loop();
            }
        });
        t.start();
    }
    public void hidProDialog(){
        if(pdialog!=null){
            pdialog.cancel();
            pdialog.cancel();
        }
    }
}
