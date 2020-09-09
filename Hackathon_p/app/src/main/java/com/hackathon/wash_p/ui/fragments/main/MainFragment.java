package com.hackathon.wash_p.ui.fragments.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.response.list.List_wash;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.adapters.floor.RecyclerAdapter;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<List_wash> list_washList;
    private Call<List<List_wash>> request;
    private Viewmodel_fragment fg;
    private static final String KEY = "KEY_STUDENT";
    private List<List_wash> results1;
    private ArrayList<String> student;

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

        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);

        adapter = new RecyclerAdapter((position) -> {

            fg.setWash(list_washList.get(position));

            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_mainFragment_to_directionFragment);

        }, getActivity());

        addList();


        student = getStringArrayPref(getContext(), KEY);

        if(student.size() == 0){
            View dialogView_c = getLayoutInflater().inflate(R.layout.dialog_name, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setView(dialogView_c)
                    .setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextInputEditText inputGrade = dialogView_c.findViewById(R.id.grade);
            TextInputEditText inputClassNum = dialogView_c.findViewById(R.id.classnum);
            TextInputEditText inputNum = dialogView_c.findViewById(R.id.num);
            TextInputEditText inputName = dialogView_c.findViewById(R.id.name);

            dialogView_c.findViewById(R.id.confirm).setOnClickListener(view1 -> {

                student.add(inputGrade.getText().toString());
                student.add(inputClassNum.getText().toString());
                student.add(inputNum.getText().toString());
                student.add(inputName.getText().toString());

                setStringArrayPref(getContext(), KEY, student);

                dialog.dismiss();

            });

            dialog.show();
        }else{
            for(int i = 0; i < student.size(); i ++){
                if(student.get(i).isEmpty() || student.get(i) == null) {
                    View dialogView_c = getLayoutInflater().inflate(R.layout.dialog_name, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setView(dialogView_c)
                            .setCancelable(false);

                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    TextInputEditText inputGrade = dialogView_c.findViewById(R.id.grade);
                    TextInputEditText inputClassNum = dialogView_c.findViewById(R.id.classnum);
                    TextInputEditText inputNum = dialogView_c.findViewById(R.id.num);
                    TextInputEditText inputName = dialogView_c.findViewById(R.id.name);

                    dialogView_c.findViewById(R.id.confirm).setOnClickListener(view1 -> {

                        student.add(inputGrade.getText().toString());
                        student.add(inputClassNum.getText().toString());
                        student.add(inputNum.getText().toString());
                        student.add(inputName.getText().toString());

                        setStringArrayPref(getContext(), KEY, student);

                        dialog.dismiss();

                    });

                    dialog.show();
                }
            }

        }





        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public List_wash list_Section_return(){

        request = Server.getInstance().getApi().getData();



        request.enqueue(new Callback<List<List_wash>>() {
            @Override
            public void onResponse(Call<List<List_wash>> call, Response<List<List_wash>> response) {
                if(response.code() == 200){
                    Log.i("i", "Success : here to message \n "+response.body());
                    results1 = response.body();
                }else{
                    Log.i("i", "Failed : here to message \n "+response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<List_wash>> call, Throwable t) {
                Log.i("i", t.getMessage());
            }
        });


        for(int idx = 0 ; idx < results1.size(); idx ++){
            if(results1.get(idx).getStudentName().equals("")
                    && results1.get(idx).getClassNum().equals("")
                    && results1.get(idx).getGrade().equals("")
                    && results1.get(idx).getStudentNum().equals("")){

            }
        }


        return null;
    }
    @Override
    public void onStop(){
        setStringArrayPref(getContext(), KEY, student);
        super.onStop();
    }
    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}