package com.example.nurserystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Users;

public class login extends AppCompatActivity {
    private EditText number, passd;
    private Button loginbutton;
    private ProgressDialog loading;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbutton =(Button) findViewById(R.id.loginbutton);
        passd=(EditText) findViewById(R.id.passd);
        number=(EditText) findViewById(R.id.number);
        loading=new ProgressDialog(this);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();
            }

            private void loginuser() {
                String pno= number.getText().toString();
                String pass=passd.getText().toString();
                 if(TextUtils.isEmpty(pno))
                {
                    Toast.makeText(login.this, "Please Enter your name...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(login.this, "Please type your phone number...", Toast.LENGTH_SHORT).show();
                }
                else
                 {
                     loading.setTitle("Login Account");
                     loading.setMessage("Checking credentials");
                     loading.setCanceledOnTouchOutside(false);
                     loading.show();

                     AllowAccessToAccount(pno, pass);
                     
                 }
            }

            private void AllowAccessToAccount(String pno, String pass) {
                final DatabaseReference Rootref;
                Rootref= FirebaseDatabase.getInstance().getReference();
                Rootref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(parentDbName). child(pno).exists())
                        {
                            Users users=dataSnapshot.child(parentDbName).child(pno).getValue(Users.class);
                                    if(users.getPhone().equals((pno)))
                                    {
                                        if(users.getPassword().equals(pass))
                                        {
                                            Toast.makeText(login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            loading.dismiss();

                                            Intent intent = new Intent(login.this, home.class);
                                            startActivity(intent);
                                        }
                                    }

                        }
                        else
                        {
                            Toast.makeText(login.this, "Account with this " +pno +" do not exists", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}