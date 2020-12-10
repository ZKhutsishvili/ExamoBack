package core;

import core.question.BlankQuestion;
import core.question.MultipleAnswer;
import core.question.MultipleChoice;
import core.question.Question;

import java.util.*;

public class RandomQuestionsGenerator {
    public List<Question> getQuestions() {
        List<Question> data = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int a = rand.nextInt(20);
            int b = rand.nextInt(10);
            String sum = Integer.toString(a + b);
            String sub = Integer.toString(a - b);
            String prod = Integer.toString(a * b);
            String[] arr = {sum, sub, prod};
            List<String> ls = Arrays.asList(arr);
            data.add(new MultipleChoice(a + "+" + b + "=?", sum, ls));
            data.add(new MultipleChoice(a + "-" + b + "=?", sub, ls));
            data.add(new MultipleChoice(a + "*" + b + "=?", prod, ls));
            if (b != 0) {
                data.add(new BlankQuestion(a + "/" + b + "=?", Integer.toString(a / b)));
                data.add(new BlankQuestion(a + "%" + b + "=?", Integer.toString(a % b)));
            }
            String[] arr2 = {a + "", b + "", rand.nextInt(1000) + "", rand.nextInt(1000) + ""};
            List<String> ls2 = Arrays.asList(arr2);
            String[] arr3 = {a + "", b + ""};
            List<String> ls3 = Arrays.asList(arr3);
            data.add(new MultipleAnswer(("Choose these numbers " + a + " " + b), ls3, ls2));
        }
        Collections.shuffle(data);
        return data;
    }
}
