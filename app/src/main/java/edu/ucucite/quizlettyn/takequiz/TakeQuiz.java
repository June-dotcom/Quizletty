package edu.ucucite.quizlettyn.takequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.score.ScoreActivity;
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItems;

public class TakeQuiz extends AppCompatActivity {
    private DatabaseReference db_ref_take_quiz_set, db_ref_take_quiz_info;
    public static ArrayList<take_quizItem> takeQuizItems;

    //    private RecyclerView tRecyclerView;
//    private RecyclerView.LayoutManager tLayoutMan;tfsdafsf
//    private TakeQuizAdapter tAdapter;
    public static String quiz_desc_str;
    public static String quiz_name_str;
    TextView quiz_name_nfo_txt, quiz_desc_nfo_txt;

    TextView tv_question, tv_qz_progress;
    EditText ed_anstxt;

    public static int GLOBAL_COUNTER_INDEX = 0;
    public static String quiz_code_str;
    private static myQuizItems quiz_obj;
    Button btn_prev, btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        Intent intent = getIntent();
        String quiz_frm_type = intent.getStringExtra("quiz_frm_type");
        quiz_name_nfo_txt = findViewById(R.id.quiz_title_take_quiz_txt);
        quiz_desc_nfo_txt = findViewById(R.id.quiz_desc_take_quiz_txt);

        tv_qz_progress = findViewById(R.id.tv_qz_progress);
        tv_question = findViewById(R.id.question_tk_ch);
        ed_anstxt = findViewById(R.id.answer_tk_ch);

        btn_prev = findViewById(R.id.btn_prev);
        btn_next = findViewById(R.id.btn_next);

        // categorize quiz types
        if (quiz_frm_type.equals("frm_qz_items")) {
            quiz_obj = (myQuizItems) intent.getSerializableExtra("quiz_obj");
            String quiz_code = quiz_obj.getQuiz_code();
            quiz_name_nfo_txt.setText(quiz_obj.getQuiz_name());
            quiz_desc_nfo_txt.setText(quiz_obj.getQuiz_desc());
            quiz_name_str = quiz_obj.getQuiz_name();
            GLOBAL_COUNTER_INDEX = 0;

            getSupportActionBar().setTitle(quiz_obj.getQuiz_name());
            createQuizList(quiz_code);
//            initquiz();

        } else if (quiz_frm_type.equals("frm_qz_code")) {

            quiz_code_str = intent.getStringExtra("quiz_code");
//            findViewById(R.id.quiz_desc_card).setVisibility(View.GONE);
            GLOBAL_COUNTER_INDEX = 0;
            createQuizList(quiz_code_str);
            initquiz();
        } else if (quiz_frm_type.equals("frm_qz_qr")) {
            quiz_code_str = intent.getStringExtra("quiz_code");
//            findViewById(R.id.quiz_desc_card).setVisibility(View.GONE);
            GLOBAL_COUNTER_INDEX = 0;
            createQuizList(quiz_code_str);
            initquiz();
        } else if (quiz_frm_type.equals("frm_qz_sample")) {
//            findViewById(R.id.quiz_desc_card).setVisibility(View.GONE);
            takeQuizItems = new ArrayList<>();
            ArrayList<take_quizItem> takeQuizItems_tmp = intent.getParcelableArrayListExtra("quiz_set_arrlist");
            GLOBAL_COUNTER_INDEX = 0;
            quiz_name_nfo_txt.setText(intent.getStringExtra("quiz_name"));
            quiz_desc_nfo_txt.setText(intent.getStringExtra("quiz_desc"));
            quiz_name_str = intent.getStringExtra("quiz_name");
            takeQuizItems = takeQuizItems_tmp;
//            altbuildRecyclerView();
            initquiz();
        }
    }

    @SuppressLint("SetTextI18n")
    public void initquiz() {

        int current_index = GLOBAL_COUNTER_INDEX + 1;
//        int total_index = takeQuizItems.size();
        int total_index = takeQuizItems.size();
        tv_qz_progress.setText(current_index + "/" + total_index);

        tv_question.setText(takeQuizItems.get(GLOBAL_COUNTER_INDEX).getQuestion());
        // for now testing
        ed_anstxt.setText(takeQuizItems.get(GLOBAL_COUNTER_INDEX).getInputted_answer());
        if (current_index == 1) {
            btn_prev.setVisibility(View.INVISIBLE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeQuizItems.get(GLOBAL_COUNTER_INDEX).setInputted_answer(ed_anstxt.getText().toString());
                GLOBAL_COUNTER_INDEX--;
                initquiz();
            }
        });


        if (current_index == total_index) {
            btn_next.setVisibility(View.INVISIBLE);
        } else {
            btn_next.setVisibility(View.VISIBLE);
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeQuizItems.get(GLOBAL_COUNTER_INDEX).setInputted_answer(ed_anstxt.getText().toString());
                GLOBAL_COUNTER_INDEX++;
                initquiz();

            }
        });
    }

    public void createQuizList(String quiz_code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        takeQuizItems = new ArrayList<>();
                db.collection(quiz_code)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());

                                try {
                                    takeQuizItems.add(new take_quizItem(takeQuizItems.size()+1,
                                            String.valueOf(document.getData().get("question")),"",
                                            String.valueOf(document.getData().get("answer")),
                                            Boolean.parseBoolean(String.valueOf(document.getData().get("case_sensitive")))));
                                    Log.d("pass1", "ok");
                                    initquiz();

                                } catch (Exception e) {
                                    // complete
//                                    initquiz();
//                                    Toast.makeText(TakeQuiz.this, takeQuizItems.get(0).toString(), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_take_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.info_take_quiz:
//                show_alert_quiz_info();
//                // show alert dialog info
//                return true;
            case R.id.submit_take_quiz:
//                show_alert_quiz_info();
                // check the quiz items then proceed to the scores
                // bug
                takeQuizItems.get(GLOBAL_COUNTER_INDEX).setInputted_answer(ed_anstxt.getText().toString());
                confirm_submit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void confirm_submit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Do you want to check your quiz");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                Intent intent = new Intent(TakeQuiz.this, ScoreActivity.class);
                intent.putExtra("quiz_name", quiz_name_str);
                intent.putExtra("total_items", String.valueOf(takeQuizItems.size()));
                intent.putExtra("score", String.valueOf(getQuiz_score()));
                intent.putParcelableArrayListExtra("quiz_item_arr_list", takeQuizItems);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "score " + getQuiz_score() + "/" + takeQuizItems.size(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean check_for_unanswered_items() {
        boolean is_all_answered = true;
        for (take_quizItem takequizitem : takeQuizItems) {
            if (takequizitem.getInputted_answer().equals("")) {
                is_all_answered = false;
            }
        }
        return is_all_answered;
    }

    public int getQuiz_score() {
        int score = 0;
        for (take_quizItem takeQuizItem : takeQuizItems) {
            if (takeQuizItem.getInputted_answer().equals(takeQuizItem.getCorrect_answer())) {
                score += 1;
            }
        }
        return score;

    }

}
