package com.example.revalform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeverifyActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    TextView verifymsg;
    Button btnlogout,resendCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeverify);
        resendCode =findViewById(R.id.verifybut);
        mFirebaseAuth = FirebaseAuth.getInstance();
        verifymsg =findViewById(R.id.Email_verify);
        final FirebaseUser user= mFirebaseAuth.getCurrentUser();
        if(!user.isEmailVerified()){
            resendCode.setVisibility(View.VISIBLE);
            verifymsg.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Varification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","onFailure: Email Not Sent"+e.getMessage());
                        }
                    });
                }
            });

        }
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getInstance().signOut();
                Intent inToMain=new Intent(HomeverifyActivity.this, LoginActivity.class);
                startActivity(inToMain);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(HomeverifyActivity.this,"Can't go Back",Toast.LENGTH_SHORT).show();
        return;
    }
}
