package com.e21cn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liangminqiang on 2018/3/27.
 */

public class LmqTools {
    public static String Tag="ZS";

    public static final String USERFILENAME = "userinfomation";
    public static String getKSZJWalletPath(){
        String result="";
        try {
            File sdcardDir = Environment.getExternalStorageDirectory();
            result = sdcardDir.getParent() + "/" + sdcardDir.getName() + "/kszj/wallet/";
            File f = new File(result);
            if (!f.exists()) {
                boolean issucces2s = f.mkdirs();
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
    public static void saveIssure(Context context,String uid){
        SharedPreferences.Editor sh=context.getSharedPreferences(USERFILENAME,0).edit();
        sh.putBoolean("sh",false);
        sh.commit();

    }
    public static void saveMY(Context context,String my){
        SharedPreferences.Editor edit=context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS).edit();
        edit.putString("kszj_my",my);
        edit.commit();
    }
    public static String getMY(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("kszj_my","");
    }
    public static boolean isIdCard(String idcard){
        Pattern p=Pattern.compile("^(\\d{15}|\\d{17}[\\dx])$");
        Matcher m=p.matcher(idcard);
        if(!m.matches()){

            return false;
        }
        int w[] = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        int sum = 0;//保存级数和
        for(int i = 0; i <idcard.length() -1 ; i++){
            sum += new Integer(idcard.substring(i,i+1)) * w[i];
        }
        String sums[] = {"1","0","x","9","8","7","6","5","4","3","2"};
        if (sums[(sum%11)].equals(idcard.substring(idcard.length()-1,idcard.length()))) {//与身份证最后一位比较
        }else {

            return false;

        }
        return true;
    }
    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */  //
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
//	    Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isPhone(String phone){
        // Pattern pattern = Pattern.compile("^13/d{9}||15[8,9]/d{8}$");"^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        //  Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern pattern = Pattern.compile("^(13|14|15|16|17|18|19)\\d{9}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    /**
     * SD卡是否存在
     */
    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
    public static String getKSZJImgPath(){
        File sdcardDir = Environment.getExternalStorageDirectory();
        String path = sdcardDir.getParent() + "/" + sdcardDir.getName()+"/kszj/image/";
        File f=new File(path);
        if(!f.exists())
            f.mkdirs();
        return path;

    }
    public static void setUserInfo(Context context,String uid,String key,String type,String phone,String idCard,String name,String pwd){
        SharedPreferences.Editor edit=context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS).edit();
        edit.putString("user_uid",uid);
        edit.putString("user_key",key);
        edit.putString("user_type",type);
        edit.putString("user_phone",phone);
        edit.putString("user_idCard",idCard.equalsIgnoreCase("null")?"":idCard);
        edit.putString("user_name",name.equalsIgnoreCase("null")?"":name);
        edit.putString("user_pwd",pwd);


        edit.commit();
    }

    public static void setUserType(Context context,String type){
        SharedPreferences.Editor edit=context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS).edit();

        edit.putString("user_type",type);


        edit.commit();
    }
    public static void exitLogin(Context context){
        SharedPreferences.Editor edit=context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS).edit();
        //edit.putString("user_uid",uid);
        edit.putString("user_key","");
        edit.putString("user_type","");
        //edit.putString("user_phone","");
        edit.putString("user_idCard","");
        edit.putString("user_name","");
       // edit.putString("user_pwd","");


        edit.commit();
    }
    public static String getUser_Name(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_name","");
    }
    public static String getUser_Pwd(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_pwd","");
    }
    public static String getUser_Id(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_uid","");
    }
    public static String getUser_Key(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_key","");
    }
    public static String getUser_Type(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_type","");
    }
    public static String getUser_Phone(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_phone","");
    }
    public static String getUser_Idcard(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getString("user_idCard","");
    }
    public static void setSHstateRefresh(Context context,boolean shouldrefresh){
        SharedPreferences.Editor edit=context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS).edit();

        edit.putBoolean("shouldrefresh",shouldrefresh);


        edit.commit();
    }
    public static boolean shouldRefreshSHState(Context context){
        SharedPreferences sh = context.getSharedPreferences(USERFILENAME, Context.MODE_MULTI_PROCESS);
        return  sh.getBoolean("shouldrefresh",false);
    }
}
