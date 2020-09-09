package com.hackathon.wash_p.ui.adapters.way;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.list.List_wash;
import com.hackathon.wash_p.ui.adapters.listener.onItemClickListener;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    private List<List_wash> list_washes;
    private onItemClickListener listener;
    private FragmentActivity activity;
    public RecyclerAdapter2(onItemClickListener listener, FragmentActivity activity){
        this.listener = listener;
        this.activity = activity;
    }
    public void setData(List<List_wash> list_washes){
        this.list_washes = list_washes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView_name = itemView.findViewById(R.id.item_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_final, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List_wash list_wash = list_washes.get(position);

        holder.textView_name.setText(list_wash.getWay());

        holder.itemView.setOnClickListener(v->{
            listener.OnItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return list_washes.size();
    }
}

