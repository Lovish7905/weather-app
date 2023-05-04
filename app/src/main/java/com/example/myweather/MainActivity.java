package com.example.myweather;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    EditText name,mail,password;
    Button register;
    TextView loginpage;
    ImageView bg;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.editTextTextEmailAddress);
        mail=(EditText) findViewById(R.id.editTextTextEmailAddress2);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        register=(Button) findViewById(R.id.button);
        loginpage=(TextView) findViewById(R.id.textView2);
        fauth=FirebaseAuth.getInstance();
        bg=(ImageView) findViewById(R.id.imageView2);

        Picasso.get().load("https://images.unsplash.com/photo-1571254653469-acd926b30a83?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=435&q=80").into(bg);
        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }
loginpage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,MainActivity2.class));
    }
});
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mail.getText().toString().trim();
                String pass=password.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mail.setError("Enter Valid Email ID ");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Enter Valid password ");
                    return;
                }
                if(pass.length()<=6){
                    password.setError("Password Should Be Greater Than 6 Digits");
                }
                fauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(MainActivity.this,"Successfully created",Toast.LENGTH_LONG).show();
                       startActivity(new Intent(MainActivity.this,MainActivity2.class));
                   }
                   else{
                       Toast.makeText(MainActivity.this,"Some Error Occured",Toast.LENGTH_LONG).show();
                   }
                    }
                });
            }
        });
    }
}