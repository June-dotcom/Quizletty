package edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.takequiz.take_quizItem;

public class preloaded_quiz_obj {
    private String quiz_name;
    private String quiz_desc;
    private ArrayList<take_quizItem> quizItems;

    public preloaded_quiz_obj(String quiz_name, String quiz_desc, ArrayList<take_quizItem> quizItems) {
        this.quiz_name = quiz_name;
        this.quiz_desc = quiz_desc;
        this.quizItems = quizItems;
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

    public ArrayList<take_quizItem> getQuizItems() {
        return quizItems;
    }

    public void setQuizItems(ArrayList<take_quizItem> quizItems) {
        this.quizItems = quizItems;
    }
}
