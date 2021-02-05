package edu.ucucite.quizlettyn.editquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import edu.ucucite.quizlettyn.R;

import edu.ucucite.quizlettyn.fragment_nav.createquiz.quizInfo;
import edu.ucucite.quizlettyn.fragment_nav.createquiz.quizItem;
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItems;
import edu.ucucite.quizlettyn.takequiz.TakeQuiz;
import edu.ucucite.quizlettyn.takequiz.take_quizItem;

public class EditActivity extends AppCompatActivity {
    public static ArrayList<quizItem> quizItemsArrayList;
    public static ArrayList<String> doc_ids;
    private FloatingActionButton fab, fab_del;
    private quizInfo quizInfo_item;
    private DatabaseReference db_ref_quiz_sets, db_ref_quiz_info;
    FirebaseUser user;
    TextView quiz_name_nfo_txt, quiz_desc_nfo_txt;

    TextView tv_qz_progress;
    EditText ed_anstxt, ed_question;

    public static int GLOBAL_COUNTER_INDEX = 0;
    public static String quiz_code_str;

    public static int total_index = 0;
    public static int current_index = 0;

    private static DatabaseReference db_ref_quiz_items, db_ref_quiz_acc;

    Button btn_prev, btn_next, btn_add_new;
    public static myQuizItems editquizItemsInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        editquizItemsInfo = (myQuizItems) intent.getSerializableExtra("Quiz_item_edit_obj");
        getSupportActionBar().setTitle("Edit " + editquizItemsInfo.getQuiz_name());
        quiz_code_str = editquizItemsInfo.getQuiz_code();
        quizItemsArrayList = new ArrayList<>();
        doc_ids = new ArrayList<>();
        GLOBAL_COUNTER_INDEX = 0;
        current_index = GLOBAL_COUNTER_INDEX + 1;

        init_mod_qz();
        createQuizList(editquizItemsInfo.getQuiz_code());

    }

    public void init_mod_qz() {
        quiz_name_nfo_txt = findViewById(R.id.mod_quiz_title_take_quiz_txt);
        quiz_desc_nfo_txt = findViewById(R.id.mod_quiz_desc_take_quiz_txt);
        tv_qz_progress = findViewById(R.id.mod_tv_qz_progress);
        ed_question = findViewById(R.id.mod_ed_crt_qst_quiz);
        ed_anstxt = findViewById(R.id.mod_ed_crt_ans_quiz);
        btn_prev = findViewById(R.id.mod_btn_prev);
        btn_next = findViewById(R.id.mod_btn_next);
        btn_add_new = findViewById(R.id.mod_btn_add_new);
        fab = findViewById(R.id.mod_fab_add);
        fab_del = findViewById(R.id.mod_fab_delete);
    }

    @SuppressLint("SetTextI18n")
    public void mod_quiz() {
        quiz_name_nfo_txt.setText(editquizItemsInfo.getQuiz_name());
        quiz_desc_nfo_txt.setText(editquizItemsInfo.getQuiz_desc());
        current_index = GLOBAL_COUNTER_INDEX + 1;
//        int total_index = quizItemsArrayList.size();
        total_index = quizItemsArrayList.size();
        tv_qz_progress.setText(current_index + "/" + total_index);
        ed_question.setText(quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).getQuestion());
        // for now testing
        ed_anstxt.setText(quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).getAnswer());
        if (current_index == 1) {
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz_item_prev_next("prev");
            }
        });

        if (current_index == total_index) {
            btn_next.setVisibility(View.GONE);
            btn_add_new.setVisibility(View.VISIBLE);
        } else {
            btn_next.setVisibility(View.VISIBLE);
            btn_add_new.setVisibility(View.GONE);

        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz_item_prev_next("next");
            }
        });
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
                GLOBAL_COUNTER_INDEX++;
                mod_quiz();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
                mod_quiz();
            }
        });
        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void insertItem() {
        quiz_item_setter();
        quizItemsArrayList.add(new quizItem("", "", false));
        tv_qz_progress.setText(current_index + "/" + total_index);
    }

    public void quiz_item_setter() {
        quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).setQuestion(ed_question.getText().toString());
        quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).setAnswer(ed_anstxt.getText().toString());
    }


    public void quiz_item_prev_next(String prev_next_remarks) {
        quiz_item_setter();
        if (prev_next_remarks.equals("next")) {
            GLOBAL_COUNTER_INDEX++;
        } else if (prev_next_remarks.equals("prev")) {
            GLOBAL_COUNTER_INDEX--;
        }
        mod_quiz();
    }

    // do something with the data coming from the AlertDialog

    public void saveQuizEdit() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (quizItem obj : quizItemsArrayList) {
            db.collection(quiz_code_str)
                    .add(obj)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("error", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("error", "Error adding document", e);
                        }
                    });
        }
        removeQuizList();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_quiz, menu);
        return true;
    }

    public void removeQuizList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (String quiz_item_id : doc_ids){
            db.collection(quiz_code_str).document(quiz_item_id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Status", "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Status", "Error deleting document", e);
                        }
                    });
        }



    }


    public void createQuizList(String quiz_code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        quizItemsArrayList = new ArrayList<>();
        db.collection(quiz_code)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v("qid", document.getId() + " => " + document.getData());
//                                Toast.makeText(EditActivity.this, String.valueOf(), Toast.LENGTH_SHORT).show();

                                try {
                                    doc_ids.add(String.valueOf(document.getId()));
                                    quizItemsArrayList.add(new quizItem(String.valueOf(document.getData().get("question")),
                                            String.valueOf(document.getData().get("answer")),
                                            Boolean.parseBoolean(String.valueOf(document.getData().get("case_sensitive")))));
                                    Log.d("pass1", "ok");
//                                    Log.d("docidstr", doc_ids.get(0));
                                    mod_quiz();
//                                    quiz_desc_nfo_txt.setText(quizItemsArrayList.get(0).toString());

                                } catch (Exception e) {
                                    // complete
//                                    initquiz();
                                    quiz_desc_nfo_txt.setText(quizItemsArrayList.get(0).toString());
                                    Toast.makeText(EditActivity.this, quizItemsArrayList.get(0).toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("pass1  ", "error");
                                }
                            }
                        } else {
                            Log.w("errorfirestore", "Error getting documents.", task.getException());
                        }
                    }
                });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.compose_quiz_set:
//                Snackbar.make(getView(), "Confirm item", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showAlertConfirmation_qsave();
                return true;
            case R.id.reset_quiz_set:
                reset_quiz_items();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void reset_quiz_items(){
        quizItemsArrayList.clear();
        quizItemsArrayList = new ArrayList<>();
        GLOBAL_COUNTER_INDEX = 0;
        quizItemsArrayList.add(new quizItem("", "", false));
        mod_quiz();
    }
    public void showAlertConfirmation_qsave() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        builder.setTitle("Edit quiz");
        builder.setMessage("Do you want to save changes");

        // add a button
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quiz_item_setter();
                saveQuizEdit();
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}