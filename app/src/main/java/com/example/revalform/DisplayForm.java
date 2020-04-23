package com.example.revalform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DisplayForm extends AppCompatActivity {

    private RevalData data = null;
    private dataSample sample = null;
    String usn;
    private int i;
    TextView subj;
    TextView fee;
    TextView sl;
    TextView ftype;
    Map<String, String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_form);
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String email = mFirebaseUser.getEmail().toString();
        Log.d("DisplayForm", "FML "+email);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("email");
        DocumentReference docRef = collRef.document(email);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            usn = documentSnapshot.getString("usn");
                            Log.d("DisplayForm", "FML "+usn);
                            loaddata();

                        } else {
                            //Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                            Log.d("DisplayForm", "FML");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("profile_frag", e.toString());
                    }
                });
        //loaddata();
    }

    @Override
    public void onBackPressed() {
        Intent inToMain = new Intent(DisplayForm.this, HomeActivity.class);
        startActivity(inToMain);

    }

    private void loaddata() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("CSEReval").document("sem").collection("five");
        DocumentReference docRef = collRef.document(usn);
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            data = documentSnapshot.toObject(RevalData.class);
                            //sample= documentSnapshot.toObject(dataSample.class);
//                            String title = note.getTitle();
//                            String description = note.getDescription();
                            //String name = data.getName();
                            //usn = sample.getUsn();
                            Log.d("DisplayMessageActivity", "!fml" );

//                            subj = findViewById(R.id.subj1);
//                            subj.setText(name);
                            i = 0;


                            int[] slIDs = new int[]{R.id.sl1, R.id.sl2, R.id.sl3, R.id.sl4, R.id.sl5};
                            int[] ftypeIDs = new int[]{R.id.ftype1, R.id.ftype2, R.id.ftype3, R.id.ftype4, R.id.ftype5};
                            int[] subjIDs = new int[]{R.id.subj1, R.id.subj2, R.id.subj3, R.id.subj4, R.id.subj5};
                            int[] feeIDs = new int[]{R.id.fee1, R.id.fee2, R.id.fee3, R.id.fee4, R.id.fee5};
                            Map<String, String> courses;
                            courses = data.getCourses();

                            //Log.d("DisplayMessageActivity", courses.get("15CS5DCMAD"));


                            for (Map.Entry<String, String> entry : courses.entrySet()) {
                                subj = findViewById(subjIDs[i]);
                                subj.setText(entry.getValue());
                                sl = findViewById(slIDs[i]);
                                sl.setText(String.valueOf(i+1));

                                fee = findViewById(feeIDs[i]);
                                fee.setText("1000");
                                ftype = findViewById(ftypeIDs[i]);
                                ftype.setText("Reval");
                                if (i > 4)
                                    break;
                                i++;
                            }







                            //textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                        } else {
                            //Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            Log.d("DisplayForm", "FML");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("DisplayForm", e.toString());
                    }
                });

//        Log.d("DisplayMessageActivity", name + usn);
//        textname.setText(name);
//        textusn.setText(usn);
//        List<Map<String , Object>> subjects;
//        //subjects =sample.getSubjects();
//        String code;

    }
}
