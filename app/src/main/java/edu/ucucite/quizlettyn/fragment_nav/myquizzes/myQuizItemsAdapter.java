package edu.ucucite.quizlettyn.fragment_nav.myquizzes;


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

public class myQuizItemsAdapter extends RecyclerView.Adapter<myQuizItemsAdapter.MyViewHolder> {
    private final ArrayList<myQuizItems> menuItemQuizzesList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onEditClick(int position);

        void onShareClick(int position);

        void onSaveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView quiz_name, quiz_desc, quiz_item_count;
        public MaterialCardView card_menu;
        public ImageView card_background;
        public Button icon_edit, icon_delete, icon_share, icon_save;

        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            quiz_name = v.findViewById(R.id.menu_quiz_title);
            quiz_desc = v.findViewById(R.id.quiz_desc);
            quiz_item_count = v.findViewById(R.id.quiz_item_count);
            card_menu = v.findViewById(R.id.card_quiz_menu);
            card_background = v.findViewById(R.id.menu_card_img);
            icon_edit = v.findViewById(R.id.q_menu_edit_btn);
            icon_delete = v.findViewById(R.id.q_menu_del_btn);
            icon_share = v.findViewById(R.id.q_menu_share_btn);
            icon_save = v.findViewById(R.id.q_menu_save_btn);
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

            icon_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(getAdapterPosition());
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

            icon_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onSaveClick(getAdapterPosition());
                        }
                    }
                }
            });

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public myQuizItemsAdapter(ArrayList<myQuizItems> myDataset) {
        this.menuItemQuizzesList = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public myQuizItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_quiz_menu_item, parent, false);
        myQuizItemsAdapter.MyViewHolder vh = new myQuizItemsAdapter.MyViewHolder(v, mListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final myQuizItemsAdapter.MyViewHolder holder, final int position) {
        final myQuizItems QuizItemholder_obj = menuItemQuizzesList.get(position);
        holder.quiz_name.setText(QuizItemholder_obj.getQuiz_name());

        holder.quiz_desc.setText(QuizItemholder_obj.getQuiz_desc());
        String quiz_item_count_txt = " items";


        Random random = new Random();
        holder.card_background.setImageResource(holder.itemView.getContext().getResources().getIdentifier(
                "img_dash_" + random.nextInt(16),
                "drawable",
                holder.itemView.getContext().getPackageName()));

        if (Integer.parseInt(QuizItemholder_obj.getQuiz_item_count()) > 1) {
            quiz_item_count_txt = QuizItemholder_obj.getQuiz_item_count() + "items";
        } else {
            quiz_item_count_txt = QuizItemholder_obj.getQuiz_item_count() + "item";
        }
        holder.quiz_item_count.setText(quiz_item_count_txt);
        holder.card_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quiz_code = menuItemQuizzesList.get(position).getQuiz_code();
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
