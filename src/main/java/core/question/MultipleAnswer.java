package core.question;

import java.util.Collections;
import java.util.List;

public class MultipleAnswer implements Question{

    String question, answer;
    List<String> possibleAnswers, answers;
    public MultipleAnswer(String question, List<String> answers, List<String> possibleAnswers){
        this.question = question;
        this.answers = answers;
        this.possibleAnswers = possibleAnswers;
    }


    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public List<String> getPossibleAnswers() {
        Collections.shuffle(possibleAnswers);
        return possibleAnswers;
    }

    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public String getType() {
        return "multiple answer";
    }

    @Override
    public String toText() {
        String str = "multiple answer\n"+question+"://\n";
        int len = possibleAnswers.size();
        for(int i = 0; i < len; i++){
            String ans = possibleAnswers.get(i);
            str += ans+"@";
            if(answers.contains(ans)){
                str += (double)1/answers.size()+"\n";
            }else{
                str += "0.0\n";
            }
        }
        return str;
    }

}
