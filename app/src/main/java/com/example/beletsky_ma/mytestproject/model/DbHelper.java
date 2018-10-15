package com.example.beletsky_ma.mytestproject.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper implements BaseColumns
{
    private static final String DATABASE_NAME = "mytestproject.db";
    private static final int DATABASE_VERSION = 1;

    private static DbHelper sInstance = null;

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory)
    {
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context)
    {
        if(sInstance == null)
        {
            sInstance = new DbHelper(context,null);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d("DBHelper","creating DB");
        db.execSQL("CREATE TABLE user_info(user_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL, name TEXT, pass TEXT, email TEXT, balance REAL, city_id INTEGER);");
        db.execSQL("INSERT INTO user_info(user_id,name,pass,email,balance,city_id) VALUES (1,\"Mike\",\"pass\",\"myMail@email.org\",119.56,1)");
        db.execSQL("CREATE TABLE cities(city_id INTEGER PRIMARY KEY, name TEXT);");
        db.execSQL("CREATE INDEX cities_i1 on cities(city_id)");
        db.execSQL("INSERT INTO cities (city_id,name) VALUES (1,\"Moscow\");");
        db.execSQL("INSERT INTO cities (city_id,name) VALUES (2,\"St.Petersburg\");");
        db.execSQL("CREATE TABLE promos(promo_id INTEGER PRIMARY KEY, article INTEGER, name TEXT, price REAL, disc_price REAL);");
        db.execSQL("INSERT INTO promos(promo_id,article,name,price,disc_price) VALUES (1,123,\"Зубная щетка\",58.49,47.00);");
        db.execSQL("INSERT INTO promos(promo_id,article,name,price,disc_price) VALUES (2,125,\"Обувная щетка\",58.49,47.00);");
        db.execSQL("INSERT INTO promos(promo_id,article,name,price,disc_price) VALUES (3,128,\"Апельсины весовые\",58.49,47.00);");
        db.execSQL("INSERT INTO promos(promo_id,article,name,price,disc_price) VALUES (4,132,\"Электровелосипед\",58.49,47.00);");
        db.execSQL("INSERT INTO promos(promo_id,article,name,price,disc_price) VALUES (5,137,\"Зубная щетка\",58.49,47.00);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //обновление структуры БД
    }
}
