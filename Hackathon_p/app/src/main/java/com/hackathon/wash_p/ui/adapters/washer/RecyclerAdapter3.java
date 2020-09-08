package com.hackathon.wash_p.ui.adapters.washer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.ui.adapters.listener.onItemClickListener;

import java.util.List;

import retrofit2.Response;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.ViewHolder> {
    private List<List_wash> list_washes;
    private onItemClickListener listener;
    private Context context;
    private int position;
    private ViewHolder holder;

    private Response<List<List_wash>> response;
    public RecyclerAdapter3(onItemClickListener listener, Response<List<List_wash>> response, Context context){
        this.listener = listener;
        this.response = response;
        this.context = context;
    }
    public void setData(List<List_wash> list_washes){
        this.list_washes = list_washes;
    }
    public void updateData(List<List_wash> list_washes){
        this.list_washes = list_washes;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name, textView_usingNow;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.item_name);
            textView_usingNow = itemView.findViewById(R.id.usingNow);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_final, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List_wash list_wash = list_washes.get(position);

        this.position = position;
        this.holder = holder;

        holder.textView_name.setText(list_wash.getWasherNum() + "번 세탁기");
        if(list_wash.getCheckWasher()){
            holder.textView_usingNow.setTextColor(context.getColor(R.color.Red));
            holder.textView_usingNow.setText("사용 불가능");
        }else{
            holder.textView_usingNow.setTextColor(context.getColor(R.color.Green));
            holder.textView_usingNow.setText("사용 가능");
        }

        holder.itemView.setOnClickListener(v->{
            listener.OnItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return list_washes.size();
    }
}

