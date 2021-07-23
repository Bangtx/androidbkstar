package com.example.counttimes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.counttimes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class home extends AppCompatActivity {
    ToggleButton btnStudy, btnEat, btnExercise, btnChill, btnSleep, btnStart_Stop;
    Button btnSetD_Day, btnChart;
    TextView txtDDay, txtStart_Stop, txtTg;
    String NameD_Day;
    Calendar cal;
    int second, minute, hour;
    private static final long START_TIME_IN_MILLIS = 864000000;
    CountDownTimer countDownTimer_study, countDownTimer_eat, countDownTimer_exercise, countDownTimer_chill, countDownTimer_sleep;
    public long timerMillis_study = START_TIME_IN_MILLIS;
    public long timerMillis_eat = START_TIME_IN_MILLIS;
    public long timerMillis_chill = START_TIME_IN_MILLIS;
    public long timerMillis_sleep = START_TIME_IN_MILLIS;
    public long timerMillis_exercise = START_TIME_IN_MILLIS;
    Boolean study = false, eat = false, exercise = false, chill = false, sleep = false, start_stop = false;
    int study_tem = 0, eat_tem = 0, exercise_tem = 0, chill_tem = 0, sleep_tem = 0, start_stop_tem = 0;

    String Name;
    long ngay ;
    String tem_string;

    public static final String F = "f";
    public static final String NameFile1_sleep = "NameFile1_sleep";
    public static final String F2 = "f2";
    public static final String F3 = "f3";
    public static final String F4 = "f4";
    public static final String F5 = "f5";

    int ho = 0, mi = 0, se = 0, da = 0;

    int hour_print = 0, minute_print = 0, second_print = 0, temp = 0;

    int time1 = 0;

    DatabaseReference mdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Name = intent.getStringExtra(login.NameFile);
        mdata = FirebaseDatabase.getInstance().getReference();



        addControls();
        addEvents();


        btnSetD_Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart_Stop.setChecked(false);
                Intent intent = new Intent(home.this, Set_D_Day.class);
                intent.putExtra(F, Name);
                startActivity(intent);
            }
        });
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, chart.class);
                i.putExtra(F, Name);
                startActivity(i);
            }
        });

        getDDay();

    }

    private void addControls() {
        txtTg = findViewById(R.id.txtTg);
        btnStudy = findViewById(R.id.btnStudy);
        btnEat = findViewById(R.id.btnEat);
        btnExercise = findViewById(R.id.btnExercise);
        btnChill = findViewById(R.id.btnChill);
        btnSleep = findViewById(R.id.btnSleep);
        //txtStart_Stop = findViewById(R.id.txtStart_Stop);
        btnSetD_Day = findViewById(R.id.btnSetD_Day);
        txtDDay = findViewById(R.id.txtDDay);
        btnChart = findViewById(R.id.btnchart);
        btnStart_Stop = findViewById(R.id.btnStart_Stop);
        btnSleep.setBackgroundColor(Color.rgb(130, 211, 250));
        btnChill.setBackgroundColor(Color.rgb(130, 211, 250));
        btnStudy.setBackgroundColor(Color.rgb(130, 211, 250));
        btnEat.setBackgroundColor(Color.rgb(130, 211, 250));
        btnExercise.setBackgroundColor(Color.rgb(130, 211, 250));
        //txtDDay.setText(d);
    }

    private void addEvents() {

        getTimeAction();

        btnStudy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                study = b;
                if(b) {
                    btnStart_Stop.setChecked(false);
                    Toggle("study");
                    Log.d("1234", Name);
                }
            }
        });

        btnEat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                eat = b;
                if(b) {
                    btnStart_Stop.setChecked(false);
                    Toggle("eat");
                }
                else {
                    btnStudy.setBackgroundColor(Color.rgb(220, 220, 220));
                }
            }
        });
        btnExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                exercise = b;
                if(b) {
                    btnStart_Stop.setChecked(false);
                    Toggle("exercise");
                }
                else {
                    btnExercise.setBackgroundColor(Color.rgb(220, 220, 220));
                }
            }
        });
        btnChill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chill = b;
                if(b) {
                    btnStart_Stop.setChecked(false);
                    Toggle("chill");
                }
                else {
                    btnChill.setBackgroundColor(Color.rgb(220, 220, 220));
                }
            }
        });
        btnSleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sleep = b;
                    if(b) {
                        btnStart_Stop.setChecked(false);
                        Toggle("sleep");
                    }
                    else {
                        btnSleep.setBackgroundColor(Color.rgb(220, 220, 220));
                    }
            }
        });
        btnStart_Stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                start_stop = b;
                if(start_stop) {
                    if (study) {
                        startTime("study");
                        Log.d("1234", "study");
                    }
                    if (eat) {
                        startTime("eat");
                        Log.d("1234", "eat");
                    }
                    if (exercise) {
                        startTime("exercise");
                        Log.d("1234", "ssss");
                    }

                    if (chill) {
                        startTime("chill");
                    }

                    if (sleep) {
                        startTime("sleep");
                    }
                }
                if(!start_stop){
                    if(study_tem == 1) {
                        stopTime("study");
                    }
                    if(eat_tem == 1) {
                        stopTime("eat");
                    }
                    if(exercise_tem == 1) {
                        stopTime("exercise");
                    }

                    if(chill_tem == 1) {
                        stopTime("chill");
                    }

                    if(sleep_tem == 1) {
                        stopTime("sleep");
                    }
                }
            }
        });
    }

    private void Toggle(String s) {
        if (s.equals("study")) {
            btnEat.setChecked(false);
            btnExercise.setChecked(false);
            btnChill.setChecked(false);
            btnSleep.setChecked(false);
            btnStudy.setBackgroundColor(Color.rgb(7, 220, 232));
            btnEat.setBackgroundColor(Color.rgb(130, 211, 250));
            btnExercise.setBackgroundColor(Color.rgb(130, 211, 250));
            btnChill.setBackgroundColor(Color.rgb(130, 211, 250));
            btnSleep.setBackgroundColor(Color.rgb(130, 211, 250));
        }
        if (s.equals("eat")) {
            btnStudy.setChecked(false);
            btnExercise.setChecked(false);
            btnChill.setChecked(false);
            btnSleep.setChecked(false);
            btnEat.setBackgroundColor(Color.rgb(7, 220, 232));
            btnStudy.setBackgroundColor(Color.rgb(130, 211, 250));
            btnExercise.setBackgroundColor(Color.rgb(130, 211, 250));
            btnChill.setBackgroundColor(Color.rgb(130, 211, 250));
            btnSleep.setBackgroundColor(Color.rgb(130, 211, 250));
        }
        if (s.equals("exercise")) {
            btnEat.setChecked(false);
            btnStudy.setChecked(false);
            btnChill.setChecked(false);
            btnSleep.setChecked(false);
            btnExercise.setBackgroundColor(Color.rgb(7, 220, 232));
            btnEat.setBackgroundColor(Color.rgb(130, 211, 250));
            btnStudy.setBackgroundColor(Color.rgb(130, 211, 250));
            btnChill.setBackgroundColor(Color.rgb(130, 211, 250));
            btnSleep.setBackgroundColor(Color.rgb(130, 211, 250));
        }
        if (s.equals("chill")) {
            btnEat.setChecked(false);
            btnExercise.setChecked(false);
            btnStudy.setChecked(false);
            btnSleep.setChecked(false);
            btnChill.setBackgroundColor(Color.rgb(7, 220, 232));
            btnEat.setBackgroundColor(Color.rgb(130, 211, 250));
            btnStudy.setBackgroundColor(Color.rgb(130, 211, 250));
            btnExercise.setBackgroundColor(Color.rgb(130, 211, 250));
            btnSleep.setBackgroundColor(Color.rgb(130, 211, 250));
        }
        if (s.equals("sleep")) {
            btnEat.setChecked(false);
            btnExercise.setChecked(false);
            btnChill.setChecked(false);
            btnStudy.setChecked(false);
            btnSleep.setBackgroundColor(Color.rgb(7, 220, 232));
            btnChill.setBackgroundColor(Color.rgb(130, 211, 250));
            btnExercise.setBackgroundColor(Color.rgb(130, 211, 250));
            btnEat.setBackgroundColor(Color.rgb(130, 211, 250));
            btnStudy.setBackgroundColor(Color.rgb(130, 211, 250));
        }
    }

    private void startTime(String s) {
        if (s.equals("study")) {
            countDownTimer_study = new CountDownTimer(timerMillis_study, 1000) {
                @Override
                public void onTick(long l) {
                    timerMillis_study = l;
                    study_tem = 1;
                    action action = new action(timerMillis_study, timerMillis_eat, timerMillis_exercise, timerMillis_chill, timerMillis_sleep);
                    mdata.child(Name).setValue(action);
                    updateText("study");
                    saveData((int) timerMillis_study, "study/", "seconds.txt");
                }

                @Override
                public void onFinish() {

                }
            }.start();

        }
        if (s.equals("eat")) {
            countDownTimer_eat = new CountDownTimer(timerMillis_eat, 1000) {
                @Override
                public void onTick(long l) {
                    timerMillis_eat = l;
                    eat_tem = 1;
                    action action = new action(timerMillis_study, timerMillis_eat, timerMillis_exercise, timerMillis_chill, timerMillis_sleep);
                    mdata.child(Name).setValue(action);
                    updateText("eat");
                    Log.d("1234", "eat");
                    saveData((int) timerMillis_eat, "eat/", "seconds.txt");
                }

                @Override
                public void onFinish() {

                }
            }.start();

        }
        if (s.equals("exercise")) {
            countDownTimer_exercise = new CountDownTimer(timerMillis_exercise, 1000) {
                @Override
                public void onTick(long l) {
                    timerMillis_exercise = l;
                    exercise_tem = 1;
                    action action = new action(timerMillis_study, timerMillis_eat, timerMillis_exercise, timerMillis_chill, timerMillis_sleep);
                    mdata.child(Name).setValue(action);
                    updateText("exercise");
                    Log.d("1234", "exercise");
                    saveData((int) timerMillis_exercise, "exercise/", "seconds.txt");
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
        if (s.equals("chill")) {
            countDownTimer_chill = new CountDownTimer(timerMillis_chill, 1000) {
                @Override
                public void onTick(long l) {
                    timerMillis_chill = l;
                    chill_tem = 1;
                    action action = new action(timerMillis_study, timerMillis_eat, timerMillis_exercise, timerMillis_chill, timerMillis_sleep);
                    mdata.child(Name).setValue(action);
                    updateText("chill");
                    Log.d("1234", "chill");
                    saveData((int) timerMillis_chill, "chill/", "seconds.txt");
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
        if (s.equals("sleep")) {
            countDownTimer_sleep = new CountDownTimer(timerMillis_sleep, 1000) {
                @Override
                public void onTick(long l) {
                    timerMillis_sleep = l;
                    sleep_tem = 1;
                    action action = new action(timerMillis_study, timerMillis_eat, timerMillis_exercise, timerMillis_chill, timerMillis_sleep);
                    mdata.child(Name).setValue(action);
                    updateText("sleep");
                    Log.d("1234", "sleep");
                    saveData((int) timerMillis_sleep, "sleep/", "seconds.txt");
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }

    private void updateText(String s) {
        if(s.equals("study")) {temp = (int) timerMillis_study;}
        if(s.equals("eat")) {temp = (int) timerMillis_eat;}
        if(s.equals("chill")) {temp = (int) timerMillis_chill;}
        if(s.equals("exercise")) {temp = (int) timerMillis_exercise;}
        if(s.equals("sleep")) {temp = (int) timerMillis_sleep;}
        ho = (int) (864000 - temp/1000)/3600;
        mi = (int) (864000- temp/1000 - 3600*ho)/60;
        se = (int) (864000- temp/1000 - 3600*ho - 60*mi);
        txtTg.setText( "Time :" + String.valueOf(ho) +":" + String.valueOf(mi) +":" + String.valueOf(se));
        getDDay();
    }

    private void stopTime(String s){
        if (s.equals("study")){
            countDownTimer_study.cancel();
        }
        if (s.equals("eat")){
            countDownTimer_eat.cancel();
        }
        if (s.equals("exercise")){
            countDownTimer_exercise.cancel();
        }
        if (s.equals("chill")){
            countDownTimer_chill.cancel();
        }
        if (s.equals("sleep")){
            countDownTimer_sleep.cancel();
        }
    }
    private void stopTime2(){

        if (study_tem == 1){
            countDownTimer_study.cancel();
        }
        if (eat_tem == 1){
            countDownTimer_eat.cancel();
        }
        if (exercise_tem == 1){
            countDownTimer_exercise.cancel();
        }
        if (chill_tem == 1){
            countDownTimer_chill.cancel();
        }
        if (sleep_tem == 1){
            countDownTimer_sleep.cancel();
        }
        btnStart_Stop.setChecked(false);
    }
    private void getTimeAction(){
        File file0 = new File("/storage/emulated/0/dataApp/");
        if(!file0.exists()){
            file0.mkdir();
        }

        timerMillis_study = timerMillis_sleep = timerMillis_chill = timerMillis_eat = timerMillis_exercise = 864000000;
        File file = new File("/storage/emulated/0/dataApp/" + Name );
        if(!file.exists()){
            file.mkdir();
            saveData((int) timerMillis_study, "study/", "seconds.txt");
            saveData((int) timerMillis_eat, "eat/", "seconds.txt");
            saveData((int) timerMillis_exercise, "exercise/", "seconds.txt");
            saveData((int) timerMillis_chill, "chill/", "seconds.txt");
            saveData((int) timerMillis_sleep, "sleep/", "seconds.txt");
        }
        readData("study/", "seconds.txt");
        timerMillis_study = temp;
        readData("eat/", "seconds.txt");
        timerMillis_eat = temp;
        readData("exercise/", "seconds.txt");
        timerMillis_exercise = temp;
        readData("chill/", "seconds.txt");
        timerMillis_chill = temp;
        readData("sleep/", "seconds.txt");
        timerMillis_sleep = temp;

        //mdata.child(Name).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            //public void onDataChange(DataSnapshot snapshot) {
            //    if(snapshot.exists()) {
             //       action2 action2 = snapshot.getValue(com.example.thay_anh.action2.class);
        //    timerMillis_study = action2.study;
               //     timerMillis_sleep = action2.sleep;
               //     timerMillis_chill = action2.chill;
               //     timerMillis_eat = action2.eat;
               //     timerMillis_exercise = action2.exercise;
              //  }
              //  else {

              //  }
           // }

          //  @Override
          //  public void onCancelled(@NonNull DatabaseError error) {

         //   }
       // });
    }

    private void saveData(int data,String path, String path_txt) {
        File file0 = new File("/storage/emulated/0/dataApp/");
        if(!file0.exists()){
            file0.mkdir();
        }
        File file = new File("/storage/emulated/0/dataApp/" + Name + path);
        if(!file.exists()){
            file.mkdir();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/storage/emulated/0/dataApp/" + Name + path + path_txt);
            fileOutputStream.write((String.valueOf(data)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void readData( String path, String path_txt) {
        try {
            FileInputStream fis1 = new FileInputStream("/storage/emulated/0/dataApp/" + Name + path + path_txt);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String line1;
            while ((line1 = br1.readLine()) != null) {
                String Data = line1;
                int i = Integer.parseInt(Data);
                temp = i;
                tem_string = Data;
                Log.d("1234", tem_string);
            }

            fis1.close();
            br1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDDay(){
        File file = new File("/storage/emulated/0/dataApp/" + Name + "DDay/");
        if(file.exists()){

            readData("DDay/", "second.txt");
            ngay = temp;


            Log.d("1234", String.valueOf(ngay));
            txtDDay.setText(String.valueOf(ngay));
            cal = Calendar.getInstance();

            readNameday("Name_DDay/", "second.txt");
            NameD_Day = tem_string;

            int x1 = (int) (cal.getTimeInMillis()/1000/84600);
            int ng = (int) (ngay - x1);
            Log.d("1234", String.valueOf(ngay));
            Log.d("1234", "NameD_Day" + String.valueOf(NameD_Day));

            if(ng != 0){
                txtDDay.setText(NameD_Day + ":\n"+String.valueOf(ng) + " Days remaining");
            }
            if(ng == 0){
                timerMillis_study = timerMillis_sleep = timerMillis_chill = timerMillis_eat = timerMillis_exercise = 864000000;
                txtDDay.setText("Set DDay");
                saveData((int) 864000000, "sleep/", "seconds.txt");
                saveData((int) 864000000, "study/", "seconds.txt");
                saveData((int) 864000000, "chill/", "seconds.txt");
                saveData((int) 864000000, "eat/", "seconds.txt");
                saveData((int) 864000000, "exercise/", "seconds.txt");

            }

        }
        if(!file.exists()) {
            txtDDay.setText( " No DDays");

        }
    }

    private void readNameday( String path, String path_txt) {
        try {
            FileInputStream fis1 = new FileInputStream("/storage/emulated/0/dataApp/" + Name + path + path_txt);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis1));
            String line1;
            while ((line1 = br1.readLine()) != null) {
                String Data = line1;
                tem_string = Data;
                Log.d("1234", tem_string);
            }

            fis1.close();
            br1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}