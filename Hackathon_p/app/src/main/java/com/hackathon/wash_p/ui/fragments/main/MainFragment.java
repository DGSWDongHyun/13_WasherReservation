package com.hackathon.wash_p.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.List_wash;
import com.hackathon.wash_p.ui.activites.main.MainActivity;
import com.hackathon.wash_p.ui.adapters.floor.RecyclerAdapter;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MainFragment extends Fragment {
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<List_wash> list_washList;
    private Call<List<List_wash>> request;
    private Viewmodel_fragment fg;
    private View viewing;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide));
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }
    public void addList(){
        list_washList  = new ArrayList<>();

        list_washList.add(new List_wash(null, null, null, "", false,
                null, null, "3F",
                null, null));

        list_washList.add(new List_wash(null, null, null, "", false,
                null, null, "4F",
                null, null));

        list_washList.add(new List_wash(null, null, null, "", false,
                null, null, "5F",
                null, null));

        adapter.setData(list_washList);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity)getActivity()).view(view , "main");
        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);

        adapter = new RecyclerAdapter((position) -> {

            fg.setWash(list_washList.get(position));

            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_mainFragment_to_directionFragment);

        }, getActivity());

        addList();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}