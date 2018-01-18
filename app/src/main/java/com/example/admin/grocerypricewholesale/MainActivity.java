package com.example.admin.grocerypricewholesale;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridView;
    ArrayList<String> title;
    ArrayList<Integer> image;
    String userId,userEmail;
    FirebaseAuth mAuth;
    String personName;
    private static final int CONTENT_VIEW_ID = 10101010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image=new ArrayList<Integer>();
        title=new ArrayList<String>();

        image.add(R.drawable.dryfruits);
        title.add("Dry Fruits");

        image.add(R.drawable.cerealsfinal);
        title.add("Pulses");

        image.add(R.drawable.masalafinal);
        title.add("Spices");

        image.add(R.drawable.extragrocery1);
        title.add("Extras");



        gridView=(GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new CustomAdpter(getApplicationContext(),image,title));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("GridViewItem",title.get(i));
                startActivity(intent);
            }
        });


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dryfruits) {
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("GridViewItem",item.getTitle());
            startActivity(intent);
        } else if (id == R.id.nav_pulses) {
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("GridViewItem",item.getTitle());
            startActivity(intent);

        } else if (id == R.id.nav_spices) {
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("GridViewItem",item.getTitle());
            startActivity(intent);

        } else if (id == R.id.nav_extras) {
            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
            intent.putExtra("GridViewItem",item.getTitle());
            startActivity(intent);

        } else if (id == R.id.nav_contactus) {
            Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
            startActivity(intent);

        } else if (id == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
