package core.question;

import core.question.Question;

import java.util.List;

public class BlankQuestion implements Question {
    String question, answer;
    List<String> possibleAnswers;
    public BlankQuestion(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public BlankQuestion(String question, String answer, List<String> possibleAnswers){
        this.question = question;
        this.answer = answer;
        this.possibleAnswers = possibleAnswers;
    }

    @Override
    public List<String> getAnswers() {
        return null;
    }

    @Override
    public List<String> getPossibleAnswers() {
        return null;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getType() {
        return "blank";
    }

    @Override
    public String toText() {
        return "blank\n"+question+"://\n"+answer+"@=\n";
    }


}
