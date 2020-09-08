package com.hackathon.wash_p.ui.fragments.step;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.ui.adapters.way.RecyclerAdapter2;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import java.util.ArrayList;
import java.util.List;


public class DirectionFragment extends Fragment {
    private Viewmodel_fragment fg;
    private List_wash current_Data;
    private List<List_wash> list_washList;
    private RecyclerView recyclerView;
    private RecyclerAdapter2 adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void addList(){
        list_washList  = new ArrayList<>();

        list_washList.add(new List_wash(null, null, null, null, false,
                null, null, current_Data.getFloor(),
                null, "왼쪽"));

        list_washList.add(new List_wash(null, null, null, null, false,
                null, null, current_Data.getFloor(),
                null, "오른쪽"));

        adapter.setData(list_washList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_direction, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);

        current_Data = fg.getWash().getValue();

        adapter = new RecyclerAdapter2((position) -> {

            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_directionFragment_to_washingNumFragment);

            if(list_washList.get(position).getWay().contains("왼쪽"))
                current_Data.setWay("left");
            else if(list_washList.get(position).getWay().contains("오른쪽"))
                current_Data.setWay("right");

        }, getActivity());





        recyclerView = view.findViewById(R.id.recyclerView_direc);

        addList();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}