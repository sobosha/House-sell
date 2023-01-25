package comtt.example.sobhan.amlak_admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<agahi> arr;

    Context cnt;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView info;
        public TextView cost;
        public TextView phone;
        public TextView metr;
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
            image=v.findViewById(R.id.image_view_sale_rec);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<agahi> agahiha) {

        arr = agahiha;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rec_agahi, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        cnt=parent.getContext();
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(arr.get(position).getName());
        holder.info.setText(arr.get(position).getInfo());
        holder.cost.setText(arr.get(position).getCost());
        holder.phone.setText(String.valueOf(arr.get(position).getPhone()));
        holder.metr.setText(String.valueOf(arr.get(position).getMetr()));
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arr.size();
    }
}
