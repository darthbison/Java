package states;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class QuizGame {


    private static BufferedReader is;
    private static int score = 0;

    private Map<String,Boolean> questions = new HashMap<>();

    enum Answers { TRUE, FALSE }

    public static void main(String[] args) throws IOException {


        display("Random Quiz!\nPlease answer True or False!\n");
        QuizGame qg = new QuizGame();
        qg.createQuestions();
        qg.beginQuiz();

        display("Final Score: " + score);
        is.close();
    }

    private void createQuestions()
    {
        questions.put("Is the sky blue?\n", true);
        questions.put("Golden State will be champs again right?\n",false);
    }

    private void computeScore(boolean correct)
    {
        if (correct) {
            score = incrementScore(score);
        } else {
            score = decrementScore(score);
        }
    }

    private int incrementScore(int currentScore) {
        score = currentScore + 1;
        display("Correct! Plus 1");
        return score;
    }

    private int decrementScore(int currentScore) {
        score = currentScore - 1;
        display("Incorrect! Minus 1...");
        return score;
    }

    private static void display(String msg) {
        System.out.println(msg);
    }

    private void beginQuiz() throws IOException {

        int questionNumber = 0;
        while (questionNumber != questions.size())
        {
            if (askQuestion(questionNumber))
                questionNumber += 1;

        }


    }

    private boolean askQuestion(int index) throws IOException {
        is = new BufferedReader(new InputStreamReader(System.in));

        List<String> keys = new ArrayList(questions.keySet());
        String question = keys.get(index);

        boolean correctAns = questions.get(question);
        display(question);

        Optional<String> line = Optional.of(is.readLine());

        try {
            if (line.isPresent()) {
                Answers a = Answers.valueOf(line.get().toUpperCase());

                switch (a) {
                    case TRUE:
                    case FALSE:
                }
                boolean givenAnswer = Boolean.parseBoolean(line.get().toLowerCase());
                boolean finalAnswer = (correctAns == givenAnswer);
                computeScore(finalAnswer);

            }
        }catch (IllegalArgumentException e) {
            display("Invalid Answer...Please answer true or false");
            askQuestion(index);

        }
        return true;

    }

}
