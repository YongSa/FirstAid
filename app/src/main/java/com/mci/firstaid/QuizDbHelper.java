package com.mci.firstaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mci.firstaid.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FirstAid.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void resetDatabase() {
        if (db == null || !db.isOpen()) {
            db = getWritableDatabase();
        }
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Welche Antwort zur Wundenversorgung und Blutstillung ist richtg?", "In der Wunde befindliche Fremdkörper müssen entfernt werden, da sonst kein Druckverband angelegt werden kann.", "Wenn es die Zeit erlaubt, ist zum Anlegen des Wundverbandes die Bekleidung nach Möglichkeit zu entfernen, damit die Wunde frei bleibt.", "Blutet ein Druckverband durch, so ist dieser vollständig zu entfernen und durch einen neuen zu ersetzten.", 2);
        addQuestion(q1);
        Question q2 = new Question("Wer darf ein, nach dem zweiten Druckverband, angelegtes Tourniquet entfernen?", "Ein Sanitätsoffizier Arzt", "Ein Einsatzhelfer A", "Ein Sanitätsfeldwebel und Rettungsassistent", 1);
        addQuestion(q2);
        Question q3 = new Question("Nach wie vielen MInuten kann bei der Anwendung des Morphin-Autoinjektors ein weiterer Injektor gegeben werden, wenn keine schmerzstillende Wikrung eintritt?", "Nach 15 Minuten", "Nach 60 Minuten", "Nach 30 Minuten", 3);
        addQuestion(q3);
        Question q4 = new Question("Bei Anwendung des Autoinjektors: Wessen Autoinjektor ist grundsätzlich zu verwenden?", "Der Autoinjektor der verletzten Person", "Der Autoinjektor der hilfeleistenten Person", "Der Autoinjektor von nicht-verwundeten Kameraden", 1);
        addQuestion(q4);
        Question q5 = new Question("Wie viele Anwendungen des Morphin-Autoinjektors können max. pro Tag erfolgen", "2 Anwendungen", "6 Anwendungen", "4 Anwendungen", 2);
        addQuestion(q5);
    }

    public void addQuestion(Question question) {
        if (db == null || !db.isOpen()) {
            db = getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
