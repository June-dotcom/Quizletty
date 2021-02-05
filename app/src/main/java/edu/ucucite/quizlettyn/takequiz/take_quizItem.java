package edu.ucucite.quizlettyn.takequiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class take_quizItem implements Parcelable {
    private int quiznum;
    private String question;
    private String inputted_answer;
    private String correct_answer;
    private boolean case_sensitive;

    @Override
    public String toString() {
        return "take_quizItem{" +
                "quiznum=" + quiznum +
                ", question='" + question + '\'' +
                ", inputted_answer='" + inputted_answer + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", case_sensitive=" + case_sensitive +
                '}';
    }

    public take_quizItem(int quiznum, String question, String inputted_answer, String correct_answer, boolean case_sensitive) {
        this.quiznum = quiznum;
        this.question = question;
        this.inputted_answer = inputted_answer;
        this.correct_answer = correct_answer;
        this.case_sensitive = case_sensitive;
    }

    protected take_quizItem(Parcel in) {
        quiznum = in.readInt();
        question = in.readString();
        inputted_answer = in.readString();
        correct_answer = in.readString();
        case_sensitive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quiznum);
        dest.writeString(question);
        dest.writeString(inputted_answer);
        dest.writeString(correct_answer);
        dest.writeByte((byte) (case_sensitive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<take_quizItem> CREATOR = new Creator<take_quizItem>() {
        @Override
        public take_quizItem createFromParcel(Parcel in) {
            return new take_quizItem(in);
        }

        @Override
        public take_quizItem[] newArray(int size) {
            return new take_quizItem[size];
        }
    };

    public int getQuiznum() {
        return quiznum;
    }

    public void setQuiznum(int quiznum) {
        this.quiznum = quiznum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInputted_answer() {
        return inputted_answer;
    }

    public void setInputted_answer(String inputted_answer) {
        this.inputted_answer = inputted_answer;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public boolean isCase_sensitive() {
        return case_sensitive;
    }

    public void setCase_sensitive(boolean case_sensitive) {
        this.case_sensitive = case_sensitive;
    }
}
