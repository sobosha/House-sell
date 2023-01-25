package comtt.example.sobhan.amlak_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login=findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_retrofit();
            }
        });
    }

   /* private void Login()
    {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url="http://sobosha.freehost.io/Login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(Login.this, "response:"+response, Toast.LENGTH_SHORT).show();
                        if(response.contains("Login"))
                        {
                            String pas=response.replace("Login ","");
                            Intent intent=new Intent(getApplicationContext(),Profile.class);
                            intent.putExtra("name",pas);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                EditText user=findViewById(R.id.usernamelogin);
                EditText pass=findViewById(R.id.passwordlogin);
                if(!user.getText().toString().equals("") && !pass.getText().toString().equals("")){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", user.getText().toString());
                    params.put("password", pass.getText().toString());
                    return params;
                }
                else {
                    //Toast.makeText(MainActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    return null;
                }

            }
        };
        queue.add(postRequest);
    }*/


    EditText user;
    EditText pass;
    private void Login_retrofit()
    {
        user=findViewById(R.id.usernamelogin);
        pass=findViewById(R.id.passwordlogin);

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
        Call<user_ret> call = service.Logindata(userreg.getUsername(),userreg.getPassword());

        call.enqueue(new Callback<user_ret>() {
            @Override
            public void onResponse(Call<user_ret> call, retrofit2.Response<user_ret> response) {
                user_ret usret=response.body();
                if(usret.isCorrect())
                {
                    User user_login_true=new User();
                    user_login_true.setUsername(usret.getName());
                    user_login_true.setFamily(usret.getFamily());
                    user_login_true.setId(usret.getId());
                    user_login_true.setImage_url(usret.getImage_url());
                    user_login_true.setPassword(usret.getPassword());
                    user_login_true.setSenficode(usret.getSenfi_code());
                    Intent intent=new Intent(getApplicationContext(),Profile.class);
                    intent.putExtra("name",usret.getName());
                    if(usret.getImage_url()!=null){
                    intent.putExtra("image",usret.getImage_url());}
                    else{
                        intent.putExtra("image","");
                    }
                    intent.putExtra("User", user_login_true);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this, "Don't correct!", Toast.LENGTH_SHORT).show();
                    user.setText("");
                    pass.setText("");
                }

                
            }

            @Override
            public void onFailure(Call<user_ret> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}
