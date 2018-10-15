package com.example.beletsky_ma.mytestproject.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beletsky_ma.mytestproject.MainActivity;
import com.example.beletsky_ma.mytestproject.POJO.User;
import com.example.beletsky_ma.mytestproject.R;
import com.example.beletsky_ma.mytestproject.SupportUtils.MyApplication;
import com.example.beletsky_ma.mytestproject.model.LocalRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LKFragment extends Fragment {

    private final static String TITLE = "личный кабинет";
    @BindView(R.id.name) TextView mNameTextView;
    @BindView(R.id.email)TextView mEmailTextView;
    @BindView(R.id.balance)TextView mBalanceTextView;
    @BindView(R.id.city)TextView mCityTextView;

    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    MainActivity activity;
    public LKFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lk, container, false);
        unbinder = ButterKnife.bind(this,rootView);

        User user = LocalRepository.getUserById(MyApplication.getInstance().getPreferenceUtils().getUserId());
        mNameTextView.setText(user.name);
        mEmailTextView.setText(user.email);
        mBalanceTextView.setText(user.balance+"");


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
        activity.setTitle(TITLE);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
        activity = null;
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
