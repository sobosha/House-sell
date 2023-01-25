package comtt.example.sobhan.amlak_admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class profile_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    User user;
    private ApiServices service;
    ImageView image;
    Bitmap bit;
    //private OnFragmentInteractionListener mListener;

    public profile_frag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imageView=view.findViewById(R.id.image_profile_frag);
        image=imageView;
        Button btn_change_profile_image=view.findViewById(R.id.btn_change_profile_image);
        TextView username=view.findViewById(R.id.username_profile_txview);
        TextView family=view.findViewById(R.id.family_profile_txview);
        TextView senfi_code=view.findViewById(R.id.senficode_profile_txview);
        Button btn_change_password=view.findViewById(R.id.btn_change_password);

        username.setText(user.getUsername());
        family.setText(user.getFamily());
        senfi_code.setText(user.getSenficode());

        Picasso.with(getContext()).load(user.getImage_url()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_android_black_24dp).into(imageView);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_pass();
            }
        });

        btn_change_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });


    }

    public void setArgUser(User user_get){
        user=user_get;
    }

    private void change_pass(){
        OkHttpClient httpClient=new OkHttpClient();
        Gson gson=new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://sobosha.ir/").client(httpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
        service=retrofit.create(ApiServices.class);

        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.change_password_dialog);
        final EditText now_password = dialog.findViewById(R.id.now_password);
        final EditText new_password = dialog.findViewById(R.id.new_password);
        final EditText confirm = dialog.findViewById(R.id.confirm_new_password);
        Button btn_change=dialog.findViewById(R.id.btn_change_password_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now_password.getText().toString().equals(user.getPassword())){
                    if(new_password.getText().toString().equals(confirm.getText().toString()))
                    {
                        //TODO send Api for change password
                        Call<ResponseBody> call=service.change_password(user.getId(),new_password.getText().toString());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(getContext(), "پسورد تغییر کرد", Toast.LENGTH_LONG).show();
                                user.setPassword(new_password.getText().toString());
                                now_password.setText("");
                                new_password.setText("");
                                confirm.setText("");
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "در تغییر پسورد خطایی رخ داده!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "پسورد جدید با تکرار پسورد همخوانی ندارد", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "پسورد فعلی وارد شده اشتباه است!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            try {
                bit= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.change_profile_image);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView imageView=dialog.findViewById(R.id.image_profile_alter);
                Button btn_change=dialog.findViewById(R.id.btn_change_image_alter);
                imageView.setImageBitmap(bit);
                dialog.show();
                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ByteArrayOutputStream byt=new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.JPEG,100,byt);
                        byte[] bytes=byt.toByteArray();
                        String image_change= Base64.encodeToString(bytes,0);

                        OkHttpClient httpClient=new OkHttpClient();
                        Gson gson=new GsonBuilder().setLenient().create();

                        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://sobosha.ir/").client(httpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
                        service=retrofit.create(ApiServices.class);

                        Call<ResponseBody> call=service.change_profile_image(user.getId(),image_change);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(getContext(), "تغییرات ثبت شد", Toast.LENGTH_LONG).show();
                                ((Profile)getActivity()).set_name_item_change();
                                dialog.dismiss();
                                Picasso.with(getContext()).load(user.getImage_url()).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_android_black_24dp).into(image);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
