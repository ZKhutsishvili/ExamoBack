package core;

import core.question.MultipleChoice;
import core.question.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionsConverter {

    private final String questionsPath;
    private final String answersPath;
    private final String toPath;

    public QuestionsConverter(String questionsPath, String answersPath, String toPath){
        this.questionsPath = questionsPath;
        this.answersPath = answersPath;
        this.toPath = toPath;
    }

    public void convert(){
        List<Question> questions = new ArrayList<Question>();
        InputStream questionsFile = null;
        InputStream answersFile = null;
        try {
            questionsFile = new FileInputStream(questionsPath);
            answersFile = new FileInputStream(answersPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanQuestions = new Scanner(questionsFile);
        Scanner scanAnswers = new Scanner(answersFile);
        scanAnswers.nextLine();
        //skip description
        for(int i = 0; i < 10; i++){
            scanQuestions.nextLine();
        }
        String currLine="";
        while(scanQuestions.hasNextLine()){
            String question = currLine.substring(currLine.indexOf(" ")+1);
            while(true){
                currLine = scanQuestions.nextLine();
                //skip empty lines
                if(currLine.length()<3){
                    continue;
                }
                if(!Character.isDigit(currLine.charAt(0))){
                    break;
                }
                question += currLine.substring(currLine.indexOf(" ")+1)+"\n";
            }
            question = question.substring(0, question.length()-1);
            List<String> answers = new ArrayList<>();
            answers.add(currLine.substring(2));
            while(true){
                if(!scanQuestions.hasNextLine()){
                    break;
                }
                currLine = scanQuestions.nextLine();
                if(Character.isDigit(currLine.charAt(0))){
                    break;
                }
                answers.add(currLine.substring(2));
            }
            String ans = scanAnswers.nextLine();
            char correct = ans.charAt(ans.indexOf(".")-1);
            int answerIndex = correct-'A';
            Question current;
            if(answers.size()==8){
                List<String> newAnswers = new ArrayList<>();
                for(int i = 0; i < 4; i++){
                    newAnswers.add(answers.get(2*i)+"\n"+answers.get(2*i+1));
                }
                answers = newAnswers;
            }
            current = new MultipleChoice(question, answers.get(answerIndex), answers);
            questions.add(current);
        }
        QuestionsToText qtt = new QuestionsToText(toPath, questions);
        qtt.makeMarkupFile();
    }
}
