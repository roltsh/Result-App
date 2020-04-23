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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth mFirebaseAuth;
    TextView verifymsg;
    Button btnlogout,resendCode;
    BottomNavigationView bottomNavigationView;
    NavigationView sideNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView hn,ge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        resendCode =findViewById(R.id.verifybut);
        mFirebaseAuth =FirebaseAuth.getInstance();
        verifymsg =findViewById(R.id.Email_verify);
        final FirebaseUser user= mFirebaseAuth.getCurrentUser();
//        if(!user.isEmailVerified()){
//            resendCode.setVisibility(View.VISIBLE);
//            verifymsg.setVisibility(View.VISIBLE);
//
//            resendCode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//
//                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(v.getContext(), "Varification Email Has Been Sent", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("tag","onFailure: Email Not Sent"+e.getMessage());
//                        }
//                    });
//                }
//            });
//            Intent inToMain=new Intent(HomeActivity.this, HomeverifyActivity.class);
//            startActivity(inToMain);
//        }
        btnlogout = findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getInstance().signOut();
                Intent inToMain=new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(inToMain);
            }
        });
        sideNavigationView = findViewById(R.id.side_nav);

        sideNavigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView =findViewById(R.id.bottom_nav);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatainer,new view_results()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;

                switch (item.getItemId()){
                    case R.id.nav_view:
                        fragment = new view_results();
                        break;
                    case R.id.nav_forum:
                        fragment = new apply_forms();
                        break;
                    case R.id.nav_list:
                        fragment = new applied_forms();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conatainer,fragment).commit();

                return true;
            }
        });


    }
    @Override
    public void onBackPressed(){
        Toast.makeText(HomeActivity.this,"Can't go Back",Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_profile:
                fragmentManager = getSupportFragmentManager();
              fragmentTransaction=fragmentManager.beginTransaction();
              fragmentTransaction.add(R.id.fragment_conatainer,new profile_frag()).commit();
                break;
             case  R.id.nav_info:
                 fragmentManager = getSupportFragmentManager();
                 fragmentTransaction=fragmentManager.beginTransaction();
                 fragmentTransaction.add(R.id.fragment_conatainer,new aboutus_frag()).commit();
                 break;
            case  R.id.nav_feedback:
                 fragmentManager = getSupportFragmentManager();
                 fragmentTransaction=fragmentManager.beginTransaction();
                 fragmentTransaction.add(R.id.fragment_conatainer,new help_frag()).commit();
                 break;
            case  R.id.nav_contact:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_conatainer,new contactus_frag()).commit();
                break;
        }

        return true;
    }
}
