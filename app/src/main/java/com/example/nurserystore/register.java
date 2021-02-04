package com.example.nurserystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register extends AppCompatActivity {
    private Button createaccountButton;
    private EditText name, phno, passd;
private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createaccountButton =(Button) findViewById(R.id.createaccount);
        name=(EditText) findViewById(R.id.name);
        phno=(EditText) findViewById(R.id.phno);
        passd=(EditText) findViewById(R.id.passd);
        loading=new ProgressDialog(this);
        createaccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createaccount();
            }
        });

    }

    private void createaccount() {
        String n= name.getText().toString();
        String pno= phno.getText().toString();
        String pass=passd.getText().toString();

        if(TextUtils.isEmpty(n))
        {
            Toast.makeText(this, "Please type your name...", Toast.LENGTH_SHORT).show();
    }
        else if(TextUtils.isEmpty(pno))
        {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "Please type your phone number...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Create Account");
            loading.setMessage("Checking credentials");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            Validatephone(n,pno,pass);
            
            
            
    }
    }


    private void Validatephone(String n, String pno, String pass)
    {

final DatabaseReference Rootref;
Rootref= FirebaseDatabase.getInstance().getReference();
Rootref.addListenerForSingleValueEvent ( new ValueEventListener() {

    @Override

    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        if (!(dataSnapshot.child("Users").child(pno).exists())) {
            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("phone", pno);
            userdataMap.put("password", pass);
            userdataMap.put("name", n);


            Rootref.child("Users").child(pno).updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this, "Congrats! Your account has been created.", Toast.LENGTH_SHORT).show();
                                //loading.dismiss();

                                Intent intent = new Intent(register.this, login.class);
                                startActivity(intent);

                            } else {
                                //loading.dismiss();
                                Toast.makeText(register.this, "Network error: Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(register.this, "This " + pno +" already exists!", Toast.LENGTH_SHORT).show();
            loading.dismiss();
            Toast.makeText(register.this, "Try again using another Phone Number", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(register.this, start.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
 }
}
