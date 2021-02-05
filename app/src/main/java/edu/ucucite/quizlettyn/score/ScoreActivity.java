package edu.ucucite.quizlettyn.score;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.ucucite.quizlettyn.MainActivity;
import edu.ucucite.quizlettyn.R;

import edu.ucucite.quizlettyn.takequiz.take_quizItem;

public class ScoreActivity extends AppCompatActivity {
    public TextView score_info, score_txt, score_pct, score_ratio;
    private static ArrayList<take_quizItem> take_quizItems;
    private RecyclerView qRecyclerView;
    private RecyclerView.LayoutManager mLayoutMan;
    private resultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent = getIntent();
        String total_items_str = intent.getStringExtra("total_items");
        String score_str = intent.getStringExtra("score");
        getSupportActionBar().setTitle("Your score for " + intent.getStringExtra("quiz_name"));
        DecimalFormat format_score_pct = new DecimalFormat("#.00");
        score_info = findViewById(R.id.score_info);
        score_txt = findViewById(R.id.score_txt);
        score_pct = findViewById(R.id.score_pct);
        score_ratio = findViewById(R.id.score_ratio);

        score_txt.setText(score_str);
        score_ratio.setText(score_str + "/" + total_items_str);

        take_quizItems = getIntent().getParcelableArrayListExtra("quiz_item_arr_list");

//        Toast.makeText(this, take_quizItems.get(0).getInputted_answer(), Toast.LENGTH_SHORT).show();
        float score_pct_float = (Float.parseFloat(score_str) / Float.parseFloat(total_items_str)) * 100;
        score_pct.setText(format_score_pct.format(score_pct_float) + "%");

        score_info.setText(getScoreInfoScale(score_pct_float));
        buildRecyclerView();

    }


    public void buildRecyclerView(){
        qRecyclerView = findViewById(R.id.recycler_item_result);
        qRecyclerView.setHasFixedSize(false);
        mLayoutMan = new LinearLayoutManager(getApplicationContext());
        mAdapter = new resultAdapter(take_quizItems);
        qRecyclerView.setLayoutManager(mLayoutMan);
        qRecyclerView.setAdapter(mAdapter);


    }
    public String getScoreInfoScale(float score_pct_float){
        String score_info_str = "";
        if (score_pct_float > 95) {
            score_info_str = "Excellent";
        } else if (score_pct_float > 90 && score_pct_float < 95) {
            score_info_str = "Great";
        } else if (score_pct_float > 80 && score_pct_float < 90) {
            score_info_str = "Good";
        } else if (score_pct_float > 70 && score_pct_float < 80) {
            score_info_str = "Fair";
        } else if (score_pct_float > 60 && score_pct_float < 70) {
            score_info_str = "That's okay";
        } else if (score_pct_float < 60) {
            score_info_str = "That's rough buddy";
        }
        return score_info_str;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ScoreActivity.this, MainActivity.class));
    }

}