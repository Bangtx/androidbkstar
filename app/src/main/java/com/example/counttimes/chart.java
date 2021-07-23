package com.example.counttimes;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.counttimes.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class chart extends AppCompatActivity {
    BarChart barChart;

    TextView txtstu, txteat, txtexe, txtchi, txtsle;

    int temp;
    String temp_String, Name;
    float time_study;
    float time_eat;
    float time_exercise;
    float time_chill;
    float time_sleep;
    float stu, eat, exe, chi, sle;
    String study_string = "study/";
    String eat_string = "eat/";
    String exercise_string = "exercise/";
    String chill_string = "chill/";
    String sleep_string = "sleep/";
    String second_string = "seconds.txt";
    CountDownTimer countDownTimer;
    int timerMillis = 100000000;
    int x1, x2, x3, x4, x5;
    ArrayList<BarEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        addControls();
        Intent intent = getIntent();
        Name = intent.getStringExtra(home.F);
        temp = 864000;
        readData(study_string, second_string);
        time_study =(float) 864000 - temp/1000;

        temp = 864000;
        readData(eat_string, second_string);
        time_eat =(float) 864000 -  temp/1000;

        temp = 864000;
        readData(exercise_string, second_string);
        time_exercise =(float) 864000 -  temp/1000;

        temp = 864000;
        readData(chill_string, second_string);
        time_chill =(float) 864000 -  temp/1000;

        temp = 864000;
        readData(sleep_string, second_string);
        time_sleep =(float) 864000 -  temp/1000;

        stu = time_study/(time_study + time_eat + time_exercise + time_chill + time_sleep)*100;
        eat = time_eat/(time_study + time_eat + time_exercise + time_chill + time_sleep)*100;
        exe = time_exercise/(time_study + time_eat + time_exercise + time_chill + time_sleep)*100;
        chi = time_chill/(time_study + time_eat + time_exercise + time_chill + time_sleep)*100;
        sle = time_sleep/(time_study + time_eat + time_exercise + time_chill + time_sleep)*100;

        entries.add(new BarEntry(1,stu));
        entries.add(new BarEntry(2,eat));
        entries.add(new BarEntry(3,exe));
        entries.add(new BarEntry(4,chi));
        entries.add(new BarEntry(5,sle));


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("study");
        labels.add("eat");
        labels.add("exercise");
        labels.add("chill");
        labels.add("sleep");


        BarDataSet barDataSet = new BarDataSet(entries, "Time (%)");
        barDataSet.setValueTextSize(16f);


        BarData barData = new BarData( barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(labels.size());

        hienthitxt((int) time_study, txtstu, "Study");
        hienthitxt((int) time_eat, txteat, "Eat/cook");
        hienthitxt((int) time_exercise, txtexe, " exercise");
        hienthitxt((int) time_chill, txtchi, "Chill");
        hienthitxt((int) time_sleep, txtsle, "Sleep");

    }

    private void hienthitxt(int s, TextView tv, String as) {
        int hour, minute, second;
        hour = s/3600;
        minute = (s - 3600*hour)/60;
        second = s - 3600*hour - 60*minute;
        tv.setText(String.format("%s :%dh %dm %ds",as , hour, minute, second));
    }


    private void addControls() {
        barChart = findViewById(R.id.barChart);
        txtstu = findViewById(R.id.txtstu);
        txteat = findViewById(R.id.txteat);
        txtexe = findViewById(R.id.txtexe);
        txtchi = findViewById(R.id.txtchi);
        txtsle = findViewById(R.id.txtsle);
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
                temp_String = Data;
            }

            fis1.close();
            br1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}