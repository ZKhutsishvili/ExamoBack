package core.question;

import java.util.Collections;
import java.util.List;

public class MultipleChoice implements Question{
    String question, answer;
    List<String> possibleAnswers;

    public MultipleChoice(String question, String answer, List<String> possibleAnswers){
        this.question = question;
        this.answer = answer;
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public List<String> getAnswers() {
        return null;
    }

    @Override
    public List<String> getPossibleAnswers() {
        Collections.shuffle(possibleAnswers);
        return possibleAnswers;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getType() {
        return "multiple choice";
    }
    @Override
    public String toText() {
        String str = "multiple choice\n"+question+"://\n";
        int len = possibleAnswers.size();
        for(int i = 0; i < len; i++){
            String ans = possibleAnswers.get(i);
            str += ans+"@";
            if(ans.equals(answer)){
                str += "1.0\n";
            }else{
                str += "0.0\n";
            }
        }
        return str;
    }
}
