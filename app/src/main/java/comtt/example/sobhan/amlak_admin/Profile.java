package comtt.example.sobhan.amlak_admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {
    private ApiServices service;
    ArrayList<agahi_rent> rent=new ArrayList<>();
    ArrayList<agahi> forosh;
    String area_temp="";
    int i_check=0;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.syncState();
        set_name_item_change();
        load_after_login();
        change_frag();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setarrayrent();
                search_agahi();

            }
        });




    }


    private void load_after_login()
    {
        user= (User) getIntent().getSerializableExtra("User");
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragmentB = fragmentManager.findFragmentByTag("agahi_forosh_frag");

        if (fragmentB == null) {
            fragmentB = new agahi_forosh_frag();
            ((agahi_forosh_frag) fragmentB).setName_shoper(get_item_prof_name());
            Bundle bundle=new Bundle();
            bundle.putBoolean("search",false);
            fragmentB.setArguments(bundle);

        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frag_change,fragmentB,"agahi_forosh_frag");
        transaction.commit();
        forosh=((agahi_forosh_frag) fragmentB).getArray();

    }

    private void load_profile_frag()
    {
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragprof=fm.findFragmentByTag("prof_frag");
        if(fragprof == null)
        {
            fragprof=new profile_frag();
            ((profile_frag) fragprof).setArgUser(user);
        }
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.frag_change,fragprof,"prof_frag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //setting animation for fragment transaction
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void load_agahi_rent_frag()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragmentB = fragmentManager.findFragmentByTag("agahi_rent_frag");

        if (fragmentB == null) {
            fragmentB = new agahi_rent_frag();
            ((agahi_rent_frag) fragmentB).setName_shoper(get_item_prof_name());
            Bundle bundle=new Bundle();
            bundle.putBoolean("search",false);
            fragmentB.setArguments(bundle);

        }
        else
        {
            ((agahi_rent_frag) fragmentB).retrofit_get_data();

        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_change, fragmentB, "agahi_rent_frag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //setting animation for fragment transaction
        transaction.addToBackStack(null);
        transaction.commit();
        setarrayrent();

    }

    private void load_agahi_frag()
    {

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragmentB = fragmentManager.findFragmentByTag("agahi_forosh_frag");

        if (fragmentB == null) {
            fragmentB = new agahi_forosh_frag();
            ((agahi_forosh_frag) fragmentB).setName_shoper(get_item_prof_name());
            Bundle bundle=new Bundle();
            bundle.putBoolean("search",false);
            fragmentB.setArguments(bundle);
        }
        else
        {
            ((agahi_forosh_frag) fragmentB).retrofit_get_data();

        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_change, fragmentB, "agahi_forosh_frag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //setting animation for fragment transaction
        transaction.addToBackStack(null);
        transaction.commit();
        forosh=((agahi_forosh_frag) fragmentB).getArray();
    }

    private void load_afzoodan_frag()
    {
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragafz=fm.findFragmentByTag("afzoodan_frag");
        if(fragafz == null)
        {
            fragafz=new afzoodan_frag();
            ((afzoodan_frag) fragafz).setName_shoper(get_item_prof_name());
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frag_change,fragafz,"afzoodan_frag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //setting animation for fragment transaction
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void load_message_frag()
    {
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragmessage=fm.findFragmentByTag("message_frag");
        if(fragmessage == null)
        {
            fragmessage=new message_frag();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frag_change,fragmessage,"message_frag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //setting animation for fragment transaction
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void change_frag()
    {
        NavigationView navigation_view=findViewById(R.id.navigation_view);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.prof:
                        load_profile_frag();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.forosh_item:
                        load_agahi_frag();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.add_item:
                        load_afzoodan_frag();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.message_item:
                        load_message_frag();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.rent_item:
                        load_agahi_rent_frag();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.exit:
                        Intent i=new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }

    public void set_name_item_change()
    {

        String name=getIntent().getStringExtra("name");
        String image=getIntent().getStringExtra("image");
        NavigationView nav=findViewById(R.id.navigation_view);
        View v=nav.getHeaderView(0);
        TextView tx_header=v.findViewById(R.id.user_name_prof_nav);
        ImageView img_prof=v.findViewById(R.id.image_profile);
        DownloadImageFromPath(image,img_prof);
        tx_header.setText(name);
        Menu menu=nav.getMenu();
        MenuItem item=menu.findItem(R.id.name_from_prof);
        item.setTitle(name);
    }

    public String get_item_prof_name()
    {
        String name=getIntent().getStringExtra("name");
        NavigationView nav=findViewById(R.id.navigation_view);
        Menu menu=nav.getMenu();
        MenuItem item=menu.findItem(R.id.name_from_prof);
        return item.getTitle().toString();
    }

    private void search_agahi()
    {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=getLayoutInflater();
        final View custom_search=layoutInflater.inflate(R.layout.search_messagebox,null);
        final RadioButton radio_forosh=custom_search.findViewById(R.id.search_radio_forosh);
        final RadioButton radio_rent=custom_search.findViewById(R.id.search_radio_rent);
        final LinearLayout ln=custom_search.findViewById(R.id.linear_rahn);
        final LinearLayout l=custom_search.findViewById(R.id.radio_search);
        builder.setView(custom_search);
        final AlertDialog dial=builder.create();
       radio_forosh.setChecked(true);
       radio_forosh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b)
               {

                   l.setVisibility(View.VISIBLE);
               }
               else
               {
                   l.setVisibility(View.GONE);
               }
           }
       });
        radio_rent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });
        Button btn_serach=custom_search.findViewById(R.id.btn_search);
        final EditText et_cost=custom_search.findViewById(R.id.search_cost);
        final EditText et_rahn=custom_search.findViewById(R.id.search_rahn);
        final EditText et_metr=custom_search.findViewById(R.id.search_metr);
        Spinner area=custom_search.findViewById(R.id.search_spinner);
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==1)
                {
                    area_temp="basij";
                    i_check=1;
                }
                else if(i==2) {
                    area_temp = "daneshgah";
                    i_check=2;
                }
                else if (i==3) {
                    area_temp = "farhangian";
                    i_check=3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i_check == 0) {
                    Toast.makeText(Profile.this, "لطفا یک منطقه را انتخاب کنید..", Toast.LENGTH_SHORT).show();
                } else {
                    if (radio_forosh.isChecked()) {
                        RadioButton rd_btn_vila=custom_search.findViewById(R.id.vila_search_radio);
                        RadioButton rd_btn_aparteman=custom_search.findViewById(R.id.aparteman_search_radio);
                        RadioButton rd_btn_sanad=custom_search.findViewById(R.id.sanad_search_radio);
                        RadioButton rd_btn_gholname=custom_search.findViewById(R.id.gholname_search_radio);
                        if (et_cost.getText().toString().equals("") || et_metr.getText().toString().equals("")) {
                            Toast.makeText(Profile.this, "فیلد نمی تواند خالی باشد!", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<agahi> temp = forosh;
                            for (agahi a : new ArrayList<agahi>(forosh)) {
                                if (Integer.parseInt(a.getCost()) > Integer.parseInt(et_cost.getText().toString())) {
                                    temp.remove(a);
                                    continue;
                                }
                                if (a.getMetr() > Integer.parseInt(et_metr.getText().toString())) {
                                    temp.remove(a);
                                    continue;
                                }
                                if (!a.getArea().equals(area_temp)) {
                                    temp.remove(a);
                                    continue;
                                }
                                if(rd_btn_aparteman.isChecked() && a.isType())
                                {
                                    temp.remove(a);
                                    continue;
                                }
                                else if(rd_btn_vila.isChecked() && !a.isType())
                                {
                                    temp.remove(a);
                                    continue;
                                }

                                if(rd_btn_sanad.isChecked() && !a.isSanad())
                                {
                                    temp.remove(a);
                                    continue;
                                }
                                else if(rd_btn_gholname.isChecked() && a.isSanad())
                                {
                                    temp.remove(a);
                                    continue;
                                }
                            }
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            Fragment fragmentB = new agahi_forosh_frag();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("search", true);
                            bundle.putSerializable("array_list", forosh);
                            fragmentB.setArguments(bundle);
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frag_change, fragmentB, "agahi_forosh_search");
                            transaction.commit();
                            dial.dismiss();
                            setarrayforosh();

                        }
                    } else if (radio_rent.isChecked()) {
                        if (et_cost.getText().toString().equals("") || et_metr.getText().toString().equals("") || et_rahn.getText().toString().equals("")) {
                            Toast.makeText(Profile.this, "فیلد نمی تواند خالی باشد!", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<agahi_rent> temp = rent;
                            for (agahi_rent a : new ArrayList<agahi_rent>(rent)) {
                                if (Integer.parseInt(a.getCost()) > Integer.parseInt(et_cost.getText().toString())) {
                                    temp.remove(a);
                                    continue;
                                }
                                if (a.getMetr() > Integer.parseInt(et_metr.getText().toString())) {
                                    temp.remove(a);
                                    continue;
                                }
                                if (Integer.parseInt(a.getRahn()) > Integer.parseInt(et_rahn.getText().toString())) {
                                    temp.remove(a);
                                    continue;
                                }
                                if (!a.getArea().equals(area_temp)) {
                                    temp.remove(a);
                                    continue;
                                }
                            }
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            Fragment fragmentB = new agahi_rent_frag();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("search", true);
                            bundle.putSerializable("array_list", rent);
                            fragmentB.setArguments(bundle);
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frag_change, fragmentB, "agahi_rent_search");
                            transaction.commit();
                            dial.dismiss();
                            setarrayrent();

                        }
                    }
                }
            }
        });
        dial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dial.show();




       /* final Dialog dialog=new Dialog(getBaseContext());
        dialog.setContentView(R.layout.search_messagebox);

        RadioButton radio_forosh=dialog.findViewById(R.id.search_radio_forosh);
        RadioButton radio_rent=dialog.findViewById(R.id.search_radio_rent);
        final LinearLayout ln=dialog.findViewById(R.id.linear_rahn);
       *//* radio_forosh.setChecked(true);
        radio_rent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    ln.setVisibility(View.VISIBLE);
                else
                    ln.setVisibility(View.GONE);
            }
        });*//*

        Button btn_serach=dialog.findViewById(R.id.btn_search);
        btn_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/


    }

    private void setarrayrent() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sobosha.ir/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ApiServices.class);
        Call<agahi_list_rent> call = service.Get_agahi_rent(get_item_prof_name());
        call.enqueue(new Callback<agahi_list_rent>() {
            @Override
            public void onResponse(Call<agahi_list_rent> call, retrofit2.Response<agahi_list_rent> response) {
                agahi_list_rent a=response.body();
                rent.clear();
                if(a.getAgahis()!=null){
                for(int i=0;i<a.getAgahis().length;i++)
                {
                    rent.add(a.getAgahis()[i]);
                }}

            }

            @Override
            public void onFailure(Call<agahi_list_rent> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setarrayforosh() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sobosha.ir/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ApiServices.class);
        Call<agahi_list> call = service.Get_agahi(get_item_prof_name());
        call.enqueue(new Callback<agahi_list>() {
            @Override
            public void onResponse(Call<agahi_list> call, retrofit2.Response<agahi_list> response) {
                agahi_list a=response.body();
                forosh.clear();
                if(a.getAgahis()!=null){
                for(int i=0;i<a.getAgahis().length;i++)
                {
                    forosh.add(a.getAgahis()[i]);
                }}

            }

            @Override
            public void onFailure(Call<agahi_list> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void DownloadImageFromPath(String path,ImageView image){

        if(!path.isEmpty()){
            image.setBackground(null);
        Picasso.with(getApplicationContext())
                .load(path)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(image);}
        else{
            image.setBackground(null);
            Glide.with(getApplicationContext())
                    .load(path)
                    .placeholder(R.drawable.ic_android_black_24dp)
                    .into(image);

        }
    }


}
