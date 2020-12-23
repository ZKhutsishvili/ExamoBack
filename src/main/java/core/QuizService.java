package core;

import core.question.MultipleAnswer;
import core.question.Question;

import java.util.*;

public class QuizService {

    public List<Question> createQuiz(List<Question> questions, int num) {
        Set<Question> distinct = new HashSet<>();
        List<Question> quiz = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < num; i++){
            int randIndex = rand.nextInt(questions.size());
            while(distinct.contains(questions.get(randIndex))){
                randIndex++;
            }
            Question currentQuestion = questions.get(randIndex);
            distinct.add(currentQuestion);
            quiz.add(currentQuestion);
        }
        return quiz;
    }


    public int score(List<Question> quiz, List<String> answers) {
        int score = 0;
        for (int i = 0; i < quiz.size(); i++){
            Question question = quiz.get(i);
            if(question instanceof MultipleAnswer){
                List<String> userAnswers = Arrays.asList(answers.get(i).split("&"));
                List<String> correctAnswers = question.getAnswers();
                int count = 0;
                for(String ans : userAnswers){
                    for(String correct : correctAnswers){
                        if(ans.equals(correct)){
                            count++;
                            break;
                        }
                    }
                }
                if(count == userAnswers.size())
                    score++;
            }else {
                if (question.getAnswer().substring(2).equals(answers.get(i)))
                    score++;
            }
        }
        return score;
    }
}
