package com.example.counttimes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.counttimes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Set_D_Day extends AppCompatActivity {
    EditText txtDD_Day, txtNameOfD_Day;
    Button btnSave;
    String Name;
    int nam, thang, ngay;
    Calendar calendar = Calendar.getInstance();
    DatabaseReference mdata;
    public static final String NameFile = "NAME_FILE";
    public static final String NameFile1 = "NAME_FILE1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__d__day);

        mdata = FirebaseDatabase.getInstance().getReference();
        Intent intent2 = getIntent();
        Name = intent2.getStringExtra(home.F);
        addControls();
        addEvents();

    }

    private void addEvents() {
        txtDD_Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ngay = calendar.get(Calendar.DATE);
                thang = calendar.get(Calendar.MONTH);
                nam = calendar.get(Calendar.YEAR);
                chonngay();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nameofday = txtNameOfD_Day.getText().toString();
                String Dateofday = txtDD_Day.getText().toString();

                int x = (int) (calendar.getTimeInMillis()/1000/84600);
                saveData(x, "DDay/", "second.txt");
                saveData1(Nameofday, "Name_DDay/", "second.txt");
                mdata.child(Name).child("dday").setValue(x);
                finish();

            }
        });
    }

    private void chonngay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(i, i1, i2);
                txtDD_Day.setText(simpleDateFormat.format(calendar.getTime()));

                //.mdata.child(Name).setValue(x);
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void saveData(int data,String path, String path_txt) {
        File file0 = new File("/storage/emulated/0/dataApp/");
        if(!file0.exists()){
            file0.mkdir();
        }

        try {
            File file = new File("/storage/emulated/0/dataApp/" + Name + path);
            if(!file.exists()){
                file.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("/storage/emulated/0/dataApp/" + Name + path + path_txt);
            fileOutputStream.write((String.valueOf(data)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Log.d("1234", e.toString());

        }

    }
    private void saveData1(String data,String path, String path_txt) {
        File file0 = new File("/storage/emulated/0/dataApp/");
        if(!file0.exists()){
            file0.mkdir();
        }

        try {
            File file = new File("/storage/emulated/0/dataApp/" + Name + path);
            if(!file.exists()){
                file.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("/storage/emulated/0/dataApp/" + Name + path + path_txt);
            fileOutputStream.write((String.valueOf(data)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            Log.d("1234", e.toString());

        }

    }

    private void addControls() {
        txtNameOfD_Day = findViewById(R.id.txtNameOfD_Day);
        txtDD_Day = findViewById(R.id.txtDD_Day);
        btnSave = findViewById(R.id.btnSave);
    }
}