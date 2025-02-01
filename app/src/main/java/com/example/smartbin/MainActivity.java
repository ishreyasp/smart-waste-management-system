package com.example.smartbin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  //  private static final int MY_PERMISSION_REQUEST_RECEIVE_SMS = 0;

    DrawerLayout drawerLayout;
    TextView tvInfo;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvInfo = (TextView)findViewById(R.id.tv_info);
        listView = (ListView)findViewById(R.id.listview);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("WELCOME");
        arrayList.add("All SamrtBins are sucessfully deployed");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        /*String itemName = (String)item.getTitle();
        tvInfo.setText(itemName);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch(item.getItemId()){
                    case R.id.item_a: startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        break;

                    case R.id.item_b: startActivity(new Intent(getApplicationContext(),Objective.class));
                        break;

                    case R.id.item_c:  startActivity(new Intent(getApplicationContext(),AboutUs.class));
                        break;
                }

            }
        },200);

        closeDrawer();
        /*switch(item.getItemId()){
            case R.id.item_a: startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                break;

            case R.id.item_b: startActivity(new Intent(getApplicationContext(),Objective.class));
                break;

            case R.id.item_c:  startActivity(new Intent(getApplicationContext(),AboutUs.class));
                break;
        }*/
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.right_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
