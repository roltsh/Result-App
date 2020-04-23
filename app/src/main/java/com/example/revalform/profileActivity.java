package com.example.revalform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profileActivity extends AppCompatActivity {

    private String  email;
    FirebaseAuth mFirebaseAuth;
    TextView nm;
    TextView usnd;
    TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mail=findViewById(R.id.emaildisp1);
        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        email = mFirebaseUser.getEmail();
        mail.setText(email);
        //Toast.makeText(profileActivity.this,email,Toast.LENGTH_SHORT).show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("email");
        DocumentReference docRef = collRef.document(email);
        //mFirebaseAuth = FirebaseAuth.getInstance();
        nm= findViewById(R.id.namedisp1);
        usnd=findViewById(R.id.usndisp1);
        // mail=view.findViewById(R.id.emaildisp);
//        nm.setText("Djr");
//        usnd.setText("Djr");
//        mail.setText("Djr");
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String  usn = documentSnapshot.getString("usn");
                            Log.d("profile_frag", " " + name+usn);

                            nm.setText(name);
                            usnd.setText(usn);
                            mail.setText(email);

                        } else {
                            Toast.makeText(profileActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(profileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("profile_frag", e.toString());
                    }
                });

    }
    @Override
    public void onBackPressed() {
        Intent inToMain = new Intent(profileActivity.this, HomeActivity.class);
        startActivity(inToMain);

    }
}
