package com.hackathon.wash_p.ui.activites.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.data.request.Apply_wash;
import com.hackathon.wash_p.data.response.result.Data_result;
import com.hackathon.wash_p.network.Server;
import com.hackathon.wash_p.ui.activites.intro.IntroActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    View view;
    String view_name;
    Spinner s,s2,s3,s4;
    BoomMenuButton bmb;
    String[] arr_spin_floor = {"3","4","5"};
    String[] arr_spin_direction = {"left","right"};
    String[] arr_spin_washernum = {"1","2","3"};
    String[] arr_spin_time = {"1","2"};
    private Call<Data_result> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();

        ab.setTitle("세탁기를 선택하세요.");

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

                            AlertDialog dialog = builder.create();

                            setUpSpinner(dialogView_b);


                            dialogView_b.findViewById(R.id.confirm_post).setOnClickListener(view1 -> {

                                SimpleDateFormat dateSet = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date_start = dateSet.format(new Date(System.currentTimeMillis()));
                                String date_end = s4.getSelectedItem().toString().equals("1") ? dateSet.format(new Date(System.currentTimeMillis() + 3600000)) : dateSet.format(new Date(System.currentTimeMillis() + 7200000));

                                Apply_wash apply_wash = new Apply_wash(s.getSelectedItem().toString(),s3.getSelectedItem().toString(),s2.getSelectedItem().toString(),"1","3","12","양동현",true,date_start,date_end);
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
        public void view(View view, String view_name){
            this.view = view;
            this.view_name = view_name;
        }
    }