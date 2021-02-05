package edu.ucucite.quizlettyn.fragment_nav.createquiz;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import edu.ucucite.quizlettyn.R;


public class createquizFragment extends Fragment {
    // array list for create quiz item
    public static ArrayList<quizItem> quizItemsArrayList;

    private FloatingActionButton fab, fab_del;
    private static View root;
    private quizInfo quizInfo_item;
    private DatabaseReference db_ref_quiz_sets, db_ref_quiz_info;
    FirebaseUser user;

    TextView tv_qz_progress;
    EditText ed_anstxt, ed_question;

    public static int GLOBAL_COUNTER_INDEX = 0;
    public static String quiz_code_str;

    public static int total_index = 0;
    public static int current_index = 0;

    Button btn_prev, btn_next, btn_add_new;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_createquiz, container, false);
        setHasOptionsMenu(true);
        createEmptyList();
        init_create_qz();
        crt_quiz();
        //db_ref



        //end of db ref
        fab = root.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
                crt_quiz();

//                Snackbar.make(view, "Add item complete ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });

        fab_del = root.findViewById(R.id.fab_delete);
        fab_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(GLOBAL_COUNTER_INDEX);
            }
        });

        return root;
    }

    public void init_create_qz() {
        tv_qz_progress = root.findViewById(R.id.crt_tv_qz_progress);
        ed_question = root.findViewById(R.id.ed_crt_qst_quiz);
        ed_anstxt = root.findViewById(R.id.ed_crt_ans_quiz);
        btn_prev = root.findViewById(R.id.crt_btn_prev);
        btn_next = root.findViewById(R.id.crt_btn_next);
        btn_add_new = root.findViewById(R.id.crt_btn_add_new);

    }

    public void reset_quiz_items(){
        quizItemsArrayList.clear();
        quizItemsArrayList = new ArrayList<>();
        GLOBAL_COUNTER_INDEX = 0;
        quizItemsArrayList.add(new quizItem("", "", false));
        crt_quiz();

    }

    public void showAlertConfirmation_qsave() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // set the custom layout
        quiz_item_setter();
        builder.setMessage("Enter quiz details to save");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_create_quiz_confirm, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("SAVE QUIZ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                TextInputEditText quiztitle = customLayout.findViewById(R.id.quiz_title_ed);
                TextInputEditText quizdesc = customLayout.findViewById(R.id.quiz_description_ed);
                confirm_quiz_save_dialog(quiztitle.getText().toString(), quizdesc.getText().toString(), quizItemsArrayList);
//                qAdapter.notifyDataSetChanged();
                reset_quiz_items();
                Toast.makeText(getContext(), "Quiz saved", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetTextI18n")
    public void crt_quiz() {
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
                crt_quiz();
            }
        });
    }

    public void quiz_item_setter(){
        quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).setQuestion(ed_question.getText().toString());
        quizItemsArrayList.get(GLOBAL_COUNTER_INDEX).setAnswer(ed_anstxt.getText().toString());
    }
    public void quiz_item_prev_next(String prev_next_remarks){
        quiz_item_setter();
        if (prev_next_remarks.equals("next")){
            GLOBAL_COUNTER_INDEX++;
        }else if (prev_next_remarks.equals("prev")){
            GLOBAL_COUNTER_INDEX--;
        }
        crt_quiz();
    }

    // do something with the data coming from the AlertDialog
    private void confirm_quiz_save_dialog(String qtitle, String qdesc, ArrayList<quizItem> quizItemsArrayList_save) {
// do all firebase saving and sqlite for offline data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // quizzes_set_items_realtime database
        UUID idOne = UUID.randomUUID();
        Calendar cal = Calendar.getInstance();

        String uni_id =  String.valueOf(idOne).substring(1, 5) + String.valueOf(new SimpleDateFormat("MD").format(cal.getTime()));
        String quiz_set_id = "QZLTY-" + uni_id.toUpperCase();
//        for (quizItem quizItem: quizItemsArrayList_save){
//            db.collection("quizzes").document(quiz_set_id).set(quizItem).document().add(quizItem).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    Toast.makeText(root.getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }


        for (quizItem quizItem : quizItemsArrayList_save) {
            db.collection(quiz_set_id)
                    .add(quizItem)
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


        Toast.makeText(getContext(), "Successfully created quiz to firestore", Toast.LENGTH_SHORT).show();

        // end of
        // get current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        String q_creator_name = user.getDisplayName();
        String q_creator_uid = user.getUid();

        //quiz info set realtime database
        quizInfo_item = new quizInfo(quiz_set_id, qtitle, qdesc, q_creator_uid, q_creator_name,
                String.valueOf(quizItemsArrayList_save.size()));
        db_ref_quiz_info = FirebaseDatabase.getInstance().getReference().child("User_quizzes").child(q_creator_uid);
        db_ref_quiz_info.push().setValue(quizInfo_item);
        // end of

//        // quiz account by uid set realtime database
//        db_ref_quiz_acc = FirebaseDatabase.getInstance().getReference().child("User_quizzes_id");
//        db_ref_quiz_acc.child(q_creator_uid).push().setValue(quiz_set_id);
        // end of
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_quiz, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.compose_quiz_set:
                Snackbar.make(getView(), "Confirm item", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showAlertConfirmation_qsave();
                return true;
            case R.id.reset_quiz_set:
                reset_quiz_items();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void insertItem() {
        quiz_item_setter();
        quizItemsArrayList.add(new quizItem("", "", false));
        tv_qz_progress.setText(current_index + "/" + total_index);

    }

    public void removeItem(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity

                if (GLOBAL_COUNTER_INDEX!=0){
                    quizItemsArrayList.get(position).setAnswer("");
                    quizItemsArrayList.get(position).setQuestion("");
                    quizItemsArrayList.remove(position);
                    GLOBAL_COUNTER_INDEX--;
                }else{
                    quizItemsArrayList.get(position).setAnswer("");
                    quizItemsArrayList.get(position).setQuestion("");
                }
                crt_quiz();

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

    public void createEmptyList() {
        quizItemsArrayList = new ArrayList<>();
        quizItemsArrayList.add(new quizItem("", "", false));
    }


}