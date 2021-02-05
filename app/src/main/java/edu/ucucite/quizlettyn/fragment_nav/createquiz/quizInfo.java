package edu.ucucite.quizlettyn.fragment_nav.createquiz;

public class quizInfo {
    private String quiz_code;
    private String quiz_name;
    private String quiz_desc;
    private String quiz_creator_uid;
    private String quiz_creator_name;
    private String quiz_item_count;

    public quizInfo(String quiz_code, String quiz_name, String quiz_desc, String quiz_creator_uid, String quiz_creator_name, String quiz_item_count) {
        this.quiz_code = quiz_code;
        this.quiz_name = quiz_name;
        this.quiz_desc = quiz_desc;
        this.quiz_creator_uid = quiz_creator_uid;
        this.quiz_creator_name = quiz_creator_name;
        this.quiz_item_count = quiz_item_count;
    }

    public String getQuiz_code() {
        return quiz_code;
    }

    public void setQuiz_code(String quiz_code) {
        this.quiz_code = quiz_code;
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

    public String getQuiz_creator_uid() {
        return quiz_creator_uid;
    }

    public void setQuiz_creator_uid(String quiz_creator_uid) {
        this.quiz_creator_uid = quiz_creator_uid;
    }

    public String getQuiz_creator_name() {
        return quiz_creator_name;
    }

    public void setQuiz_creator_name(String quiz_creator_name) {
        this.quiz_creator_name = quiz_creator_name;
    }

    public String getQuiz_item_count() {
        return quiz_item_count;
    }

    public void setQuiz_item_count(String quiz_item_count) {
        this.quiz_item_count = quiz_item_count;
    }
}
