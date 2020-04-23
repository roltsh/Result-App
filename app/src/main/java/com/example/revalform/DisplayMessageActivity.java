package com.example.revalform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class DisplayMessageActivity extends AppCompatActivity {

    private String usn;
    private String name;
    private dataSample sample = null;
    TextView textname;
    TextView textusn;
    TextView sub;
    TextView cie;
    TextView see;
    TextView total;
    TextView grade;
    Map<String, Object> subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        name = intent.getStringExtra(view_results.EXTRA_NAME);
        usn = intent.getStringExtra(view_results.EXTRA_USN);


        // Capture the layout's TextView and set the string as its text
        textname = findViewById(R.id.textView6);
        textusn = findViewById(R.id.textView7);
//        textname.setText(name);
//        textusn.setText(usn);
        loadData();
    }
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private CollectionReference collRef = db.collection("CSE").document("sem").collection(name);
//    private DocumentReference docRef = db.document(usn);
//    private dataSample sample = null;
//
    private void loadData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("CSE").document("sem").collection(name);
        DocumentReference docRef = collRef.document(usn);


        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            sample = documentSnapshot.toObject(dataSample.class);

//                            String title = note.getTitle();
//                            String description = note.getDescription();
                            name = sample.getName();
                            usn = sample.getUsn();
                            Log.d("DisplayMessageActivity", name + usn);

                            textname.setText(name);
                            textusn.setText(usn);

                            int[] subIDs = new int[] {R.id.sub1,R.id.sub2,R.id.sub3,R.id.sub4,R.id.sub5,R.id.sub6};
                            int[] sIDs = new int[] {R.id.s1,R.id.s2,R.id.s3,R.id.s4,R.id.s5,R.id.s6};
                            int[] cIDs = new int[] {R.id.c1,R.id.c2,R.id.c3,R.id.c4,R.id.c5,R.id.c6};
                            int[] tIDs = new int[] {R.id.t1,R.id.t2,R.id.t3,R.id.t4,R.id.t5,R.id.t6};
                            int[] gIDs = new int[] {R.id.g1,R.id.g2,R.id.g3,R.id.g4,R.id.g5,R.id.g6};
                            List<Map<String , Object>> subjects;
                            subjects =sample.getSubjects();
                            int n = subjects.size();
                            //Map<String, Object> subject;
                            int i=0;
                            while((n!=0) && (i<6)) {
                                subject = subjects.get(i);

                                sub = findViewById(subIDs[i]);
                                sub.setText(subject.get("CODE").toString());
                                cie = findViewById(cIDs[i]);
                                cie.setText(subject.get("CIE").toString());
                                see = findViewById(sIDs[i]);
                                see.setText(subject.get("SEE").toString());
                                total = findViewById(tIDs[i]);
                                total.setText(subject.get("TOTAL").toString());
                                grade = findViewById(gIDs[i]);
                                grade.setText(subject.get("GRADE").toString());

                                n--;
                                i++;
                            }



                            //textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                        } else {
                            //Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("DisplayMessageActivity", e.toString());
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
