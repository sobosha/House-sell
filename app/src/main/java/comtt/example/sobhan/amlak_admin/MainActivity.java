package comtt.example.sobhan.amlak_admin;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

//TODO:Import volley
public class MainActivity extends AppCompatActivity {
    EditText user,pass,family;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RegisterVolley();
                RegisterRetrofit();
            }
        });



        Button btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }

    private void RegisterRetrofit()
    {
        user=findViewById(R.id.username);
    pass=findViewById(R.id.password);
    family=findViewById(R.id.family);

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
    User userreg=new User();
        userreg.setUsername(user.getText().toString());
        userreg.setPassword(pass.getText().toString());
        userreg.setFamily(family.getText().toString());
    Call<ResponseBody> call = service.insertData(userreg.getUsername(),userreg.getPassword(),userreg.getFamily());

        call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Toast.makeText(MainActivity.this, "response"+response, Toast.LENGTH_LONG).show();

            user.setText("");
            pass.setText("");
            family.setText("");
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Throwable"+t, Toast.LENGTH_LONG).show();
        }
    });
}
}
