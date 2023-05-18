package com.wam.login_reg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText memail, mpassword;
    TextView mcreatBTn;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail =findViewById(R.id.Email);
        mpassword =findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn =findViewById(R.id.loginBtn);
        mcreatBTn = findViewById(R.id.creatText);


        mcreatBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                System.out.println(email);
                System.out.println(password);

//                if(TextUtils.isEmpty(email))
//                {
//                    memail.setError("Email is required");
//                    return;
//                }
//                if(TextUtils.isEmpty(password))
//                {
//                    mpassword.setError("Password is required");
//                    return;
//                }
//
//                if (password.length()<6){
//                    mpassword.setError("Password must be >= 6 charcter");
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//
//                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful())
//                        {
//
//                            Toast.makeText(getApplicationContext(), "Log in succesful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
////                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(), "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
            }
        });

    }
}