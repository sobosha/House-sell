package comtt.example.sobhan.amlak_admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.bumptech.glide.signature.ObjectKey;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter_rent extends RecyclerView.Adapter<MyAdapter_rent.MyViewHolder> {
    private ArrayList<agahi_rent> arr;

    Context cnt;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView info;
        public TextView cost;
        public TextView phone;
        public TextView metr;
        public TextView rahn;
        public ImageView image;
        public Button btn;
        public MyViewHolder(View v) {
            super(v);
            name=v.findViewById(R.id.tx_name);
            info=v.findViewById(R.id.tx_info);
            cost=v.findViewById(R.id.tx_cost);
            //btn=v.findViewById(R.id.btn_agahi_see);
            phone=v.findViewById(R.id.tx_phone);
            metr=v.findViewById(R.id.tx_metr);
            rahn=v.findViewById(R.id.tx_rahn);
            image=v.findViewById(R.id.image_rent_rec);
        }
    }


    public MyAdapter_rent(ArrayList<agahi_rent> agahiha) {

        arr = agahiha;


    }


    @Override
    public MyAdapter_rent.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rec_agahi_rent, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        cnt=parent.getContext();
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(arr.get(position).getName());
        holder.info.setText(arr.get(position).getInfo());
        holder.cost.setText(arr.get(position).getCost());
        holder.phone.setText(String.valueOf(arr.get(position).getPhone()));
        holder.metr.setText(String.valueOf(arr.get(position).getMetr()));
        holder.rahn.setText(String.valueOf(arr.get(position).getRahn()));
        String image_path=String.valueOf(arr.get(position).getImage());
        if(!image_path.isEmpty()){
        Picasso.with(cnt)
                .load(image_path)
                .placeholder(R.drawable.ic_menu_gallery)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.image);}
        else{
            Glide.with(cnt).load(image_path).placeholder(R.drawable.ic_menu_gallery).into(holder.image);
        }


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

}
