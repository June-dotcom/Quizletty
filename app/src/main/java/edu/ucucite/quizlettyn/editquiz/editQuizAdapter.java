package edu.ucucite.quizlettyn.editquiz;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;

public class editQuizAdapter extends RecyclerView.Adapter<editQuizAdapter.createQuizViewHolder> {
    private ArrayList<editquizItems> editquizItemsArrayList;
    private editQuizAdapter.OnItemClickListener qListener;


    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onSetQuestion(int position, String question);
        void onSetAnswer(int position, String answer);
        void onSetIsCaseSensitive(int position, boolean option);
    }

    public void setOnItemClickListener(editQuizAdapter.OnItemClickListener listener) {
        qListener = listener;
    }

    public static class createQuizViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText question_ed;
        public TextInputEditText answer_ed;
        public CheckBox is_case_sensitive;
        public ImageView delete_item_btn;

        public createQuizViewHolder(View v, final editQuizAdapter.OnItemClickListener listener) {
            super(v);
            question_ed = v.findViewById(R.id.question_TextField);
            answer_ed = v.findViewById(R.id.answer_take_quiz);
            delete_item_btn = v.findViewById(R.id.delete_button);
            is_case_sensitive = v.findViewById(R.id.case_sensitive_option);

            is_case_sensitive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    listener.onSetIsCaseSensitive(getAdapterPosition(), isChecked);
                }
            });

            delete_item_btn.setOnClickListener(new View.OnClickListener() {
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

            question_ed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    listener.onSetQuestion(position, question_ed.getText().toString());
                }
            });

            answer_ed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    listener.onSetAnswer(position, answer_ed.getText().toString());
                }
            });


        }
    }

    public editQuizAdapter(ArrayList<editquizItems> exampleList) {
        editquizItemsArrayList = exampleList;
    }

    @Override
    public editQuizAdapter.createQuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_create_quiz_item, parent, false);
        editQuizAdapter.createQuizViewHolder evh = new editQuizAdapter.createQuizViewHolder(v, qListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(final editQuizAdapter.createQuizViewHolder holder, final int position) {
        editquizItems editquizItems = editquizItemsArrayList.get(position);
        holder.question_ed.setText(editquizItems.getQuestion());
        holder.answer_ed.setText(editquizItems.getAnswer());
        holder.is_case_sensitive.setChecked(editquizItems.isCase_sensitive());
    }

    @Override
    public int getItemCount() {
        return editquizItemsArrayList.size();
    }

}