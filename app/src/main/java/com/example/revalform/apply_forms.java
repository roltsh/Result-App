package com.example.revalform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class apply_forms extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.apply_forms_frag,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button revalbutton = (Button) getActivity().findViewById(
                R.id.reval);
        revalbutton.setOnClickListener((View.OnClickListener) this);

        Button recountbutton = (Button) getActivity().findViewById(
                R.id.recount);
        recountbutton.setOnClickListener((View.OnClickListener) this);

        Button challengebutton = (Button) getActivity().findViewById(
                R.id.challenge);
        challengebutton.setOnClickListener((View.OnClickListener) this);

        Button photocopybutton = (Button) getActivity().findViewById(
                R.id.photo_copy);
        photocopybutton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.reval:
                Intent i=new Intent(getActivity(), ScrollingActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;

            case R.id.recount:
                Intent j=new Intent(getActivity(), ScrollingActivity2.class);
                startActivity(j);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;

            case R.id.challenge:
                Intent k=new Intent(getActivity(), ScrollingActivity3.class);
                startActivity(k);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;

            case R.id.photo_copy:
                Intent l=new Intent(getActivity(), ScrollingActivity4.class);
                startActivity(l);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;
        }

    }


}
