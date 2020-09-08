package com.hackathon.wash_p.ui.fragments.step;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.adapters.floor.RecyclerAdapter;
import com.hackathon.wash_p.ui.adapters.washer.RecyclerAdapter3;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WashingNumFragment extends Fragment {
    private List<List_wash> list_washList;
    private RecyclerView recyclerView;
    private RecyclerAdapter3 adapter;
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
                    sectionArray(response);
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


        adapter = new RecyclerAdapter3((position) -> {
        }, getActivity());

        addList();

        recyclerView = view.findViewById(R.id.recyclerView_wash);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public void sectionArray(Response<List<List_wash>> response){
        List<List_wash> list_Section = new ArrayList<>();
        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);
        for(int i = 0 ; i < response.body().size(); i ++){
            if(fg.getWash().getValue().getFloor().contains(response.body().get(i).getFloor()) && fg.getWash().getValue().getWay().contains(response.body().get(i).getWay())){
                list_Section.add(response.body().get(i));
            }
        }
        adapter.updateData(list_Section);
    }
}