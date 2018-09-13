package com.e21cn.module;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.e21cn.apiservice.BaiduOCRService;
import com.e21cn.bean.AccessTokenBean;
import com.e21cn.bean.RecognitionResultBean;
import com.e21cn.utils.RegexUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liangminqiang
 * @Description : MainPresenter
 * @class : MainPresenter
 * @time Create at 6/4/2018 4:21 PM
 */


public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;
    private BaiduOCRService baiduOCRService;

    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String API_KEY = "Oc5lSpwHlboZbAGegbtEYk2P";
    private static final String SECRET_KEY = "azeqztIr37xjzy5MF1mFe1KguHZ4s3nR";
    private static  String ACCESS_TOKEN = "";

    public MainPresenter(MainContract.View mView) {

        this.mView = mView;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://aip.baidubce.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        baiduOCRService = retrofit.create(BaiduOCRService.class);

    }


    @Override
    public void getAccessToken(final Bitmap bitmap) {

        baiduOCRService.getAccessToken(CLIENT_CREDENTIALS,API_KEY,SECRET_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AccessTokenBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccessTokenBean accessTokenBean) {
                        ACCESS_TOKEN=accessTokenBean.getAccess_token();

                        Log.e("Access token",accessTokenBean.getAccess_token());
                        getRecognitionResultByImage(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Access token","error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    @Override
    public void getRecognitionResultByImage(Bitmap bitmap) {

        String encodeResult = bitmapToString(bitmap);

        baiduOCRService.getRecognitionResultByImage(ACCESS_TOKEN,encodeResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecognitionResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RecognitionResultBean recognitionResultBean) {
                        Log.e("onnext",recognitionResultBean.toString());

                        ArrayList<String> wordList = new ArrayList<>();
                        List<RecognitionResultBean.WordsResultBean> wordsResult = recognitionResultBean.getWords_result();
                        if(wordsResult!=null&&wordsResult.size()>0) {
                            for (RecognitionResultBean.WordsResultBean words : wordsResult) {
                                wordList.add(words.getWords());
                            }
                            String result="";
                            for(String str :wordList){
                                result+=str;
                            }

//                            ArrayList<String> numbs = RegexUtils.getNumbs(wordList);
//                            StringBuilder s = new StringBuilder();
//
//                            for (String numb : numbs) {
//                                s.append(numb + "\n");
//                            }

                            mView.updateUI(result);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onerror",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}
