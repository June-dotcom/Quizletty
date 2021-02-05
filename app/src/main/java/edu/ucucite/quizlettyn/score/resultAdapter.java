package edu.ucucite.quizlettyn.score;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.takequiz.take_quizItem;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.shareUserViewHolder> {
    private final ArrayList<take_quizItem> result_quiz_items;


    public static class shareUserViewHolder extends RecyclerView.ViewHolder {
        public TextView q_text, my_ans_txt, corr_ans_txt;
        public ImageView result_status_color;


        public shareUserViewHolder(View v) {
            super(v);
            q_text = v.findViewById(R.id.result_question_rev);
            my_ans_txt = v.findViewById(R.id.result_my_answer_rev);
            corr_ans_txt = v.findViewById(R.id.result_correct_answer_rev);
            result_status_color = v.findViewById(R.id.result_remarks);


        }
    }

    public resultAdapter(ArrayList<take_quizItem> exampleList) {
        result_quiz_items = exampleList;
    }

    @Override
    public resultAdapter.shareUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_quiz_result, parent, false);
        resultAdapter.shareUserViewHolder evh = new resultAdapter.shareUserViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final resultAdapter.shareUserViewHolder holder, final int position) {
        take_quizItem take_quizItem = result_quiz_items.get(position);
        holder.q_text.setText(take_quizItem.getQuestion());
        holder.my_ans_txt.setText("My answer " + take_quizItem.getInputted_answer());
        holder.corr_ans_txt.setText("Correct answer " + take_quizItem.getCorrect_answer());
        if (take_quizItem.isCase_sensitive()){
            if (take_quizItem.getInputted_answer().equals(take_quizItem.getCorrect_answer())){
                //correct
                holder.result_status_color.setImageResource(R.color.colorCorrectAnswer);
            }else {
                holder.result_status_color.setImageResource(R.color.colorWrongAnswer);

            }
        }else{
            if (take_quizItem.getInputted_answer().toUpperCase().equals(take_quizItem.getCorrect_answer().toUpperCase())){
                // correct
                holder.result_status_color.setImageResource(R.color.colorCorrectAnswer);
            }else{
                holder.result_status_color.setImageResource(R.color.colorWrongAnswer);
            }
        }
//        holder.user_ed.setText(take_quizItem.getName());
//        holder.user_email.setText(take_quizItem.getEmail());
//        if (take_quizItem.getProvider_data().equals("facebook.com")) {
//            holder.user_provider.setImageResource(R.drawable.ic_baseline_facebook_24);
//        } else if (take_quizItem.getProvider_data().equals("google.com")) {
//            holder.user_provider.setImageResource(R.drawable.ic_google__g__logo);
//        }



    }

    @Override
    public int getItemCount() {
        return result_quiz_items.size();
    }

}
