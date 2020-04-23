package com.example.revalform;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;
    private String usn;
    private String name;
    public static final String TAG = "TAG";
    EditText emailId,password,textusn,textname;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@bmsce.ac.in";
    String usnMatch="1BM\\d{2}\\w{2}\\d{3}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        textusn = findViewById(R.id.usn);
        textname = findViewById(R.id.name);
        tvSignIn =findViewById(R.id.textView2);
        btnSignUp=findViewById(R.id.button3);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                email =emailId.getText().toString();
                usn =textusn.getText().toString();
                name=textname.getText().toString();
                String pwd=password.getText().toString();

                if(email.isEmpty())
                {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter the password");
                    password.requestFocus();
                }
                else if(email.isEmpty()&& pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    if (!(email.matches(emailPattern))) {
                        Toast.makeText(MainActivity.this, "Only BMSCE Mail Allowed", Toast.LENGTH_SHORT).show();

                    }
                    else if(!usn.matches(usnMatch)){
                        Toast.makeText(MainActivity.this, "Enter Valid USN", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "SignUp UnSuccessful,Please Try Again!", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser User = mFirebaseAuth.getCurrentUser();
                                User.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(), "Varification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("tag", "onFailure: Email Not Sent" + e.getMessage());
                                    }
                                });

                                CollectionReference notebookRef = db.collection("email");
                                Map<String,String> note=new HashMap<String, String>();
                                note.put("email",email);
                                note.put("usn",usn);
                                note.put("name",name);


                                notebookRef.document(email).set(note)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                               // Toast.makeText(ScrollingActivity.this, "Form has been submitted", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "email usn name done");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                               // Toast.makeText(ScrollingActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, e.toString());
                                            }
                                        });

                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });
                }
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(MainActivity.this,"Can't go Back",Toast.LENGTH_SHORT).show();
        return;
    }
}
