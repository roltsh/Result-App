package com.example.revalform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Map;

public class ScrollingActivity4 extends AppCompatActivity implements TextWatcher{
    private static final String TAG = "ScrollingActivity";
    public static List<EditText> allEdscc;
    Spinner semDropDown;


    public static List<EditText> allEdsct;
    Button submit;
    Button send;
    String amount,upi_id,note,name;
    TextView amt;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        EditText e = (EditText) findViewById(R.id.editText3);
        e.addTextChangedListener(this);



        long date = System.currentTimeMillis();
        TextView tvDisplayDate=(TextView)findViewById(R.id.textView14);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM, yyyy h:mm a");
        String dateString = sdf.format(date);
        tvDisplayDate.setText(dateString);

        semDropDown=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> semAdapter=new ArrayAdapter<String>(ScrollingActivity4.this,
                android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.sems));
        semDropDown.setAdapter(semAdapter);



        //database
        editTextUSN=(EditText)findViewById(R.id.editText);
        editTextName=(EditText)findViewById(R.id.editText2);
        //editTextSem=(EditText)findViewById(R.id.editText_sem);
        editTextNum=(EditText)findViewById(R.id.editText3);
        textTotalfees=(TextView)findViewById(R.id.textView12);
        textDate=(TextView)findViewById(R.id.textView14);

        send=(Button)findViewById(R.id.pay);
        submit=findViewById(R.id.submit);


        upi_id="roltsh@paytm";
        note="Payment for revals";
        name="COE";

        //upi payment
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                amt=(TextView)findViewById(R.id.textView12);
                amount=amt.getText().toString();
                payUsingUpi(amount, upi_id,name, note);
            }
        });
        submit.setEnabled(false);

    }

    @SuppressLint("SetTextI18n")
    public void addRows(){
        //RelativeLayout rl=findViewById(R.id.rl);
        TableLayout ll = findViewById(R.id.courses);
        EditText t1= findViewById(R.id.editText3);
        int n=Integer.parseInt(t1.getText().toString());
        allEdscc= new ArrayList<EditText>();
        allEdsct=new ArrayList<EditText>();


        int i;
        for (i = 1; i <=n; i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView t=new TextView(this);
            t.setText(Integer.toString(i));


            //EditText sem = new EditText(this);
            EditText cc = new EditText(this);
            EditText ct = new EditText(this);
            EditText grade = new EditText(this);


            cc.setId(200+i);
            ct.setId(300+i);
            grade.setId(400+i);

            allEdscc.add(cc);
            allEdsct.add(ct);

            row.addView(t);
            row.addView(cc);
            row.addView(ct);
            //row.addView(grade);

            ll.addView(row,i);

        }
        TextView total=(TextView)findViewById(R.id.textView12);
        total.setText(Integer.toString(600 * n));



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //String s=e.getText().toString();

    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {
        TableLayout ll = findViewById(R.id.courses);
        if (ll.getRootView() != null) {
            int i = 1;
            while (ll.getChildCount() != 1) {
                ll.removeViewAt(i);
            }
        }
        if(!TextUtils.isEmpty(s)){
            addRows();
        }



    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String KEY_USN = "usn";
    private static final String KEY_NAME = "name";


    private EditText editTextUSN;
    private EditText editTextName;
    private EditText editTextSem;
    private EditText editTextNum;
    private TextView textTotalfees;
    private TextView textDate;
    //String sem=editTextSem.getText().toString();
    //String sem="3";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("ResourceType")
    public void submitReval(View v) {
        String usn = editTextUSN.getText().toString();
        String name = editTextName.getText().toString();
        String sem=semDropDown.getSelectedItem().toString();
        int num=Integer.parseInt(editTextNum.getText().toString());
        int totalfees=Integer.parseInt(textTotalfees.getText().toString());
        String date=textDate.getText().toString();

        RadioGroup examtype=(RadioGroup)findViewById(R.id.exam);
        int selectedId = examtype.getCheckedRadioButtonId();
        RadioButton selexam = (RadioButton)findViewById(selectedId);
        String exam=selexam.getText().toString();


        int s = allEdscc.size();
        ArrayList<String> ccarray = new ArrayList<String>();
        ArrayList<String> ctarray = new ArrayList<String>();

        for(int j = 0; j < s; j++){

            ccarray.add(allEdscc.get(j).getText().toString());
        }

        for(int j = 0; j < s; j++){

            ctarray.add(allEdsct.get(j).getText().toString());
        }

        Map<String,String> courses=new HashMap<String, String>();
        for(int  i = 0 ; i < s ; i++ ){
            courses.put(ccarray.get(i), ctarray.get(i));
        }



        CollectionReference notebookRef = db.collection("CSEPhotocopy").document("sem").collection(sem);

        RevalData note= new RevalData(usn,name,exam,num, (HashMap<String, String>) courses,totalfees,date);

        notebookRef.document(usn).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ScrollingActivity4.this, "Form has been submitted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ScrollingActivity4.this,HomeActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ScrollingActivity4.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });;

    }

    //upi payment
    final int UPI_PAYMENT = 0;

    void payUsingUpi(String amount, String upiId,String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(ScrollingActivity4.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(ScrollingActivity4.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }


            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(ScrollingActivity4.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                submit.setEnabled(true);
                editTextNum.setEnabled(false);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(ScrollingActivity4.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ScrollingActivity4.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ScrollingActivity4.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


}

