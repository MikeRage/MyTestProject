package com.example.beletsky_ma.mytestproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beletsky_ma.mytestproject.POJO.User;
import com.example.beletsky_ma.mytestproject.SupportUtils.MyApplication;
import com.example.beletsky_ma.mytestproject.View.LKFragment;
import com.example.beletsky_ma.mytestproject.View.LoginFragment;
import com.example.beletsky_ma.mytestproject.View.RegistrationFragment;
import com.example.beletsky_ma.mytestproject.model.LocalRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,LoginFragment.OnFragmentInteractionListener,RegistrationFragment.OnFragmentInteractionListener,LKFragment.OnFragmentInteractionListener {

//    @BindView(R.id.nav_bar_header) LinearLayout nav_header;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    View nav_header;
    FragmentManager fm;
    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        nav_header = navigationView.getHeaderView(0);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if(!MyApplication.getInstance().getPreferenceUtils().isAuthorized())
        {
            clearHeader();
            showAuth();
        }else {
            fillHeader();
            showLK();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void fillHeader()
    {
        User user = LocalRepository.getUserById(MyApplication.getInstance().getPreferenceUtils().getUserId());
        if(user != null) {
            ((TextView) nav_header.findViewById(R.id.name)).setText(user.name);
            ((TextView) nav_header.findViewById(R.id.eMail)).setText(user.email);
        }
        else
        {
            showAuth();
            Toast.makeText(this,"ошибка получения данных пользователя", Toast.LENGTH_LONG).show();
        }
    }

    public void clearHeader()
    {
        ((TextView)nav_header.findViewById(R.id.name)).setText(getString(R.string.not_authorized));
        ((TextView)nav_header.findViewById(R.id.eMail)).setText("");
    }

    public void showAuth()
    {
        LoginFragment loginFragment = new LoginFragment();
        fm.beginTransaction().replace(R.id.main_fragment, loginFragment).commit();
    }

    public void showLK()
    {
        LKFragment lkFragment = new LKFragment();
        fm.beginTransaction().replace(R.id.main_fragment, lkFragment).commit();
    }

    public void showRegstering()
    {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        fm.beginTransaction().replace(R.id.main_fragment, registrationFragment).addToBackStack("auth").commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.personal_room) {
            Log.e(TAG,"personal room");
            if(MyApplication.getInstance().getPreferenceUtils().isAuthorized()) {
                showLK();
            }
            else
            {
                showAuth();
            }
        } else if (id == R.id.promos) {
            Log.e(TAG,"promos");
        } else if (id == R.id.exit) {
            Log.e(TAG,"exit");
            MyApplication.getInstance().getPreferenceUtils().setAuthorized(false);
            ((TextView)nav_header.findViewById(R.id.name)).setText(getString(R.string.not_authorized));
            ((TextView)nav_header.findViewById(R.id.eMail)).setText("");
            showAuth();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
