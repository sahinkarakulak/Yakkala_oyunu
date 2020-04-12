package com.mrcaracal.yakkala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView, textView2,textView3;
    Button button;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;

    SharedPreferences sharedPreferences;

    int skor;
    int veri;
    int gelen_veri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        skor = 0;

        imageView1.setEnabled(false);
        imageView2.setEnabled(false);
        imageView3.setEnabled(false);
        imageView4.setEnabled(false);
        imageView5.setEnabled(false);
        imageView6.setEnabled(false);
        imageView7.setEnabled(false);
        imageView8.setEnabled(false);
        imageView9.setEnabled(false);

        imageArray = new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};


        sharedPreferences = this.getSharedPreferences("com.mrcaracal.yakkala", Context.MODE_PRIVATE);
        veri = sharedPreferences.getInt("vr",0);

        textView3.setText("En Yüksek Skor : "+veri);

    }

    private void init(){
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button = findViewById(R.id.button);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
    }

    public void tiklanmis(View view){

        skor++;
        textView2.setText("Skor :\n"+skor);
        gelen_veri = skor;

        //

        if (gelen_veri > veri){

            sharedPreferences.edit().putInt("vr",gelen_veri).apply();
            veri = veri+1;
            textView3.setText("En Yüksek Skor : "+veri);

        }
    }

    public void baslasin(View view){

        resim_gizele_goster();

        imageView1.setEnabled(true);
        imageView2.setEnabled(true);
        imageView3.setEnabled(true);
        imageView4.setEnabled(true);
        imageView5.setEnabled(true);
        imageView6.setEnabled(true);
        imageView7.setEnabled(true);
        imageView8.setEnabled(true);
        imageView9.setEnabled(true);

        new CountDownTimer(15000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Süre :\n"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                if(gelen_veri >= veri){
                    alert_cikart();
                }
                else {
                    Toast.makeText(MainActivity.this, "Tekrar Deneyiniz :)", Toast.LENGTH_SHORT).show();
                }

                imageView1.setEnabled(false);
                imageView2.setEnabled(false);
                imageView3.setEnabled(false);
                imageView4.setEnabled(false);
                imageView5.setEnabled(false);
                imageView6.setEnabled(false);
                imageView7.setEnabled(false);
                imageView8.setEnabled(false);
                imageView9.setEnabled(false);

                button.setEnabled(true);

                handler.removeCallbacks(runnable);

                for (ImageView image : imageArray){
                    image.setVisibility(View.VISIBLE);
                }

            }
        }.start();

        button.setEnabled(false);

    }

    public void resim_gizele_goster(){

        skor = 0;
        textView2.setText("Skor :\n"+skor);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,500);

            }
        };

        handler.post(runnable);
    }

    public void alert_cikart(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Skor");
        alert.setMessage("Tebrikler! En yüksek skoru elde ettiniz.");

        /*alert.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hayır'a tıklanması halinde yapılacaklar
            }
        });

        alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Evet'e tıklanması halinde yapılacak işlemler
            }
        });*/

        alert.show();
    }
}
