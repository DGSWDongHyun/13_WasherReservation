package com.hackathon.wash_p.ui.fragments.step;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.adapters.RecyclerAdapter;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WashingNumFragment extends Fragment {
    private List<List_wash> list_washList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private Viewmodel_fragment fg;
    private Call<List<List_wash>> request;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void addList(){
        list_washList  = new ArrayList<>();

        request = Server.getInstance().getApi().getData();

        request.enqueue(new Callback<List<List_wash>>() {
            @Override
            public void onResponse(Call<List<List_wash>> call, Response<List<List_wash>> response) {
                if(response.code() == 200){
                    Log.i("i", "Success : here to message \n "+response.body());
                    adapter.updateData(response.body());
                }else{
                    Log.i("i", "Failed : here to message \n "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<List_wash>> call, Throwable t) {

            }
        });

        adapter.setData(list_washList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_washing_num, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addList();

        recyclerView = view.findViewById(R.id.recyclerView_wash);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}