package com.example.beletsky_ma.mytestproject.SupportUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

  private static final String PREF_ESSEN_PREFS_NAME = "ESSEN_PREFS";
  private static final String PREF_AUTHORIZED = "Authorized";
  private static final String PREF_NEED_TO_DELETE_TOKEN = "need_to_delete_token";
  private static final String PREF_HASH = "h_";
  private static final String PREF_TOKEN = "token";
  private static final String PREF_PREV_HASH = "prev_h";
  private static final String PREF_PATRONOMIC = "patronymic";
  private static final String PREF_ATT_PHONE = "att_phone";
  private static final String PREF_MAIL = "mail";
  private static final String PREF_CARD_CODE = "card_code";
  private static final String PREF_LAST_CARD = "last_card";
  private static final String PREF_SMS_TEXT = "sms_text";
  private static final String PREF_LAST_MESSAGE_ID = "cl_mes_last_id_";
  private static final String PREF_LAST_REQUEST_ID = "cl_req_last_id_";

  private static final String AUTHORIZED = "authorized";
  private static final String USER_ID = "user_id";

  static PreferenceUtils instance;
  private Context context;
  private SharedPreferences preferences;
  private PreferenceUtils() {
  }

  public static PreferenceUtils getInstance() {
    if (instance == null) {
      instance = new PreferenceUtils();
    }
    return instance;
  }

  public void init(Context con) {
    this.context = con;
    preferences = context.getSharedPreferences(PREF_ESSEN_PREFS_NAME, Context.MODE_PRIVATE);
  }

  public void saveAuthorizeData(String hash){
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(PREF_AUTHORIZED, Boolean.TRUE.toString());
    editor.putString(PREF_HASH, hash);
    editor.apply();
  }

  public void setUserId(int id)
  {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(USER_ID, id);
    editor.apply();
  }

  public void setAuthorized(Boolean flag)
  {
      SharedPreferences.Editor editor = preferences.edit();
      editor.putBoolean(AUTHORIZED, flag);
      editor.apply();
  }


  public Boolean isAuthorized(){
    return preferences.getBoolean(AUTHORIZED, false);
  }

  public int getUserId(){
    return preferences.getInt(USER_ID, 0);
  }

  public String getCardCode(){
    return preferences.getString(PREF_CARD_CODE, "error");
  }



  public String getPatronomic(){
    return preferences.getString(PREF_PATRONOMIC, "error");
  }

  public String getAttPhone(){
    return preferences.getString(PREF_ATT_PHONE, "");
  }

  public String getMail(){
    return preferences.getString(PREF_MAIL, "");
  }

  public String getPrevHash(){
    return preferences.getString(PREF_PREV_HASH, "error");
  }

  public String getToken(){
    return preferences.getString(PREF_TOKEN, "error");
  }

  public String getLogin(){
    return preferences.getString(PREF_LAST_CARD, "");
  }

  public String getSmsText(){
    return preferences.getString(PREF_SMS_TEXT, "sms_text");
  }

  public int getLastMessageId() {return preferences.getInt(PREF_LAST_MESSAGE_ID, -1);}

  public int getLastRequestId() {return preferences.getInt(PREF_LAST_REQUEST_ID, -1);}

  public String getNeedToDeleteToken(){
    return preferences.getString(PREF_NEED_TO_DELETE_TOKEN, "error");
  }

}
