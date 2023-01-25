package comtt.example.sobhan.amlak_admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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


public class agahi_forosh_frag extends Fragment {
    private String name_shoper;
    private ArrayList<agahi> array = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<agahi> temp;
    RecyclerView rec;
    ImageView image;
    Bitmap bit;
    boolean check_alter_image=false;
    private ApiServices service;

    public agahi_forosh_frag() {
        // Required empty public constructor
    }

    boolean search = false;
    View v;
    boolean check = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //volley_get_data();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //v=inflater.inflate(R.layout.fragment_agahi_frag, container, false);
       /* rec=new RecyclerView(getContext());
        //rec=getView().findViewById(R.id.rec_agahi);
        rec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rec.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(array);
        rec.setAdapter(mAdapter);*/
        return inflater.inflate(R.layout.fragment_agahi_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = getArguments().getBoolean("search");
        if (search == false) {
            retrofit_connect();
            retrofit_get_data();
        } else {
            temp = (ArrayList<agahi>) getArguments().getSerializable("array_list");
            array.clear();
            array.addAll(temp);
        }
        rec = view.findViewById(R.id.rec_agahi);
        rec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rec.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(array);
        rec.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();

        item_click();
        v = view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (search = false) {
            array.clear();
            mAdapter.notifyDataSetChanged();
            check = true;
        }
    }

   /* public void volley_get_data() {
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url = "http://sobosha.freehost.io/get_agahi.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getContext(), "response:" + response, Toast.LENGTH_SHORT).show();
                        string_response_handler(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name_shoper);
                return params;


            }
        };
        queue.add(postRequest);
    }*/

    public void setName_shoper(String name_shoper) {
        this.name_shoper = name_shoper;
    }

    /*private void string_response_handler(String input) {
        String temp[] = input.split(";");
        for (int i = 0; i < temp.length; i++) {
            String temp2[] = temp[i].split(" ");
            agahi a = new agahi();
            a.setName(temp2[0]);
            a.setInfo(temp2[1]);
            a.setCost(temp2[2]);
            array.add(a);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

    }*/

    private void retrofit_connect() {
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
        Call<agahi_list> call = service.Get_agahi(name_shoper);
        call.enqueue(new Callback<agahi_list>() {
            @Override
            public void onResponse(Call<agahi_list> call, retrofit2.Response<agahi_list> response) {
                agahi_list a = response.body();
                if (a.getAgahis() != null) {
                    TextView tx = v.findViewById(R.id.txview_null_agahi_forosh);
                    tx.setVisibility(View.GONE);
                    int t_size = array.size();
                    if (search == false)
                        array.clear();

                    for (int i = 0; i < a.getAgahis().length; i++) {
                        array.add(a.getAgahis()[i]);
                    }
                    if (t_size - array.size() != 0) {
                        mAdapter.notifyDataSetChanged();
                    }
                    if (mAdapter != null && check) {
                        mAdapter.notifyDataSetChanged();
                        check = false;
                    }
                } else {
                    TextView tx = v.findViewById(R.id.txview_null_agahi_forosh);
                    tx.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<agahi_list> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable" + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void item_click() {
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

    private void delete_data_retrofit(final int pos) {
        Call<ResponseBody> call = service.delete_agahi_forosh(array.get(pos).getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                array.remove(pos);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable" + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alert_delete(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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

        AlertDialog a = alert.create();
        a.show();
    }

    private void alert_alter(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("نام:" + array.get(position).getName() + "\n" + "اطلاعات؛" + array.get(position).getInfo() + "\n" + "متراژ:" + array.get(position).getMetr() + "\n" + "قیمت:" + array.get(position).getCost() + "\n" + "منطقه:" + array.get(position).getArea() + "\n" + "آدرس:" + array.get(position).getAddress());

        alert.setPositiveButton("ویرایش", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                popup_alter(position);

            }
        });

        AlertDialog al = alert.create();
        al.show();
    }

    private void popup_alter(final int position) {
        check_alter_image=false;
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alter_forosh_alert);
        final EditText et_name = dialog.findViewById(R.id.et_name_alter);
        final EditText et_info = dialog.findViewById(R.id.et_info_alter);
        final EditText et_cost = dialog.findViewById(R.id.et_cost_alter);
        final EditText et_metr = dialog.findViewById(R.id.et_metr_alter);
        final EditText et_phone = dialog.findViewById(R.id.et_phone_alter);
        final EditText et_address = dialog.findViewById(R.id.et_address_alter);
        final RadioButton radio_vila = dialog.findViewById(R.id.radio_vila_alter);
        final RadioButton radio_aparteman = dialog.findViewById(R.id.radio_aparteman_alter);
        final RadioButton radio_sanad = dialog.findViewById(R.id.radio_sanad_alter);
        final RadioButton radio_gholname = dialog.findViewById(R.id.radio_gholname_alter);
        final Spinner area = dialog.findViewById(R.id.alter_spinner);
        Button btn_alter_image=dialog.findViewById(R.id.btn_alter_image_sale);
        image=dialog.findViewById(R.id.image_alter);

        if(array.get(position).getImage().contains("http")){
            Picasso.with(getContext()).load(array.get(position).getImage()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(image);}
        btn_alter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        if (array.get(position).isSanad()) {
            radio_sanad.setChecked(true);
        } else {
            radio_gholname.setChecked(true);
        }

        if (array.get(position).isType()) {
            radio_vila.setChecked(true);
        } else {
            radio_aparteman.setChecked(true);
        }

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
        et_address.setText(array.get(position).getAddress());
        et_name.setText(array.get(position).getName());
        et_info.setText(array.get(position).getInfo());
        et_cost.setText(array.get(position).getCost());
        et_metr.setText(String.valueOf(array.get(position).getMetr()));
        et_phone.setText(array.get(position).getPhone());

        Button btn = dialog.findViewById(R.id.btn_alter_forosh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(area.getSelectedItemPosition()!=0) {
                    array.get(position).setName(et_name.getText().toString());
                    array.get(position).setInfo(et_info.getText().toString());
                    array.get(position).setCost(et_cost.getText().toString());
                    array.get(position).setMetr(Integer.parseInt(et_metr.getText().toString()));
                    array.get(position).setPhone(et_phone.getText().toString());
                    array.get(position).setAddress(et_address.getText().toString());
                    if (area.getSelectedItemPosition() == 1) {
                        array.get(position).setArea("basij");
                    } else if (area.getSelectedItemPosition() == 2) {
                        array.get(position).setArea("daneshgah");
                    } else if (area.getSelectedItemPosition() == 3) {
                        array.get(position).setArea("farhangian");
                    }
                    if(radio_sanad.isChecked())
                    {
                        array.get(position).setSanad(true);
                    }
                    else{
                        array.get(position).setSanad(false);
                    }

                    if(radio_vila.isChecked())
                    {
                        array.get(position).setType(true);
                    }
                    else{
                        array.get(position).setType(false);
                    }
                    alter_data_retrofit(position);
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), "لطفا منطقه را انتخاب کنید..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void alter_data_retrofit(int pos) {
        String image_change="";
        if(check_alter_image){
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        Bitmap bit_byt= bit;
        bit_byt.compress(Bitmap.CompressFormat.JPEG,100,byt);
        byte[] bytes=byt.toByteArray();
        image_change= Base64.encodeToString(bytes,0);}
        Call<ResponseBody> call = service.alter_forosh(array.get(pos).getName(), array.get(pos).getInfo(), array.get(pos).getCost(), array.get(pos).getPhone(), array.get(pos).getMetr(), array.get(pos).getId(), array.get(pos).getAddress(), array.get(pos).getArea(), array.get(pos).isType(), array.get(pos).isSanad(),image_change,check_alter_image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable" + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<agahi> getArray() {
        return array;
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
