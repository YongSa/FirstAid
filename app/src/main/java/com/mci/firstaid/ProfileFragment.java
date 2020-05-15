package com.mci.firstaid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view  = inflater.inflate(R.layout.fragment_profile, container, false);
        Button addQuestionButton = (Button) view.findViewById(R.id.addQuestionButton);
        Button resetAccount = (Button) view.findViewById(R.id.resetAccount);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        resetAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizDbHelper.getInstance(getContext()).resetDatabase();
            }
        });
        return view;
    }

    private void openDialog() {
        AddQuestionDialog dialog = new AddQuestionDialog();
        dialog.show(getFragmentManager(), "example");
    }
}
