package com.example.beletsky_ma.mytestproject.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beletsky_ma.mytestproject.MainActivity;
import com.example.beletsky_ma.mytestproject.POJO.City;
import com.example.beletsky_ma.mytestproject.POJO.User;
import com.example.beletsky_ma.mytestproject.R;
import com.example.beletsky_ma.mytestproject.SupportUtils.MyApplication;
import com.example.beletsky_ma.mytestproject.View.adapters.CitySpinnerAdapter;
import com.example.beletsky_ma.mytestproject.model.LocalRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class RegistrationFragment extends Fragment {

    private final static String TITLE = "регистрация";
    @BindView(R.id.name) EditText mNameEditText;
    @BindView(R.id.email) EditText mEmailEditText;
    @BindView(R.id.pass) EditText mPassEditText;
    @BindView(R.id.city_list) Spinner mCitySpinner;
    @BindView(R.id.actionRegister) Button mRegisterButton;
    @BindView(R.id.registering_progress) ProgressBar mProgressView;
    private UserRegisterTask mAuthTask = null;
    public MainActivity activity;
    List<City> cities;
    CitySpinnerAdapter adapter;
    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {

    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        unbinder = ButterKnife.bind(this,rootView);
        adapter = new CitySpinnerAdapter(this.getActivity().getApplicationContext());
        cities = LocalRepository.getCitiesList();
        adapter.cityList = cities;
        mCitySpinner.setAdapter(adapter);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        return rootView;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        mNameEditText.setError(null);
        mEmailEditText.setError(null);
        mPassEditText.setError(null);

        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPassEditText.getText().toString();
//        City city = cities.get(adapter.getItemId());
        int cityId = 2;
        boolean cancel = false;
        View focusView = null;


        if (! TextUtils.isEmpty(password) && ! isPasswordValid(password)) {
            mPassEditText.setError(getString(R.string.error_invalid_password));
            focusView = mPassEditText;
            cancel = true;
        }
        
        if (TextUtils.isEmpty(name) ) {
            mNameEditText.setError(getString(R.string.error_field_required));
            focusView = mPassEditText;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.error_field_required));
            focusView = mEmailEditText;
            cancel = true;
        } else if (! isEmailValid(email)) {
            mEmailEditText.setError(getString(R.string.error_invalid_email));
            focusView = mEmailEditText;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserRegisterTask(name,email,password,cityId);
            mAuthTask.execute((Void) null);
        }
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

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mName;
        private final String mPassword;
        private final int mCityId;
        private User user;
        UserRegisterTask(String name, String email, String password, int city_id) {
            mName = name;
            mEmail = email;
            mPassword = password;
            mCityId = city_id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            LocalRepository.createUser(mName,mEmail,mPassword,mCityId);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
//                Toast.makeText(activity,"логин и пароль верные", Toast.LENGTH_SHORT).show();
//                MyApplication.getInstance().getPreferenceUtils().setAuthorized(true);
//                MyApplication.getInstance().getPreferenceUtils().setUserId(user.user_id);

                activity.showAuth();
//                activity.fillHeader();

            } else {
                showProgress(false);
                Toast.makeText(getActivity(), "не удалось зарегистрироваться", Toast.LENGTH_SHORT).show();
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mRegisterButton.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterButton.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mRegisterButton!=null)
                        mRegisterButton.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            
           /* 
            mEmailEditText.setVisibility(show ? View.GONE : View.VISIBLE);
            mEmailEditText.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mEmailEditText!=null)
                        mEmailEditText.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mPassEditText != null)
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });*/
        } else {
            if(mRegisterButton !=null) {
                mRegisterButton.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
