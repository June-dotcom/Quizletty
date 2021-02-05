package edu.ucucite.quizlettyn.fragment_nav.sharedquizzes;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItems;
import edu.ucucite.quizlettyn.shareQuiztoUser.ShareActivity;


public class SharedQuizzesFragment extends Fragment {

    private final ArrayList<myQuizItems> menuQuizzesItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutMan;
    private sharedQuizAdapter mAdapter;
    private FirebaseUser user;
    private static String user_uid;
    private DatabaseReference db_ref_quiz_probe, db_ref_fetch_my_quizzes, db_ref_remove_menu_item, db_ref_remove_quiz_set;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shared_quizzes, container, false);
        create_menu_list();
//        menuQuizzesItems.add(new myQuizItems(0, "", "", "", "", "1"));
        buildRecyclerView();
        return view;
    }

    public void additem(int index, String quiz_key, String quiz_name, String quiz_desc, String quiz_code, String quiz_item_count) {
        menuQuizzesItems.add(new myQuizItems(index, quiz_key, quiz_name, quiz_desc, quiz_code, quiz_item_count));
        Log.v("quiz_item", menuQuizzesItems.get(0).getQuiz_code());
        mAdapter.notifyItemInserted(index);
        mAdapter.notifyDataSetChanged();

    }


    public void create_menu_list() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_uid = user.getUid();
        db_ref_fetch_my_quizzes = FirebaseDatabase.getInstance().getReference().child("Shared_quizzes").child(user_uid);
        db_ref_fetch_my_quizzes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                menuQuizzesItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    menuQuizzesItems.add(new myQuizItems(menuQuizzesItems.size(),
//                            dataSnapshot.getKey(),
//                            dataSnapshot.child("quiz_name").getValue().toString(),
//                            dataSnapshot.child("quiz_desc").getValue().toString(),
//                            dataSnapshot.child("quiz_code").getValue().toString(),
//                            dataSnapshot.child("quiz_items_count").getValue().toString()));

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


    // to be cont
    public void onDeleteItem(final int position) {
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to delete this item?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                // delete to database
//        firebase.child(id).removeValue();
                String quiz_id_rem = menuQuizzesItems.get(position).getQuiz_key();
//        menuQuizzesItems.get(position).setQuiz_name("null");
                menuQuizzesItems.remove(position);
                db_ref_remove_menu_item = FirebaseDatabase.getInstance().getReference().child("Shared_quizzes").child(user_uid).child(quiz_id_rem);
                db_ref_remove_menu_item.removeValue();
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

    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.recycler_my_shared_quizzes);
        mRecyclerView.setHasFixedSize(false);
        mLayoutMan = new LinearLayoutManager(getContext());
        mAdapter = new sharedQuizAdapter(menuQuizzesItems);
        mRecyclerView.setLayoutManager(mLayoutMan);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new sharedQuizAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                onDeleteItem(position);
            }


            @Override
            public void onShareClick(int position) {
                onShareItem(position);
            }
        });
    }
}