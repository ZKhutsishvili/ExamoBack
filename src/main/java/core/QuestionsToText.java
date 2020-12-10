package core;

import core.question.Question;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class QuestionsToText {
    String path;
    List<Question> questions;

    public QuestionsToText(String path, List<Question> questions){
        this.path = path;
        this.questions = questions;
    }

    public void makeMarkupFile(){
        try {
            DataOutputStream data = new DataOutputStream(new FileOutputStream(path+"/data.txt"));
            for(Question q : questions){
                data.writeBytes(q.toText()+"~~~\n");
            }
            data.close();
        } catch (Exception e) {
            System.out.println(e);;
        }
    }

}
