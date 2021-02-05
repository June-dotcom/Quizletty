package edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.takequiz.TakeQuiz;

public class preloaded_quiz_adapter extends RecyclerView.Adapter<preloaded_quiz_adapter.MyViewHolder> {
    private ArrayList<preloaded_quiz_obj> menuItemQuizzesList;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quiz_name, quiz_desc, quiz_item_count;
        public MaterialCardView card_menu;
        public ImageView card_background;

        public MyViewHolder(View v) {
            super(v);
            quiz_name = v.findViewById(R.id.preloaded_menu_quiz_title);
            card_background = v.findViewById(R.id.preloaded_menu_card);
            card_menu = v.findViewById(R.id.preld_card_quiz_menu);


        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public preloaded_quiz_adapter(ArrayList<preloaded_quiz_obj> myDataset) {
        this.menuItemQuizzesList = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public preloaded_quiz_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_preloaded_quiz_menu, parent, false);
        preloaded_quiz_adapter.MyViewHolder vh = new preloaded_quiz_adapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final preloaded_quiz_adapter.MyViewHolder holder, final int position) {
        final preloaded_quiz_obj QuizItemholder_obj = menuItemQuizzesList.get(position);
        holder.quiz_name.setText(QuizItemholder_obj.getQuiz_name());

        Random random = new Random();
        holder.card_background.setImageResource(holder.itemView.getContext().getResources().getIdentifier(
                "img_dash_" + random.nextInt(17),
                "drawable",
                holder.itemView.getContext().getPackageName()));
        holder.card_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quiz_object
                Intent intent = new Intent(v.getContext(), TakeQuiz.class);
                intent.putExtra("quiz_frm_type", "frm_qz_sample");
                intent.putParcelableArrayListExtra("quiz_set_arrlist", QuizItemholder_obj.getQuizItems());
                intent.putExtra("quiz_name", QuizItemholder_obj.getQuiz_name());
                intent.putExtra("quiz_desc", QuizItemholder_obj.getQuiz_desc());
                v.getContext().startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return menuItemQuizzesList.size();
    }
}
