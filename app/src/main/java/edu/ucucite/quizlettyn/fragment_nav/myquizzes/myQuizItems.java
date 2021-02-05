package edu.ucucite.quizlettyn.fragment_nav.myquizzes;

import java.io.Serializable;

public class myQuizItems implements Serializable {
    private int position;
    private String quiz_key;
    private String quiz_name;
    private String quiz_desc;
    private String quiz_code;
    private String quiz_item_count;

    public myQuizItems(int position, String quiz_key ,String quiz_name, String quiz_desc, String quiz_code, String quiz_items_count) {
        this.position = position;
        this.quiz_key = quiz_key;
        this.quiz_name = quiz_name;
        this.quiz_desc = quiz_desc;
        this.quiz_code = quiz_code;
        this.quiz_item_count = quiz_items_count;

    }

    public String getQuiz_key() {
        return quiz_key;
    }

    public void setQuiz_key(String quiz_key) {
        this.quiz_key = quiz_key;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_desc() {
        return quiz_desc;
    }

    public void setQuiz_desc(String quiz_desc) {
        this.quiz_desc = quiz_desc;
    }

    public String getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(String quiz_code) {
        this.quiz_code = quiz_code;
    }

    public String getQuiz_item_count() {
        return quiz_item_count;
    }

    public void setQuiz_item_count(String quiz_item_count) {
        this.quiz_item_count = quiz_item_count;
    }
}
