package com.example.myweather;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
public class MainActivity2 extends AppCompatActivity {
    TextView registerpage;
    EditText mail,password;
    Button login;
    ProgressBar pg;
    ImageView bg;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        registerpage=(TextView) findViewById(R.id.textView4);
        bg=(ImageView) findViewById(R.id.imageView);
        Picasso.get().load("https://images.unsplash.com/photo-1571254653469-acd926b30a83?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=435&q=80").into(bg);
        mail=(EditText) findViewById(R.id.editTextTextEmailAddress3) ;
        password=(EditText) findViewById(R.id.editTextTextPassword2);
        login=(Button) findViewById(R.id.button2);
        pg=(ProgressBar) findViewById(R.id.progressBar);
        fauth=FirebaseAuth.getInstance();
        registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        pg.setVisibility(View.VISIBLE);
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
            password.setError("Invalid Password");
        }
        fauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity2.this,"Successfully Logged In",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity2.this,HomePage.class));
                }
                else{
                    Toast.makeText(MainActivity2.this,"Some Error Occured",Toast.LENGTH_LONG).show();
                    pg.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
});}}
