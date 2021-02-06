package edu.ucucite.quizlettyn.fragment_nav.myquizzes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.editquiz.EditActivity;
import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.shareQuiztoUser.ShareActivity;
import edu.ucucite.quizlettyn.takequiz.TakeQuiz;


public class myquizzesFragment extends Fragment {

    private final ArrayList<myQuizItems> menuQuizzesItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutMan;
    private myQuizItemsAdapter mAdapter;
    private FirebaseUser user;
    private static String user_uid;
    Button btn_start_quiz;
    TextInputEditText quiz_code_query_ed;
    View view;
    private DatabaseReference db_ref_quiz_probe, db_ref_fetch_my_quizzes,
            db_ref_remove_menu_item, db_ref_remove_quiz_set, db_ref_fetch_quiz_code;
    private static String quiz_code_query = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_myquizzes, container, false);
        btn_start_quiz = view.findViewById(R.id.btn_qcode_enter);
        quiz_code_query_ed = view.findViewById(R.id.quiz_code_ed);

        // get current user
        create_menu_list();
        buildRecyclerView();
        btn_start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startquiz();
            }
        });
        return view;
    }

    public void additem(int index, String quiz_key, String quiz_name, String quiz_desc,
                        String quiz_code, String quiz_item_count) {
        menuQuizzesItems.add(new myQuizItems(index, quiz_key, quiz_name,
                quiz_desc, quiz_code, quiz_item_count));
        mAdapter.notifyItemInserted(index);
        mAdapter.notifyDataSetChanged();

    }


    public void create_menu_list() {
//        menuQuizzesItems = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_uid = user.getUid();
        db_ref_fetch_my_quizzes = FirebaseDatabase.getInstance().getReference().child("User_quizzes").child(user_uid);
        db_ref_fetch_my_quizzes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuQuizzesItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    additem(menuQuizzesItems.size(),
                            dataSnapshot.getKey(),
                            dataSnapshot.child("quiz_name").getValue().toString(),
                            dataSnapshot.child("quiz_desc").getValue().toString(),
                            dataSnapshot.child("quiz_code").getValue().toString(),
                            dataSnapshot.child("quiz_item_count").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startquiz() {
        quiz_code_query = quiz_code_query_ed.getText().toString();
        Toast.makeText(getContext(), quiz_code_query, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), TakeQuiz.class);
        intent.putExtra("quiz_code", quiz_code_query);
        intent.putExtra("quiz_frm_type", "frm_qz_code");
        view.getContext().startActivity(intent);


//        Toast.makeText(getContext(), quiz_code_query, Toast.LENGTH_SHORT).show();

//        db_ref_quiz_probe = FirebaseDatabase.getInstance().getReference().child("Quizzes_set").child(quiz_code_query);
//        db_ref_quiz_probe.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
////                     user exists in the database
//                    Intent intent = new Intent(getContext(), TakeQuiz.class);
//                        intent.putExtra("quiz_code", quiz_code_query);
//                    intent.putExtra("quiz_frm_type", "frm_qz_code");
//                    view.getContext().startActivity(intent);
//                    Toast.makeText(getContext(), "Existed", Toast.LENGTH_SHORT).show();
//                }else{
//                    // user does not exist in the database
//                    Toast.makeText(getContext(), "not-existed", Toast.LENGTH_SHORT).show();
//
//                }
////                if (snapshot.hasChild(quiz_code_query)) {
////                    if (!quiz_code_query.equals("")) {
////                        Intent intent = new Intent(getContext(), TakeQuiz.class);
////                        intent.putExtra("quiz_code", quiz_code_query);
////                        view.getContext().startActivity(intent);
////                    }
////                } else {
////                    Toast.makeText(getContext(), quiz_code_query + " does not exists", Toast.LENGTH_SHORT).show();
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void onEditItem(int position) {
        myQuizItems QuizItem_share_obj = menuQuizzesItems.get(position);
        Intent intent = new Intent(getContext(), EditActivity.class);
        intent.putExtra("Quiz_item_edit_obj", QuizItem_share_obj);
        startActivity(intent);
    }

    // to be cont
    public void onDeleteItem(final int position) {
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                // delete to database
//        firebase.child(id).removeValue();
                String quiz_id_rem = menuQuizzesItems.get(position).getQuiz_key();
                String quiz_code = menuQuizzesItems.get(position).getQuiz_code();
//        menuQuizzesItems.get(position).setQuiz_name("null");
                menuQuizzesItems.remove(position);
                db_ref_remove_menu_item = FirebaseDatabase.getInstance().getReference().child("User_quizzes").child(user_uid).child(quiz_id_rem);
                db_ref_remove_menu_item.removeValue();
                db_ref_remove_quiz_set = FirebaseDatabase.getInstance().getReference().child("Quizzes_set").child(quiz_code);
                db_ref_remove_quiz_set.removeValue();
                mAdapter.notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        //


    }

    public void onShareItem(int position) {
        myQuizItems QuizItem_share_obj = menuQuizzesItems.get(position);
        Intent intent = new Intent(getContext(), ShareActivity.class);
        intent.putExtra("Quiz_item_share_obj", QuizItem_share_obj);
        startActivity(intent);
    }

    public void onSaveItem(int position) {
        myQuizItems Quizitem_save_obj = menuQuizzesItems.get(position);

    }

    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.my_quizzes_menu_items);
        mRecyclerView.setHasFixedSize(false);
        mLayoutMan = new LinearLayoutManager(getContext());
        mAdapter = new myQuizItemsAdapter(menuQuizzesItems);
        mRecyclerView.setLayoutManager(mLayoutMan);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new myQuizItemsAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                onDeleteItem(position);
            }

            @Override
            public void onEditClick(int position) {
                onEditItem(position);
            }

            @Override
            public void onShareClick(int position) {
                onShareItem(position);
            }

            @Override
            public void onSaveClick(int position) {
                onSaveItem(position);
            }


        });
    }
}