package com.example.counttimes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.counttimes.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    Button btnOk2;
    EditText txtTk1, txtMk1;
    TextView txthienthi2, txtKt;

    String s1 = "";
    String s2 = "0";
    ArrayList<String> s;

    public static final String NameFile = "NAME_FILE";

    DatabaseReference mdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mdata = FirebaseDatabase.getInstance().getReference();

        addControls();
        addEvents();
    }

    private void Write() {
        mdata.child("UserName").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                userName uuu = snapshot.getValue(userName.class);
                s1 = uuu.Name;
                s2 = uuu.Pass;
                Log.d("1234", s1 + "   " + s2);
                if(txtMk1.getText().toString().equals(s2) && txtTk1.getText().toString().equals(s1)){
                    Intent intent = new Intent(login.this, home.class);
                    intent.putExtra(NameFile, s1);
                    startActivity(intent);
                }
                else{
                    txtKt.setText("Wrong Nickname Or Password");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addEvents() {
        btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Write();
                //Log.d("1234", uuu.Pass);
            }
        });
    }

    private void addControls() {
        btnOk2 = findViewById(R.id.btnOk2);
        txtMk1 = findViewById(R.id.txtMk1);
        txtTk1 = findViewById(R.id.txtTk1);
        txthienthi2 = findViewById(R.id.txthienthi2);
        txtKt = findViewById(R.id.txtKt);
    }
}