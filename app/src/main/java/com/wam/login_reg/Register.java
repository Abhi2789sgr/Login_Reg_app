package com.wam.login_reg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG ="TAG";
    EditText mfullname, memail, mpassword, mphone;
    Button mregisterbtn;
    TextView mloginbtn;
    ProgressBar progressBar;

    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfullname =findViewById(R.id.fullname);
        memail =findViewById(R.id.email);
        mpassword =findViewById(R.id.password);
        mphone =findViewById(R.id.phone);

        mregisterbtn =findViewById(R.id.register);
        mloginbtn=findViewById(R.id.creatText);
        progressBar=findViewById(R.id.progressbar);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email =memail.getText().toString().trim();
                final String password =mpassword.getText().toString().trim();
                final String fullname = mfullname.getText().toString();
                final String phone = mphone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    memail.setError("email is require");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("password is require");
                    return;
                }
                if (password.length() < 6){
                    mpassword.setError("must be less than 6 character");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            FirebaseUser fuser =fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Register Succesful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "OnFaliure: email is not sent"+ e.getMessage());
                                }
                            });
                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =fstore.collection("user").document(userID);
                            Map<String, String> user = new HashMap<>();
                            user.put("fName",fullname);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onsucess: user progile is created for" + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFaliure:" + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(Register.this, "error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}