 package com.appsnipp.services;


import android.media.effect.Effect;
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

 public class Register extends AppCompatActivity {

     //create database reference class to access the Realtime no schema db firbase.
     DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl
             ("https://mrfixit-f3da1-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         final EditText username=findViewById(R.id.editTextName);
         final EditText userphone=findViewById(R.id.editTextPhone);
         final EditText userid=findViewById(R.id.editTextId);
         final EditText userpass=findViewById(R.id.editTextPassword);
         final EditText confirmuserpass=findViewById(R.id.editTextPassword2);
         final ImageView regi=findViewById(R.id.buttonRegister);
         final TextView loginnowbutton=findViewById(R.id.textloginnow);

         regi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 //get user data from the edittexts to the String Variables.
                 final String fullusername=username.getText().toString();
                 final String newuserphone=userphone.getText().toString();
                 final String Id=userid.getText().toString();
                 final String userpassword=userpass.getText().toString();
                 final String ConfirmthatPasswordifitsshit=confirmuserpass.getText().toString();

                 if (fullusername.isEmpty()||newuserphone.isEmpty()||Id.isEmpty()||userpassword.isEmpty()||ConfirmthatPasswordifitsshit.isEmpty()
                 ){
                     Toast.makeText(Register.this,"Please fill all the blanks provided",Toast.LENGTH_LONG).show();
                 }
                 else if (!userpassword.equals(ConfirmthatPasswordifitsshit)){
                     Toast.makeText(Register.this,"Password haimatch",Toast.LENGTH_SHORT).show();
                 }
                 //send that data to the database using this.we gonna use id as the unique identifier.
else {
    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            //check if id not registered twice validate
            if (snapshot.hasChild(Id)){
                Toast.makeText(Register.this,"User Id already Registered",Toast.LENGTH_SHORT).show();
            }
else{

                databaseReference.child("users").child(Id).child("username").setValue(fullusername);
                databaseReference.child("users").child(Id).child("userphone").setValue(newuserphone);
                databaseReference.child("users").child(Id).child("userid").setValue(Id);
                databaseReference.child("users").child(Id).child("userpass").setValue(userpassword);

                Toast.makeText(Register.this, "User Rgistered Successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


                 }
             }
         });

         loginnowbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }
}