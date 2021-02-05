package edu.ucucite.quizlettyn.fragment_nav.sharedquizzes;

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
import edu.ucucite.quizlettyn.fragment_nav.myquizzes.myQuizItems;
import edu.ucucite.quizlettyn.takequiz.TakeQuiz;

public class sharedQuizAdapter extends RecyclerView.Adapter<sharedQuizAdapter.MyViewHolder> {
    private ArrayList<myQuizItems> menuItemQuizzesList;
    private sharedQuizAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onDeleteClick(int position);
        
        void onShareClick(int position);
    }

    public void setOnItemClickListener(sharedQuizAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quiz_name, quiz_desc, quiz_item_count;
        public MaterialCardView card_menu;
        public ImageView card_background;
        public Button icon_delete, icon_share;

        public MyViewHolder(View v, final sharedQuizAdapter.OnItemClickListener listener) {
            super(v);
            quiz_name = v.findViewById(R.id.shared_menu_quiz_title);
            quiz_desc = v.findViewById(R.id.shared_quiz_desc);
            quiz_item_count = v.findViewById(R.id.shared_quiz_item_count);
            card_menu = v.findViewById(R.id.shared_card_quiz_menu);
            card_background = v.findViewById(R.id.shared_menu_card);
            icon_delete = v.findViewById(R.id.shared_q_menu_del_btn);
            icon_share = v.findViewById(R.id.shared_q_menu_share_btn);

            icon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(getAdapterPosition());
                        }
                    }
                }
            });

            icon_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShareClick(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public sharedQuizAdapter(ArrayList<myQuizItems> myDataset) {
        this.menuItemQuizzesList = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public sharedQuizAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_shared_quiz_menu_item, parent, false);
        sharedQuizAdapter.MyViewHolder vh = new sharedQuizAdapter.MyViewHolder(v, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final sharedQuizAdapter.MyViewHolder holder, final int position) {
        final myQuizItems QuizItemholder_obj = menuItemQuizzesList.get(position);
        holder.quiz_name.setText(QuizItemholder_obj.getQuiz_name());

        holder.quiz_desc.setText(QuizItemholder_obj.getQuiz_desc());
        String quiz_item_count_txt = " items";

        if (Integer.parseInt(QuizItemholder_obj.getQuiz_item_count()) > 1) {
            quiz_item_count_txt = QuizItemholder_obj.getQuiz_item_count() + "items";
        } else {
            quiz_item_count_txt = QuizItemholder_obj.getQuiz_item_count() + "item";
        }
        holder.quiz_item_count.setText(quiz_item_count_txt);
        Random random = new Random();
        holder.card_background.setImageResource(holder.itemView.getContext().getResources().getIdentifier(
                "img_dash_" + random.nextInt(16),
                "drawable",
                holder.itemView.getContext().getPackageName()));

        holder.card_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quiz_object
                Intent intent = new Intent(v.getContext(), TakeQuiz.class);
                intent.putExtra("quiz_obj", QuizItemholder_obj);
                intent.putExtra("quiz_frm_type", "frm_qz_items");

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

