package com.hackathon.wash_p.ui.fragments.step;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.request.apply.Apply_wash;
import com.hackathon.wash_p.data.response.list.List_wash;
import com.hackathon.wash_p.data.response.result.Data_result;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.adapters.washer.RecyclerAdapter3;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WashingNumFragment extends Fragment {
    private List<List_wash> list_washList;
    private RecyclerView recyclerView;
    private RecyclerAdapter3 adapter;
    private Viewmodel_fragment fg;
    private List_wash current_Data;
    private Call<Data_result> results;
    private Call<List<List_wash>> request;
    private Response<List<List_wash>> responses;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide));
        setExitTransition(inflater.inflateTransition(R.transition.fade));
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
                    responses = response;
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
        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);
        current_Data = fg.getWash().getValue();

        adapter = new RecyclerAdapter3((position) -> {
          View dialogView = getLayoutInflater().inflate(R.layout.dialog_more, null);

           current_Data.setWasherNum(list_washList.get(position).getWasherNum());

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setView(dialogView)
                    .setCancelable(false);


            AlertDialog dialogs = builder.create();

            dialogView.findViewById(R.id.confirm).setOnClickListener(view1 -> {
                dialogs.dismiss();
            });
            aboutWashing(list_washList, dialogView, position);

            dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogs.show();

        }, responses, getContext());

        addList();

        recyclerView = view.findViewById(R.id.recyclerView_wash);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    public void aboutWashing(List<List_wash> list, View dialogView, int position){
        for(int idx = 0; idx < list.size(); idx++) {
            if(fg.getWash().getValue().getFloor().contains(list.get(idx).getFloor())
                    && fg.getWash().getValue().getWay().contains(list.get(idx).getWay())
                    && fg.getWash().getValue().getWasherNum().contains(list.get(idx).getWasherNum())){

                TextView textView_title = (TextView)dialogView.findViewById(R.id.title);
                TextView textView_usingWho = dialogView.findViewById(R.id.name);
                TextView textView_usingNow = dialogView.findViewById(R.id.using);
                TextView textView_leftTime = dialogView.findViewById(R.id.leftTime);


                SimpleDateFormat dateSet = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");

                SimpleDateFormat dateSet_result = new SimpleDateFormat("HH시간 mm분");



                if(list.get(idx).getWashEndTime() != null && list.get(idx).getWashStartTime() != null){

                    String date_start = dateSet.format(new Date(System.currentTimeMillis()));
                    String date_End = dateSet.format(list.get(idx).getWashEndTime().getTime());

                    try {
                        Date StartingWash = dateSet.parse(date_start);
                        Date EndWash = dateSet.parse(date_End);


                        long diff = (EndWash.getTime()) - StartingWash.getTime();


                        if( diff < 0 ) {
                            SimpleDateFormat dateSet2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date_start2 = dateSet2.format(System.currentTimeMillis());
                            String date_end2 = dateSet2.format(System.currentTimeMillis() - 60000);

                            textView_usingWho.setTextColor(getResources().getColor(R.color.Green));
                            textView_usingWho.setText("사용 중인 사람이 없습니다.");

                            textView_leftTime.setTextColor(getResources().getColor(R.color.Green));
                            textView_leftTime.setText("사용 중인 사람이 없습니다.");

                            Apply_wash apply_wash = new Apply_wash(list_washList.get(position).getFloor(),list_washList.get(position).getWasherNum(),list_washList.get(position).getWay(),null,null,null,null,false ,date_start2,date_end2);
                            results = Server.getInstance().getApi().putData(apply_wash);



                            results.enqueue(new Callback<Data_result>() {
                                @Override
                                public void onResponse(Call<Data_result> call, Response<Data_result> response) {
                                    if(response.code() == 200){
                                        Log.i("i", "Success : here to message \n "+response.body());
                                    }else{
                                        Log.i("i", "Failed : here to message \n "+response.message() + " " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Data_result> call, Throwable t) {
                                    Log.i("i", t.getMessage());
                                }
                            });

                            if(list.get(idx).getWashStartTime() != null && list.get(idx).getWashEndTime() != null){
                                textView_leftTime.setTextColor(getResources().getColor(R.color.Green));
                                textView_leftTime.setText("사용 중인 사람이 없습니다.");
                            }
                        }else{
                            String result = dateSet_result.format(diff);

                            textView_leftTime.setTextColor(getResources().getColor(R.color.Red));
                            textView_leftTime.setText("남은 시간 : "+result);
                        }

                    }catch (ParseException e){
                        e.getMessage();
                    }
                }else{
                    textView_leftTime.setTextColor(getResources().getColor(R.color.Green));
                    textView_leftTime.setText("사용 중인 사람이 없습니다.");
                }


                if(list.get(idx).getStudentName() == null || list.get(idx).getStudentName().isEmpty() || list.get(idx).getCheckWasher() == false) {
                    textView_usingWho.setTextColor(getResources().getColor(R.color.Green));
                    textView_usingWho.setText("사용 중인 사람이 없습니다.");
                }else{
                    textView_usingWho.setTextColor(getResources().getColor(R.color.Red));
                    textView_usingWho.setText("사용 중인 사람 이름 : " + list.get(idx).getStudentName());
                }
                textView_title.setText(list.get(idx).getWasherNum() + "번 세탁기");


                if(list.get(idx).getCheckWasher()){
                    textView_usingNow.setTextColor(getResources().getColor(R.color.Red));
                    textView_usingNow.setText("현재 사용 유 / 무 : 사용 불가능");
                }else{
                    textView_usingNow.setTextColor(getResources().getColor(R.color.Green));
                    textView_usingNow.setText("현재 사용 유 / 무 : 사용 가능");
                }

            }
        }
    }
    public void sectionArray(Response<List<List_wash>> response){
        List<List_wash> list_Section = new ArrayList<>();
        fg = ViewModelProviders.of(getActivity()).get(Viewmodel_fragment.class);
        for(int i = 0 ; i < response.body().size(); i ++){
            if(fg.getWash().getValue().getFloor().contains(response.body().get(i).getFloor()) && fg.getWash().getValue().getWay().contains(response.body().get(i).getWay())){
                list_Section.add(response.body().get(i));
            }
        }
        list_washList = list_Section;
        adapter.updateData(list_Section);
    }
}