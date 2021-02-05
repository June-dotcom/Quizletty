package edu.ucucite.quizlettyn.fragment_nav.home.preloadedquizmiscs;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

import edu.ucucite.quizlettyn.R;
import edu.ucucite.quizlettyn.takequiz.take_quizItem;


public class preloaded_quizzes_sets {
    public static ArrayList<take_quizItem> vocab_test(Context context) {
        Resources res = context.getResources();

        String[] question_set = res.getStringArray(R.array.vocab_questions1);
        String[] answer_set = res.getStringArray(R.array.vocab_answers1);
        ArrayList<take_quizItem> vocab_test = new ArrayList<>();

        vocab_test.add(new take_quizItem(1, question_set[0], "", answer_set[0], false));
        vocab_test.add(new take_quizItem(2, question_set[1], "", answer_set[1], false));
        vocab_test.add(new take_quizItem(3, question_set[2], "", answer_set[2], false));
        vocab_test.add(new take_quizItem(4, question_set[3], "", answer_set[3], false));
        vocab_test.add(new take_quizItem(5, question_set[4], "", answer_set[4], false));
        vocab_test.add(new take_quizItem(6, question_set[5], "", answer_set[5], false));
        vocab_test.add(new take_quizItem(7, question_set[6], "", answer_set[6], false));
        vocab_test.add(new take_quizItem(8, question_set[7], "", answer_set[7], false));
        vocab_test.add(new take_quizItem(9, question_set[8], "", answer_set[8], false));
        vocab_test.add(new take_quizItem(10, question_set[9], "", answer_set[9], false));

//        for (int x = 0; x < question_set.length; x++) {
//            vocab_test.add(new take_quizItem(x+1, question_set[x], "", answer_set[x], false));
//        }
        return vocab_test;
    }

    public static ArrayList<take_quizItem> sci_quiz(Context context) {
        ArrayList<take_quizItem> sci_quiz = new ArrayList<>();
        Resources res = context.getResources();
        String[] question_set = res.getStringArray(R.array.sci_questions1);
        String[] answer_set = res.getStringArray(R.array.sci_answers1);
        for (int x = 0; x < 10; x++) {
            sci_quiz.add(new take_quizItem(x+1, question_set[x], "", answer_set[x], false));
        }
        return sci_quiz;
    }

    public static ArrayList<take_quizItem> math_quiz(Context context) {
        ArrayList<take_quizItem> math_quiz = new ArrayList<>();
        Resources res = context.getResources();
        String[] question_set = res.getStringArray(R.array.math_questions1);
        String[] answer_set = res.getStringArray(R.array.math_answers1);
        for (int x = 0; x < question_set.length; x++) {
            math_quiz.add(new take_quizItem(x+1, question_set[x], "", answer_set[x], false));
        }
        return math_quiz;
    }

    public static ArrayList<take_quizItem> trivia_quiz(Context context) {
        ArrayList<take_quizItem> trivia_quiz = new ArrayList<>();
        Resources res = context.getResources();
        String[] question_set = res.getStringArray(R.array.trivia_questions1);
        String[] answer_set = res.getStringArray(R.array.trivia_answers1);
        for (int x = 0; x < question_set.length; x++) {
            trivia_quiz.add(new take_quizItem(x+1, question_set[x], "", answer_set[x], false));
        }
        return trivia_quiz;
    }
}
