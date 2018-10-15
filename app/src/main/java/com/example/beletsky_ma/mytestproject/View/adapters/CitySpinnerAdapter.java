package com.example.beletsky_ma.mytestproject.View.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.beletsky_ma.mytestproject.POJO.City;
import com.example.beletsky_ma.mytestproject.R;

import java.util.List;

public class CitySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    public List<City> cityList;
    private final Context context;
    LayoutInflater mInflater;
    public CitySpinnerAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
//                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        City city = cityList.get(position);
//        View v = mInflater.inflate(R.layout.list_item_city,parent,false);
//        ((TextView)v.findViewById(R.id.city_id)).setText(city.id);
//        ((TextView)v.findViewById(R.id.city_name)).setText(city.name);
        return getDropDownView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        City city = cityList.get(position);
        View v = mInflater.inflate(R.layout.list_item_city,parent,false);
        ((TextView)v.findViewById(R.id.city_id)).setText(city.id+"");
        ((TextView)v.findViewById(R.id.city_name)).setText(city.name);
        return v;
    }
}
