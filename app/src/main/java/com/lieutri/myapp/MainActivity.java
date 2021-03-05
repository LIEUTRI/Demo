package com.lieutri.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lieutri.myapp.components.User;
import com.lieutri.myapp.components.ViewPagerFragmentAdapter;
import com.lieutri.myapp.fragments.Scrolling2Fragment;
import com.lieutri.myapp.fragments.ScrollingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private MaterialToolbar topAppBar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ViewPager2 viewPager2;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAppBar = findViewById(R.id.topAppBar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPager2 = findViewById(R.id.pager);

        topAppBar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Handle menu item selected
            if (menuItem.isChecked())
                drawerLayout.closeDrawers();
            return true;
        });

//        firebaseAuth = FirebaseAuth.getInstance();

        ////////////////////////////

        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerFragmentAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setPageTransformer(new MarginPageTransformer(500));

        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "Page: "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        };
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser == null){
//            Toast.makeText(MainActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this, LoginActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        } else {
//            startActivity(new Intent(MainActivity.this, HomeActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}