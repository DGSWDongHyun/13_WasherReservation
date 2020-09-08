package com.hackathon.wash_p.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.ui.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MainFragment extends Fragment {
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<List_wash> list_washList;
    private Call<List<List_wash>> request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }
    public void addList(){
        list_washList  = new ArrayList<>();



        adapter.setData(list_washList);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RecyclerAdapter((position) -> {

            

            Toast.makeText(getContext(), list_washList.get(position).getName_(), Toast.LENGTH_LONG).show();

            /*request = Server.getInstance().getApi().putData(new Apply_wash("1","1","1","1",true, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 100000),"1","1","1"));

            request.enqueue(new Callback<List<List_wash>>() {
                @Override
                public void onResponse(Call<List<List_wash>> call, Response<List<List_wash>> response) {
                    if(response.code() == 200){
                        Log.i("i", "Success : here to message \n "+response.body());
                    }else{
                        Log.i("i", "Failed : here to message \n "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<List_wash>> call, Throwable t) {

                }
            });*/

        });

        addList();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}