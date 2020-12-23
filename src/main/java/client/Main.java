package client;
import core.*;
import core.question.Question;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
//        create quiz service
//        int num = 10;
//        QuizService qz = new QuizService();
//        RandomQuestionsGenerator rn = new RandomQuestionsGenerator();
//        List<Question> quiz = qz.createQuiz(rn.getQuestions(), num);
//
//        generate quiz pdf file
//        QuizToPdf pdf = new QuizToPdf("/home/zuka/სამუშაო მაგიდა", quiz);
//        pdf.makeQuizPdf();
//
//        convert questions to markup
//        QuestionsToText markup = new QuestionsToText("/home/zuka/სამუშაო მაგიდა/markup.txt", quiz);
//        markup.makeMarkupFile();

//        convert to markup (java basics)
//        QuestionsConverter q = new QuestionsConverter("/home/zuka/სამუშაო მაგიდა/Chapter01_Java_Basics.txt", "/home/zuka/სამუშაო მაგიდა/Answers_Chapter1.txt", "/home/zuka/სამუშაო მაგიდა/markup.txt");
//        q.convert();

        MarkupToQuestions mtq = new MarkupToQuestions("/home/zuka/სამუშაო მაგიდა/markup.txt");
        List<Question> ls = mtq.getQuestions();
//        QuizToPdfWithAnswers pdf = new QuizToPdfWithAnswers("/home/zuka/სამუშაო მაგიდა", ls);
//        pdf.makeQuizPdf();

//        Simulation of Quiz on localhost server (multiple users at the same time)
        try {
            ServerSocket ss = new ServerSocket(15000);
            ExecutorService pool = Executors.newCachedThreadPool();
            QuizService qz = new QuizService();
            int num = 5;
            while (true) {
                Socket socket = ss.accept();
                List<Question> quiz = qz.createQuiz(ls, num);
                List<String> answers = new ArrayList<>();
                pool.execute(() -> {
                            try {
                                serveClient(num, qz, quiz, answers, socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
//            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void serveClient(int num, QuizService qz, List<Question> quiz, List<String> answers, Socket socket) throws IOException {
        Scanner scanner = new Scanner(socket.getInputStream());
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("\nQuiz");
        pw.println("practice/exam");
        pw.flush();
        String str = scanner.nextLine();
        boolean isExam = false;
        if("exam".equals(str)){
            isExam = true;
        }else if(!"practice".equals(str)){
            pw.println("END");
            pw.flush();
            return;
        }
        for(int i = 0; i < quiz.size(); i++){
            pw.println();
            Question curr = quiz.get(i);
            pw.println(curr.getQuestion());
            List<String> possAnswers = curr.getPossibleAnswers();
            if(isExam){
                Collections.shuffle(possAnswers);
            }
            if(possAnswers!=null){
                char c = 'a';
                for(String ans : possAnswers){
                    pw.println(c + ")" + ans.substring(2));
                    pw.flush();
                    c++;
                }
            }
            pw.flush();
            answers.add(scanner.nextLine());
            if(!isExam){
                pw.println("\nCorrect answer: "+curr.getAnswer());
                pw.println("\nExplanation: "+curr.getExplanation());
            }
        }
        int score = qz.score(quiz,answers);
        pw.println("Your score is " + score + "/" + num);
        if(score>num/2){
            pw.println("You Passed!!!");
        }else{
            pw.println("You Failed :(");
        }
        pw.flush();
    }

}
