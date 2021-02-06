package edu.ucucite.quizlettyn.fragment_nav.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quiz_adapter;
import edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quiz_obj;
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItemsAdapter;

import static edu.ucucite.quizlettyn.InternetDetection.detectInternetAvailability;
import static edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quizzes_sets.math_quiz;
import static edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quizzes_sets.sci_quiz;
import static edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quizzes_sets.trivia_quiz;
import static edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs.preloaded_quizzes_sets.vocab_test;

public class HomeFragment extends Fragment {


    private ImageView dash_profile;
    private final ArrayList<preloaded_quiz_obj> menuquizzesItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutMan;
    private preloaded_quiz_adapter mAdapter;
    View root;
    FirebaseUser curent_user;
    String user_name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        home_items_ArrayList.add(new home_items_obj("How to study at home during coronavirus", "Website", ))
        TextView user_name_txt = root.findViewById(R.id.home_user_name_txt);
        try {
            curent_user = FirebaseAuth.getInstance().getCurrentUser();
            user_name = curent_user.getDisplayName();
            if (user_name.equals("")) {
                user_name = "Guest";
            }
            if (curent_user.equals(null)) {
                user_name_txt.setText("Guest");
            }
        }catch (Exception e){
            user_name = "Guest";
        }

        user_name_txt.setText(user_name);
        buildprofile_bg_random();
        detectInternetAvailability(getContext());
        create_list();
        buildrecycler();
        return root;
    }


    public void create_list() {
        menuquizzesItems.add(new preloaded_quiz_obj("Math",
                "Input the letter of the correct answer", math_quiz(getContext())));
        menuquizzesItems.add(new preloaded_quiz_obj("Trivia",
                "Choose from below the questions then input your answer", trivia_quiz(getContext())));
        menuquizzesItems.add(new preloaded_quiz_obj("Vocabulary",
                "Choose from below the questions then input your answer", vocab_test(getContext())));
        menuquizzesItems.add(new preloaded_quiz_obj("Science",
                "Choose from below the questions then input your answer", sci_quiz(getContext())
        ));
    }

    public void buildrecycler() {
        mRecyclerView = root.findViewById(R.id.recycler_pre_ld_quizzes);
        mRecyclerView.setHasFixedSize(false);
        mLayoutMan = new LinearLayoutManager(getContext());
        mAdapter = new preloaded_quiz_adapter(menuquizzesItems);
        mRecyclerView.setLayoutManager(mLayoutMan);
        mRecyclerView.setAdapter(mAdapter);


    }


    public void buildprofile_bg_random() {
        dash_profile = root.findViewById(R.id.profile_bg_home);
        Random random = new Random();
        dash_profile.setImageResource(getContext().getResources().getIdentifier(
                "img_dash_" + random.nextInt(16),
                "drawable",
                getContext().getPackageName()));

    }


}