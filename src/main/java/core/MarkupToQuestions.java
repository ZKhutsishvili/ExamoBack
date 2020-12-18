package core;

import core.question.MultipleChoice;
import core.question.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarkupToQuestions {
    private String path;

    public MarkupToQuestions(String path){
        this.path = path;
    }

    public List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        InputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String type = scanner.nextLine();
            String question = scanner.nextLine();
            while(!question.substring(question.length()-3).equals("://")){
                question += "\n"+scanner.nextLine();
            }
            question = question.substring(0, question.length()-3);
            List<String> possibleAnswers = new ArrayList<>();
            String answer = "";
            String currAnswer = scanner.nextLine();
            while (!currAnswer.equals("???")){
                int index = currAnswer.length()-4;
                if(currAnswer.charAt(index)!='@'){
                    currAnswer += "\n"+scanner.nextLine();
                    continue;
                }
                String parsed = currAnswer.substring(0, index);
                possibleAnswers.add(parsed);
                if(currAnswer.charAt(index+1)=='1'){
                    answer = parsed;
                }
                currAnswer = scanner.nextLine();
            }
            String explanation = "";
            String currLine = scanner.nextLine();
            while (!currLine.equals("~~~")){
                explanation += currLine;
                currLine = scanner.nextLine();
            }
            Question multChoice = new MultipleChoice(question, answer, possibleAnswers, explanation);
            questions.add(multChoice);
        }
        return questions;
    }
}
