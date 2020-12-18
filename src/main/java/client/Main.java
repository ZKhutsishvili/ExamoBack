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

//        MarkupToQuestions mtq = new MarkupToQuestions("/home/zuka/სამუშაო მაგიდა/markup.txt");
//        List<Question> ls = mtq.getQuestions();
//        QuizToPdf pdf = new QuizToPdf("/home/zuka/სამუშაო მაგიდა", ls);
//        pdf.makeQuizPdf();

//        Simulation of Quiz on localhost server (multiple users at the same time)
//        try {
//            ServerSocket ss = new ServerSocket(15000);
//            ExecutorService pool = Executors.newCachedThreadPool();
//            while (true) {
//                Socket socket = ss.accept();
//                List<Question> quiz = qz.createQuiz(rn.getQuestions(), num);
//                List<String> answers = new ArrayList<>();
//                pool.execute(() -> {
//                            try {
//                                serveClient(num, qz, quiz, answers, socket);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                );
//            }
////            ss.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void serveClient(int num, QuizService qz, List<Question> quiz, List<String> answers, Socket socket) throws IOException {
        Scanner scanner = new Scanner(socket.getInputStream());
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("\nQuiz");
        pw.flush();
        for(int i = 0; i < quiz.size(); i++){
            pw.println();
            Question curr = quiz.get(i);
            pw.println(quiz.get(i).getQuestion());
            List<String> possAnswers = curr.getPossibleAnswers();
            if(possAnswers!=null){
                char c = 'a';
                for(String ans : possAnswers){
                    pw.println(c + ")" + ans);
                    pw.flush();
                    c++;
                }
            }
            pw.flush();
            answers.add(scanner.next());
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
