package com.start.head.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.start.head.R;
import com.start.head.activity.FruitActivity;
import com.start.head.bean.Fruit;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> list;
    private Context context;
    public FruitAdapter(List<Fruit> list) {
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (context==null){
            context=parent.getContext();
        }
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fruit fruit=list.get(holder.getAdapterPosition());
                Intent intent=new Intent(context,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_DATA,fruit);
//                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
//                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getTypeId());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit=list.get(position);
        holder.fruit_name.setText(fruit.getName());
        Glide.with(context).load(fruit.getTypeId()).into(holder.fruit_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruit_name;
        ImageView fruit_image;
        CardView cardView;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             cardView= (CardView) itemView;
             fruit_image=itemView.findViewById(R.id.fruit_image);
             fruit_name=itemView.findViewById(R.id.fruit_name);
         }
     }
}
