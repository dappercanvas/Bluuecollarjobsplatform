package com.appsnipp.services;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://mrfixit-f3da1-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView registerButton=findViewById(R.id.textviewRegister);
        final EditText editTextLogid=findViewById(R.id.editTextID);
        final EditText editteXTpassword=findViewById(R.id.editTextPassword);
        final ImageView imagereg=findViewById(R.id.loginright);
        final TextView registernow=findViewById(R.id.textviewRegister);


        imagereg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Id=editTextLogid.getText().toString();
                final String Pass=editteXTpassword.getText().toString();

                if (Id.isEmpty() || Pass.isEmpty()){
                    Toast.makeText(Login.this,"Please Enter your ID NO or Password",Toast.LENGTH_LONG).show();
                }
                else{
databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        //check if user exists
        if (snapshot.hasChild(Id)){
            final String getPassword=snapshot.child(Id).child("userpass").getValue(String.class);
            if (getPassword.equals(Pass)){
                Toast.makeText(Login.this,"Welcome Back",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }
            else {
                Toast.makeText(Login.this,"Wrong Id or Password",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(Login.this,"Wrong Id or Password",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
                }
            }
        });
        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}