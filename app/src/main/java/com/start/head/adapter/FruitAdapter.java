package com.start.head.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.start.head.R;
import com.start.head.bean.Fruit;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private List<Fruit> list;
    public FruitAdapter(@NonNull Context context, List<Fruit> list) {
        super(context, 0);
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruit fruit=list.get(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_test_item_layout,parent,false);
            holder=new ViewHolder();
            holder.fruit_id=view.findViewById(R.id.fruit_id);
            holder.fruit_name=view.findViewById(R.id.fruit_name);
            view.setTag(holder);
        }else {
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }
        holder.fruit_id.setText(fruit.getTypeId()+"");
        holder.fruit_name.setText(fruit.getName());
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    private class ViewHolder{
        TextView fruit_id;
        TextView fruit_name;
    }
//    @Nullable
//    @Override
//    public Fruit getItem(int position) {
//        return list.get(position);
//    }
}
