package com.hackathon.wash_p.ui.activites.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.request.apply.Apply_wash;
import com.hackathon.wash_p.data.response.list.List_wash;
import com.hackathon.wash_p.data.response.result.Data_result;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.activites.intro.IntroActivity;
import com.hackathon.wash_p.viewmodel.Viewmodel_fragment;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Spinner s,s2,s3,s4;
    BoomMenuButton bmb;
    List<List_wash> list_washes;
    private Viewmodel_fragment fg;
    String[] arr_spin_floor = {"3","4","5"};
    String[] arr_spin_direction = {"left","right"};
    String[] arr_spin_washernum = {"1","2","3"};
    String[] arr_spin_time = {"1","2"};
    private Call<Data_result> results;
    private static final String KEY = "KEY_STUDENT";
    private ArrayList<String> string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();

        ab.setTitle("WasherManager");

        startActivity(new Intent(getApplicationContext(), IntroActivity.class));

        bmb = findViewById(R.id.bmb);

        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_2);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_2);


            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.washing_icon)
                    .imagePadding(new Rect(20,20,20,20))
                    .normalText("신청")
                    .subNormalText("세탁기 이용을 신청 할 수 있습니다.")
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {

                            View dialogView_b = getLayoutInflater().inflate(R.layout.dialog_apply, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                    .setView(dialogView_b)
                                    .setCancelable(false);
                            string = getStringArrayPref(getApplicationContext(), KEY);
                            AlertDialog dialog = builder.create();
                            TextView textView_name = dialogView_b.findViewById(R.id.names);

                            textView_name.setText(string.get(0)+"학년 "+string.get(1) + "반 "+string.get(2)+"번" + " "+ "이름 : " +string.get(3));

                            setUpSpinner(dialogView_b);

                            list_washes = new ArrayList<>();


                            dialogView_b.findViewById(R.id.confirm_post).setOnClickListener(view1 -> {


                                /*request = Server.getInstance().getApi().getData();

                                request.enqueue(new Callback<List<List_wash>>() {
                                    @Override
                                    public void onResponse(Call<List<List_wash>> call, Response<List<List_wash>> response) {
                                        if(response.code() == 200){
                                            Log.i("i", "Success : here to message \n "+response.body());
                                            list_washes = response.body();
                                        }else{
                                            Log.i("i", "Failed : here to message \n "+response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<List_wash>> call, Throwable t) {

                                    }
                                });
                                //2020.09.08 23:29 end - 중복 체크 마무리 해야함.*/

                                SimpleDateFormat dateSet = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date_start = dateSet.format(new Date(System.currentTimeMillis()));
                                String date_end = s4.getSelectedItem().toString().equals("1") ? dateSet.format(new Date(System.currentTimeMillis() + 3600000)) : dateSet.format(new Date(System.currentTimeMillis() + 7200000));

                                fg = ViewModelProviders.of(MainActivity.this).get(Viewmodel_fragment.class);


                                Apply_wash apply_wash = new Apply_wash(s.getSelectedItem().toString(),s3.getSelectedItem().toString(),s2.getSelectedItem().toString(),string.get(0),string.get(1),string.get(2),string.get(3),true,date_start,date_end);
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
                                dialog.dismiss();
                            });

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.show();

                        }
                    });

             HamButton.Builder builder2 = new HamButton.Builder()
                    .normalImageRes(R.drawable.washing_icon)
                     .imagePadding(new Rect(20,20,20,20))
                    .normalText("삭제")
                    .subNormalText("세탁기 이용 내역을 삭제 할 수 있습니다.");

                    bmb.addBuilder(builder);
                    bmb.addBuilder(builder2);
        }
        public void setUpSpinner(View view){

            s = (Spinner)view.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_spin_floor);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s.setAdapter(adapter);

            s2 = (Spinner)view.findViewById(R.id.spinner2);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_spin_direction);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s2.setAdapter(adapter2);

            s3 = (Spinner)view.findViewById(R.id.spinner3);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_spin_washernum);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s3.setAdapter(adapter3);

             s4 = (Spinner) view.findViewById(R.id.spinner4);
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arr_spin_time);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            s4.setAdapter(adapter4);

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