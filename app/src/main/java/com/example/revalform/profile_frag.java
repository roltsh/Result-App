package com.example.revalform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile_frag extends Fragment {

//    private String  email;
//    FirebaseAuth mFirebaseAuth;
//    TextView nm;
//    TextView usnd;
//    TextView mail;
   // @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Intent inToMain = new Intent(getActivity(), profileActivity.class);
        startActivity(inToMain);
        //View view=inflater.inflate(R.layout.activity_profile,container,false);
//        mail=findViewById(R.id.emaildisp1);
//        mFirebaseAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        email = mFirebaseUser.getEmail();
//        mail.setText(email);
//        Toast.makeText(getActivity(),email,Toast.LENGTH_SHORT).show();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference collRef = db.collection("email");
//        DocumentReference docRef = collRef.document(email);
//        //mFirebaseAuth = FirebaseAuth.getInstance();
//        nm= findViewById(R.id.namedisp1);
//        usnd=findViewById(R.id.usndisp1);
//       // mail=view.findViewById(R.id.emaildisp);
//        nm.setText("Djr");
//        usnd.setText("Djr");
//        mail.setText("Djr");
//        docRef.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            String name = documentSnapshot.getString("name");
//                            String  usn = documentSnapshot.getString("usn");
//                            Log.d("profile_frag", " " + name+usn);
//
//                            nm.setText(name);
//                            usnd.setText(usn);
//                            mail.setText(email);
//
//                        } else {
//                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
//                        Log.d("profile_frag", e.toString());
//                    }
//                });






        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
