package com.example.counttimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.counttimes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;

public class sign_up extends AppCompatActivity {
    EditText txtUserName, txtPassWord, txtVerify;
    TextView txthienthi;
    Button btnOK;

    String user = "/storage/emulated/0/Download/User.txt";
    String pass = "/storage/emulated/0/Download/Pass.txt";

    String s1 = "";
    String s2 = "";
    String s3 = "0";

    DatabaseReference mdata;


    public sign_up() throws FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mdata = FirebaseDatabase.getInstance().getReference();

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Save();
            //Write();
            }
        });
    }


    private void Save() {
        s1 = txtUserName.getText().toString();
        s2 = txtPassWord.getText().toString();
        s3 = txtVerify.getText().toString();

        if (s2.equals(s3)) {
            userName userName = new userName(s1, s2);
            mdata.child("UserName").push().setValue(userName);
            txthienthi.setText("Account successfully created");
            Intent intent = new Intent(sign_up.this, login.class);
            startActivity(intent);
        }
        if (!s2.equals(s3)) {
            txthienthi.setText("wrong password");
        }
    }


    private void addControls() {
        btnOK = findViewById(R.id.btnOk);
        txtPassWord = findViewById(R.id.txtPassWord);
        txtUserName = findViewById(R.id.txtUserName);
        txthienthi = findViewById(R.id.txthienthi);
        txtVerify = findViewById(R.id.txtVerify);
    }
}