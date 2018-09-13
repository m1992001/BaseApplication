package com.e21cn.module;

import android.content.Context;
import android.util.Log;

import com.e21cn.apiservice.ToolServerce;
import com.e21cn.utils.Contacts;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liangminqiang on 2018/4/11.
 */

public class ToolRequest {

    public ToolRequest(){


    }
    public static  OkHttpClient.Builder  getCer(Context mcontext){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        try {
            setCertificates(clientBuilder, mcontext.getAssets().open("kwt.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientBuilder;
    }
    /** * 通过okhttpClient来设置证书
     * @param //clientBuilder OKhttpClient.builder
     * @param //certificates 读取证书的InputStream */
    public static  void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) certificate.close();
                } catch (IOException e) {
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static Call<ResponseBody> update(){
//        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//        try {
//            setCertificates(clientBuilder, mcontext.getAssets().open("kwt.cer"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        Retrofit retrofit= new Retrofit.Builder().baseUrl(Contacts.BASE_URL)
//                .client(clientBuilder.build())

        Retrofit retrofit= new Retrofit.Builder().baseUrl(Contacts.UPDATEURL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ToolServerce request=retrofit.create(ToolServerce.class);
        Call<ResponseBody> call= request.update();
        Log.d("KSZJWallet", ""+call.request().url().toString());
        return call;
    }



}
