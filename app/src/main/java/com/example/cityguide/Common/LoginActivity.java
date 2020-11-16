package com.example.cityguide.Common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cityguide.Common.LoginSinup.SignUp;
import com.example.cityguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    
    private EditText email;
    private EditText password;
    private Button login;
    private TextView callSignUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        login = findViewById(R.id.loginButton);
        callSignUp = findViewById(R.id.newUser);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                loginUser(txtEmail,txtPassword);
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String e,String p)
    {
        String val = email.getText().toString();
        String val2 = password.getText().toString();
        if(val.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if(val2.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, UserDashboard.class));
                }else{
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
//        auth.signInWithEmailAndPassword(e,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this, UserDashboard.class));
//                finish();
//            }
//        });
    }
}