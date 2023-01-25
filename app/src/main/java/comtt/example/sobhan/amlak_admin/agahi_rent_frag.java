package comtt.example.sobhan.amlak_admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class agahi_rent_frag extends Fragment {

    private String name_shoper;
    private ArrayList<agahi_rent> array=new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView rec;
    private ApiServices service;
    private ArrayList<agahi_rent> temp;
    ImageView image;
    Bitmap bit;
    boolean check_alter_image=false;
    public agahi_rent_frag() {
        // Required empty public constructor
    }
    View v;
    boolean search=false;
    boolean check=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agahi_rent_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search=getArguments().getBoolean("search");
        if(search==false) {
            retrofit_connect();
            retrofit_get_data();
        }
        else
        {
            temp= (ArrayList<agahi_rent>) getArguments().getSerializable("array_list");
            array.clear();
            array.addAll(temp);
        }
        rec=view.findViewById(R.id.rec_agahi_rent);
        rec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rec.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter_rent(array);
        rec.setAdapter(mAdapter);
        item_click();
        v=view;
    }

    public void setName_shoper(String name_shoper) {
        this.name_shoper = name_shoper;
    }

    private void retrofit_connect()
    {
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
    }
    public void retrofit_get_data() {

        Call<agahi_list_rent> call = service.Get_agahi_rent(name_shoper);
        call.enqueue(new Callback<agahi_list_rent>() {
            @Override
            public void onResponse(Call<agahi_list_rent> call, retrofit2.Response<agahi_list_rent> response) {
                agahi_list_rent a=response.body();
                if(a.getAgahis()!=null) {
                    TextView tx=v.findViewById(R.id.txview_null_agahi);
                    tx.setVisibility(View.GONE);
                    int t_size=array.size();
                    if(search==false)
                        array.clear();
                    for (int i = 0; i < a.getAgahis().length; i++) {
                        array.add(a.getAgahis()[i]);
                    }
                    if(t_size-array.size()!=0)
                    {
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mAdapter != null && check) {
                        mAdapter.notifyDataSetChanged();
                        check=false;
                    }
                }
                else
                {
                     TextView tx=v.findViewById(R.id.txview_null_agahi);
                     tx.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<agahi_list_rent> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(search=false) {
            array.clear();
            mAdapter.notifyDataSetChanged();
            check = true;
        }
    }


    private void item_click()
    {
        rec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                alert_alter(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                alert_delete(position);
            }
        }));
    }

    private void delete_data_retrofit(final int pos)
    {
        Call<ResponseBody> call = service.delete_agahi_rent(array.get(pos).getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                array.remove(pos);
                mAdapter.notifyDataSetChanged();
                if(array.size()==0)
                {
                    TextView tx=v.findViewById(R.id.txview_null_agahi);
                    tx.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alert_delete(final int position)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setTitle("حذف");
        alert.setMessage("ایا از حذف مطمینی؟");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delete_data_retrofit(position);

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog a=alert.create();
        a.show();
    }

    private void alert_alter(final int position)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setMessage("نام:"+array.get(position).getName()+"\n"+"اطلاعات؛"+array.get(position).getInfo()+"\n"+"متراژ:"+array.get(position).getMetr()+"\n"+"قیمت رهن:"+array.get(position).getRahn()+"\n"+"قیمت اجاره:"+array.get(position).getCost()+"\n"+"منطقه:"+array.get(position).getArea());

        alert.setPositiveButton("ویرایش", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                popup_alter(position);

            }
        });

        AlertDialog al=alert.create();
        al.show();
    }

    private void popup_alter(final int position)
    {
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.alter_rent_alert);
        final EditText et_name=dialog.findViewById(R.id.et_name_alter_rent);
        final EditText et_info=dialog.findViewById(R.id.et_info_alter_rent);
        final EditText et_cost=dialog.findViewById(R.id.et_cost_alter_rent);
        final EditText et_metr=dialog.findViewById(R.id.et_metr_alter_rent);
        final EditText et_phone=dialog.findViewById(R.id.et_phone_alter_rent);
        final EditText et_rahn=dialog.findViewById(R.id.et_rahn_alter_rent);
        final EditText et_address=dialog.findViewById(R.id.et_address_rent_alter);
        final Spinner area=dialog.findViewById(R.id.alter_spinner_rent);
        image=dialog.findViewById(R.id.image_alter_rent);
        Button btn_change_image=dialog.findViewById(R.id.btn_change_rent_alter_image);
        if(array.get(position).getImage().contains("http"))
        {
            Picasso.with(getContext()).load(array.get(position).getImage()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(image);
        }
        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });

        if(array.get(position).getArea().equals("basij"))
        {
            area.setSelection(1);
        }
        else if(array.get(position).getArea().equals("daneshgah"))
        {
            area.setSelection(2);
        }
        else if(array.get(position).getArea().equals("farhangian"))
        {
            area.setSelection(3);
        }

        et_name.setText(array.get(position).getName());
        et_info.setText(array.get(position).getInfo());
        et_cost.setText(array.get(position).getCost());
        et_metr.setText(String.valueOf(array.get(position).getMetr()));
        et_phone.setText(array.get(position).getPhone());
        et_rahn.setText(array.get(position).getRahn());
        et_address.setText(array.get(position).getAddress());
        Button btn=dialog.findViewById(R.id.btn_alter_rent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(area.getSelectedItemPosition()!=0){
                array.get(position).setName(et_name.getText().toString());
                array.get(position).setInfo(et_info.getText().toString());
                array.get(position).setCost(et_cost.getText().toString());
                array.get(position).setMetr(Integer.parseInt(et_metr.getText().toString()));
                array.get(position).setPhone(et_phone.getText().toString());
                array.get(position).setRahn(et_rahn.getText().toString());
                array.get(position).setAddress(et_address.getText().toString());
                if (area.getSelectedItemPosition() == 1) {
                    array.get(position).setArea("basij");
                } else if (area.getSelectedItemPosition() == 2) {
                    array.get(position).setArea("daneshgah");
                } else if (area.getSelectedItemPosition() == 3) {
                    array.get(position).setArea("farhangian");
                }
                alter_data_retrofit(position);
                dialog.dismiss();}
                else
                {
                    Toast.makeText(getContext(), "لطفا منطقه را انتخاب کنید..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void alter_data_retrofit(int pos) {
        String image_change="";
        if(check_alter_image==true)
        {
            ByteArrayOutputStream byt=new ByteArrayOutputStream();
            Bitmap bit_temp=bit;
            bit_temp.compress(Bitmap.CompressFormat.JPEG,100,byt);
            byte[] bytes=byt.toByteArray();
            image_change=Base64.encodeToString(bytes,0);
        }
        Call<ResponseBody> call = service.alter_rent(array.get(pos).getName(),array.get(pos).getInfo(),array.get(pos).getCost(),array.get(pos).getRahn(),array.get(pos).getPhone(),array.get(pos).getMetr(),array.get(pos).getId(),array.get(pos).getArea(),array.get(pos).getAddress(),image_change,check_alter_image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                retrofit_get_data();
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable"+t, Toast.LENGTH_LONG).show();
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
                image.setImageBitmap(bit);
                check_alter_image=true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
