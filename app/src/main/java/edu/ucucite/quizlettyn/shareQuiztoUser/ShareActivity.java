  package edu.ucucite.quizlettyn.shareQuiztoUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.ShowQrQzStActivity;
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItems;

public class ShareActivity extends AppCompatActivity {
    private DatabaseReference db_ref_list_users, db_ref_send_quiz, db_ref_probe_quiz_uid_share;
    private EditText name_search_ed, quiz_code_share_ed;
    private ImageView ic_name_search, ic_clipboard;
    private RecyclerView qRecyclerView;
    private final ArrayList<UserQueryInfo> userAccInfos = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutMan;
    private userItemAdapter mAdapter;
    private static final boolean IS_QUIZ_USER_EXISTS = false;

    private static myQuizItems quizItem_to_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Intent intent = getIntent();
        quizItem_to_share = (myQuizItems) intent.getSerializableExtra("Quiz_item_share_obj");
        Log.v("obj_output", quizItem_to_share.getQuiz_code());
// build rec
        buildRecyclerView();
        getSupportActionBar().setTitle("Share " + quizItem_to_share.getQuiz_name());
        name_search_ed = findViewById(R.id.name_query);
        quiz_code_share_ed = findViewById(R.id.quiz_code_Share);
        ic_name_search = findViewById(R.id.ic_name_search);
        ic_clipboard = findViewById(R.id.copy_clipboard);
        quiz_code_share_ed.setText(quizItem_to_share.getQuiz_code());
        ic_clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Quiz code", quizItem_to_share.getQuiz_code());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ShareActivity.this, "Quiz code copy to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        ic_name_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("value of provider", "init");
                firebase_account_name_search(name_search_ed.getText().toString());

            }
        });
    }


    public void onshare_to_user(int index){
        String uid_receiver = userAccInfos.get(index).getUid();
//        String quiz_code = quizItem_to_share.getQuiz_code();

//        boolean is_quiz_existed = is_quiz_share_to_user_exists(quiz_code, uid_receiver);
            db_ref_send_quiz = FirebaseDatabase.getInstance().getReference("Shared_quizzes").child(uid_receiver);
            db_ref_send_quiz.push().setValue(quizItem_to_share);
//        db_ref_quiz_info = FirebaseDatabase.getInstance().getReference().child("User_quizzes").child(q_creator_uid);
//        db_ref_quiz_info.push().setValue(quizInfo_item);
    }
    public void additem(int position, String user_id, String name, String email, String profile_uri, String provider) {
        userAccInfos.add(new UserQueryInfo(position, user_id, name, email, profile_uri, provider));
        mAdapter.notifyItemInserted(position);
        mAdapter.notifyDataSetChanged();
    }

    public void firebase_account_name_search(String q_user_name) {
//        String q_user_str = q_user_name.toUpperCase();
        db_ref_list_users = FirebaseDatabase.getInstance().getReference("User_accounts");
        db_ref_list_users.orderByChild("name").equalTo(q_user_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAccInfos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Log.v("value of name", dataSnapshot.child("uid").getValue().toString());
//                    Log.v("value of name", dataSnapshot.child("name").getValue().toString());
//                    Log.v("value of name", dataSnapshot.child("email").getValue().toString());
//
//                    Log.v("value of provider", dataSnapshot.child("provider_data").getValue().toString());
//                    Toast.makeText(ShareActivity.this, "Found item", Toast.LENGTH_SHORT).show();
                    additem(userAccInfos.size(),
                            dataSnapshot.child("uid").getValue().toString(),
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("email").getValue().toString(),
                            dataSnapshot.child("profile_uri").getValue().toString(),
                            dataSnapshot.child("provider_data").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater findMenuItems = getMenuInflater();
//        findMenuItems.inflate(R.menu.menu_shared_quiz, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.shared_qr_quiz_set:
//                Intent intentqr = new Intent(getApplicationContext(), ShowQrQzStActivity.class);
//                intentqr.putExtra("qr_qzlty_code", quizItem_to_share.getQuiz_code());
//                intentqr.putExtra("qr_qzlty_name", quizItem_to_share.getQuiz_name());
//                startActivity(intentqr);
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void buildRecyclerView() {
//        userAccInfos = new ArrayList<>();
//        userAccInfos.add(new UserQueryInfo(0, "t", "t", "T", "T", "T"));
        qRecyclerView = findViewById(R.id.recycler_result_name_items);
        qRecyclerView.setHasFixedSize(false);
        mLayoutMan = new LinearLayoutManager(getApplicationContext());
        mAdapter = new userItemAdapter(userAccInfos);
        qRecyclerView.setLayoutManager(mLayoutMan);
        qRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new userItemAdapter.OnItemClickListener() {
            @Override
            public void onShareToUser(int index) {
                // get the share quiz id index and then do the firebase thing
                onshare_to_user(index);
                finish();
//                Toast.makeText(ShareActivity.this, "touched", Toast.LENGTH_SHORT).show();
            }
        });
    }
}