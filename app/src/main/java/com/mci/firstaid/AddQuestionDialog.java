package com.mci.firstaid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddQuestionDialog extends AppCompatDialogFragment {
    private EditText editTextQuestion;
    private EditText editTextAnswerA;
    private EditText editTextAnswerB;
    private EditText editTextAnswerC;
    private RadioButton radioA;
    private RadioButton radioB;
    private RadioButton radioC;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_addquestion, null);

        builder.setView(view)
                .setTitle("Frage hinzufügen")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String question = editTextQuestion.getText().toString();
                        String answerA = editTextAnswerA.getText().toString();
                        String answerB = editTextAnswerB.getText().toString();
                        String answerC = editTextAnswerC.getText().toString();
                        int answer;
                        if (radioA.isChecked()) {
                            answer = 1;
                        }
                        else if (radioB.isChecked()) {
                            answer = 2;
                        }
                        else if (radioC.isChecked()){
                            answer = 3;
                        }
                        else {
                            answer = 1;
                        }
                        QuizDbHelper.getInstance(getContext()).addQuestion(new Question(question, answerA, answerB, answerC, answer));
                    }
                });

        editTextQuestion = view.findViewById(R.id.addQuestionEdit);
        editTextAnswerA = view.findViewById(R.id.addQuestionAnswerA);
        editTextAnswerB = view.findViewById(R.id.addQuestionAnswerB);
        editTextAnswerC = view.findViewById(R.id.addQuestionAnswerC);
        radioA = view.findViewById(R.id.radioA);
        radioB = view.findViewById(R.id.radioB);
        radioC = view.findViewById(R.id.radioC);

        return builder.create();
    }
}
