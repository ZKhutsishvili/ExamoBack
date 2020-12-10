package core.question;

import java.util.List;

public interface Question {
    String getQuestion();
    List<String> getAnswers();
    List<String> getPossibleAnswers();
    String getAnswer();
    String getType();
    String toText();
}