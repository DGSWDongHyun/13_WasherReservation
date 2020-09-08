package com.hackathon.wash_p.ui.activites.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.hackathon.wash_p.R;
import com.hackathon.wash_p.ui.activites.intro.IntroActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

public class MainActivity extends AppCompatActivity {

    BoomMenuButton bmb;
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
                    .subNormalText("세탁기 이용을 신청 할 수 있습니다.");

             HamButton.Builder builder2 = new HamButton.Builder()
                    .normalImageRes(R.drawable.washing_icon)
                     .imagePadding(new Rect(20,20,20,20))
                    .normalText("삭제")
                    .subNormalText("세탁기 이용 내역을 삭제 할 수 있습니다.");

                    bmb.addBuilder(builder);
                    bmb.addBuilder(builder2);
        }
    }