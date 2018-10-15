package com.example.beletsky_ma.mytestproject.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.beletsky_ma.mytestproject.POJO.City;
import com.example.beletsky_ma.mytestproject.POJO.User;
import com.example.beletsky_ma.mytestproject.SupportUtils.MyApplication;
import com.example.beletsky_ma.mytestproject.model.DbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.beletsky_ma.mytestproject.SupportUtils.MyApplication.myDbHelper;

public class LocalRepository {

    DbHelper db;
    SQLiteDatabase rwdb;
    public static LocalRepository sInstance;
    public LocalRepository()
    {
        MyApplication.getInstance();
        db = myDbHelper;
        rwdb = db.getWritableDatabase();
    }

    public static LocalRepository getsIntstance() {
        if(sInstance == null)
            sInstance = new LocalRepository();
        return sInstance;
    }

    public User getUser(String email, String pass)
    {
        User user = null;
        Cursor cur = rwdb.rawQuery(("SELECT user_id,name,email,balance,city_id FROM user_info where email = \""+email+"\" and pass = \""+pass+"\""), null);
        if (cur.moveToFirst()) {
            user = new User();
            user.user_id = cur.getInt(0);
            user.name = cur.getString(1);
            user.email = cur.getString(2);
            user.balance = cur.getDouble(3);
            user.city_id = cur.getInt(4);
        }
        cur.close();
        return user;
    }

    public static boolean createUser(String name,String email, String pass,int city_id)
    {
        MyApplication.myDbHelper.getWritableDatabase().execSQL("INSERT INTO user_info (name,email,pass,city_id) values('"+name+"','"+email+"','"+pass+"',"+city_id+")");
        return true;
    }

    public static User getUserById(int user_id)
    {
        User user = null;
        Cursor cur = MyApplication.myDbHelper.getWritableDatabase().rawQuery(("SELECT user_id,name,email,balance,city_id FROM user_info where user_id  = "+user_id), null);
        if (cur.moveToFirst()) {
            user = new User();
            user.user_id = cur.getInt(0);
            user.name = cur.getString(1);
            user.email = cur.getString(2);
            user.balance = cur.getDouble(3);
            user.city_id = cur.getInt(4);
        }
        cur.close();
        return user;
    }

    public static List<City> getCitiesList()
    {
        List<City> list = new ArrayList<>();
        Cursor cur = MyApplication.myDbHelper.getWritableDatabase().rawQuery(("SELECT city_id,name FROM cities"), null);
        while(cur.moveToNext())
        {
            City city = new City();
            city.id = cur.getInt(0);
            city.name = cur.getString(1);
            list.add(city);
        }
        return list;
    }
}
