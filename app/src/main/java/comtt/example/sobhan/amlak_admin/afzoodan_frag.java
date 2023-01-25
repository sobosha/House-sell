package comtt.example.sobhan.amlak_admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class afzoodan_frag extends Fragment {

    private String name_shoper;
    String[] area_string = {""};
    final boolean[] vila = {false};
    final boolean[] sanad = {false};
    Bitmap bitmap;
    ImageView img;
    int i_check;
    public afzoodan_frag() {

    }

    public void setName_shoper(String name_shoper) {
        this.name_shoper = name_shoper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_afzoodan_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RadioButton radio_sale=view.findViewById(R.id.radio_sale);
        final RadioButton radio_rent=view.findViewById(R.id.radio_rent);
        final EditText rahn=view.findViewById(R.id.et_rahn_afzfrag);
        final EditText temp=view.findViewById(R.id.et_cost_afzfrag);
        final RadioGroup sanad_type=view.findViewById(R.id.sanad_type);
        final RadioGroup vila_type=view.findViewById(R.id.vila_type);
        final Spinner area=view.findViewById(R.id.spinner_area);
        final RadioButton radio_sanad=view.findViewById(R.id.sanaddar);
        final RadioButton radio_vila=view.findViewById(R.id.vila);
        radio_sale.setChecked(true);
        radio_sanad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_sanad.isChecked()) {
                    sanad[0] = true;
                } else {
                    sanad[0] = false;
                }
            }
        });

        radio_vila.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_vila.isChecked())
                    vila[0] = true;
                else {
                    vila[0] = false;
                }
            }
        });
        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1)
                {
                area_string[0]="basij";}
                else if(i==2)
                    area_string[0]="daneshgah";
                else if(i==3)
                    area_string[0]="farhangian";

                i_check=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        radio_rent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    rahn.setVisibility(View.VISIBLE);
                    sanad_type.setVisibility(View.GONE);
                    vila_type.setVisibility(View.GONE);
                    temp.setHint("قیمت اجاره");
                }
            }
        });
        radio_sale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    rahn.setVisibility(View.GONE);
                    sanad_type.setVisibility(View.VISIBLE);
                    vila_type.setVisibility(View.VISIBLE);
                    temp.setHint("قیمت");
                }
            }
        });
        Button btn_add=view.findViewById(R.id.btn_afzfrag);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                //add_volley(view);
                if(i_check!=0) {
                    if (radio_sale.isChecked())
                        add_sale_retrofit(view);
                    else if (radio_rent.isChecked())
                        add_rent_retrofit(view);
                }
                else
                {
                    Toast.makeText(getContext(), "لطفا یک منطقه را انتخاب کنید!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button btn_select_imag=view.findViewById(R.id.btn_selectimg_afzfrag);
        img=view.findViewById(R.id.img_afzfrg);
        btn_select_imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void add_rent_retrofit(View view) {
        final EditText et_name=view.findViewById(R.id.et_name_afzfrag);
        final EditText et_info=view.findViewById(R.id.et_info_afzfrag);
        final EditText et_cost=view.findViewById(R.id.et_cost_afzfrag);
        final EditText et_phone=view.findViewById(R.id.et_phone_afzfrag);
        final EditText et_metr=view.findViewById(R.id.et_metr_afzfrag);
        final EditText et_rahn=view.findViewById(R.id.et_rahn_afzfrag);
        final EditText et_address=view.findViewById(R.id.address);
        ByteArrayOutputStream by=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,by);
        byte[] bytes=by.toByteArray();
        String image_select= Base64.encodeToString(bytes,0);
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sobosha.ir/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiServices service = retrofit.create(ApiServices.class);
        Call<ResponseBody> call = service.Sabt_agahi_rent(et_name.getText().toString(),et_info.getText().toString(),et_cost.getText().toString(),et_rahn.getText().toString(),name_shoper,et_phone.getText().toString(),Integer.parseInt(et_metr.getText().toString()),area_string[0],et_address.getText().toString(),image_select,et_name.getText().toString()+et_info.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Toast.makeText(getContext(), "ثبت شد", Toast.LENGTH_SHORT).show();
                et_cost.setText("");
                et_info.setText("");
                et_metr.setText("");
                et_name.setText("");
                et_phone.setText("");
                et_rahn.setText("");
                et_address.setText("");
                img.setImageBitmap(null);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void add_volley(final View view)
    {
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url="http://sobosha.freehost.io/sabt_agahi.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getContext(), "response:"+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                EditText et_name=view.findViewById(R.id.et_name_afzfrag);
                EditText et_info=view.findViewById(R.id.et_info_afzfrag);
                EditText et_cost=view.findViewById(R.id.et_cost_afzfrag);
                EditText et_phone=view.findViewById(R.id.et_phone_afzfrag);
                EditText et_metr=view.findViewById(R.id.et_metr_afzfrag);
                if(!et_name.getText().toString().equals("") && !et_info.getText().toString().equals("")  && !et_cost.getText().toString().equals("")){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", et_name.getText().toString());
                    params.put("info", et_info.getText().toString());
                    params.put("cost", et_cost.getText().toString());
                    params.put("cost", et_cost.getText().toString());
                    params.put("cost", et_cost.getText().toString());
                    params.put("shoper",name_shoper);
                    return params;
                }
                else {
                    //Toast.makeText(MainActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    return null;
                }

            }
        };
        queue.add(postRequest);

    }

    private void add_sale_retrofit(final View view)
    {
        final EditText et_name=view.findViewById(R.id.et_name_afzfrag);
        final EditText et_info=view.findViewById(R.id.et_info_afzfrag);
        final EditText et_cost=view.findViewById(R.id.et_cost_afzfrag);
        final EditText et_phone=view.findViewById(R.id.et_phone_afzfrag);
        final EditText et_metr=view.findViewById(R.id.et_metr_afzfrag);
        final EditText et_address=view.findViewById(R.id.address);
        ByteArrayOutputStream by=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,by);
        byte[] bytes=by.toByteArray();
        String image_select= Base64.encodeToString(bytes,0);
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sobosha.ir/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiServices service = retrofit.create(ApiServices.class);
        Call<test_get_sale> call = service.Sabt_agahi(et_name.getText().toString(),et_info.getText().toString(),et_cost.getText().toString(),name_shoper,et_phone.getText().toString(),Integer.parseInt(et_metr.getText().toString()), sanad[0], vila[0], area_string[0],et_address.getText().toString(),image_select,et_name.getText().toString()+et_info.getText().toString());

        call.enqueue(new Callback<test_get_sale>() {
            @Override
            public void onResponse(Call<test_get_sale> call, retrofit2.Response<test_get_sale> response) {
                Toast.makeText(getContext(), "ثبت شد", Toast.LENGTH_LONG).show();
                //Toast.makeText(getContext(), response.body().getImage()+" "+response.body().getTitle()+" "+response.body().getName()+" "+response.body().getCost()+" "+response.body().getPhone()+" "+response.body().getMetr(), Toast.LENGTH_LONG).show();
                et_cost.setText("");
                et_info.setText("");
                et_metr.setText("");
                et_name.setText("");
                et_phone.setText("");
                et_address.setText("");
                img.setImageBitmap(null);
            }

            @Override
            public void onFailure(Call<test_get_sale> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}
