package core;

import com.itextpdf.text.Document;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import core.question.Question;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class QuizToPdf {
    String path;
    List<Question> quiz;

    public QuizToPdf(String path, List<Question> quiz){
        this.path = path;
        this.quiz = quiz;
    }

    public void makeQuizPdf(){
        try{
            Document doc = new Document();
            FileOutputStream file = new FileOutputStream(path+"/Quiz.pdf");
            PdfWriter.getInstance(doc, file);
            doc.open();
            Paragraph par = new Paragraph("Quiz\n\n");
            for(int i = 0; i < quiz.size(); i++){
                Question curr = quiz.get(i);
                par.add(i+1+". ");
                par.add(curr.getQuestion()+"\n");
                List<String> posAnswers = curr.getPossibleAnswers();
                if(posAnswers!=null){
                    char c = 'a';
                    for(String ans : posAnswers){
                        par.add(c+") "+ans+"\n");
                        c++;
                    }
                }else{
                    par.add("Answer: \n");
                }
                par.add("\n");
            }
            doc.add(par);
            doc.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
