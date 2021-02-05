package edu.ucucite.quizlettyn.takequiz;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;

public class TakeQuizAdapter extends RecyclerView.Adapter<TakeQuizAdapter.MyViewHolder> {
    private ArrayList<take_quizItem> TakeQuizItemsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView question;
        public EditText answer;


    public MyViewHolder(View v) {
            super(v);
            question = v.findViewById(R.id.question_take_quiz);
            answer = v.findViewById(R.id.answer_take_quiz);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TakeQuizAdapter(ArrayList<take_quizItem> myDataset) {
        TakeQuizItemsList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TakeQuizAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_take_quiz_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.question.setText(TakeQuizItemsList.get(position).getQuiznum() + ". "
                + TakeQuizItemsList.get(position).getQuestion());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return TakeQuizItemsList.size();
    }
}