package com.e21cn.module;

import android.graphics.Bitmap;

/**
 * @author liangminqiang
 * @Description : MainContract
 * @class : MainContract
 * @time Create at 6/4/2018 4:21 PM
 */


public interface MainContract {

    interface View{
        void updateUI(String s);
    }

    interface Presenter{
        void getAccessToken(Bitmap bitmap);
        void getRecognitionResultByImage(Bitmap bitmap);
    }

}
