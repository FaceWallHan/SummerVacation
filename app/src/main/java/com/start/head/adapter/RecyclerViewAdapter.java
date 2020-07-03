package com.start.head.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.start.head.R;
import com.start.head.bean.Fruit;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<Fruit> list;
    public RecyclerViewAdapter( List<Fruit> list) {
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_test_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
        holder.fruit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                 Fruit fruit=list.get(position);
                Toast.makeText(parent.getContext(), "you clicked view"+fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fruit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Fruit fruit=list.get(position);
                Toast.makeText(parent.getContext(), "you clicked view"+fruit.getTypeId(), Toast.LENGTH_SHORT).show();
            }
        });
        /**点击事件竟然可以在适配器里直接写？ListView好像就不可以吧？？？
         * 2020年7月3日21:32:03，不打印日志了，无法验证RecyclerViewAdapter和ListView是否滑动实时刷新position？？？
         * */
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit=list.get(position);
        holder.fruit_id.setText(fruit.getTypeId()+"");
        holder.fruit_name.setText(fruit.getName());
        Log.d(TAG, "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruit_id;
        TextView fruit_name;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             fruit_id=itemView.findViewById(R.id.fruit_id);
             fruit_name=itemView.findViewById(R.id.fruit_name);
         }
     }
}
