package com.example.revalform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class view_results extends Fragment {

    public static final String EXTRA_NAME = "com.example.revalform.NAME";
    public static final String EXTRA_USN = "com.example.revalform.USN";
    private String usnval;
    private String semyo;


    Spinner semsdropdown,depdropdown;
    Button sub;
    //String usnval;
    EditText usn;
    String usnMatch="1BM\\d{2}\\w{2}\\d{3}";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.view_results_frag,container,false);
        semsdropdown=rootview.findViewById(R.id.sem);
        depdropdown=rootview.findViewById(R.id.dep);
        initspinnerfooter();
        sub =rootview.findViewById(R.id.submit);
        usn =rootview.findViewById(R.id.usn);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usnval = usn.getText().toString();
                semyo = semsdropdown.getSelectedItem().toString();
               // Toast.makeText(getActivity(), usnval, Toast.LENGTH_SHORT).show();
                if (!usnval.matches(usnMatch) && (usnval!="")) {
                    Toast.makeText(getActivity(), "Enter Proper Usn", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference collRef = db.collection("CSE").document("sem").collection(semyo);
                    DocumentReference docRef = collRef.document(usnval);
                    docRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        //Intent intent = new Intent(this, DisplayMessageActivity.class);
                                        Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
                                        intent.putExtra(EXTRA_NAME, semyo);
                                        intent.putExtra(EXTRA_USN, usnval);
                                        startActivity(intent);
                                        //Toast.makeText(getActivity(), semyo, Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                                    Log.d("view_results", e.toString());
                                }
                            });

                }
            }
        });
        return rootview;
    }
    private void initspinnerfooter(){
        String[] semitems =new String[]{
                "one","two","three","four","five","six","seven","eight"};
        String[] depitems =new String[]{
                "CSE","ISE","EEE","ME","ECE","CVE","TE","CE","BIO"};
        ArrayAdapter<String> semadapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,semitems);
        semsdropdown.setAdapter(semadapter);
        semsdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        ArrayAdapter<String> depadapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,depitems);
        depdropdown.setAdapter(depadapter);
        depdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        }
    }

