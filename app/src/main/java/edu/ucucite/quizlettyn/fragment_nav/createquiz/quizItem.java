package edu.ucucite.quizlettyn.fragment_nav.createquiz;

public class quizItem {
    private String question;
    private String answer;
    private boolean case_sensitive;

    public quizItem(String question, String answer, boolean case_sensitive) {
        this.question = question;
        this.answer = answer;
        this.case_sensitive = case_sensitive;
    }

    @Override
    public String toString() {
        return "quizItem{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", case_sensitive=" + case_sensitive +
                '}';
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCase_sensitive() {
        return case_sensitive;
    }

    public void setCase_sensitive(boolean case_sensitive) {
        this.case_sensitive = case_sensitive;
    }
}
