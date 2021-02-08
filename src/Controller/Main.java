package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socialNetwork.People;
import socialNetwork.Pttrns.Patterns;
import socialNetwork.SerialData;
import socialNetwork.Streams;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static List<People> pplList = new ArrayList<People>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        new SerialData().main(); // сериализация данных

        // открываем форму авторизации
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/auth.fxml"));
        primaryStage.setTitle("Проект \"Знакомства\"");
        primaryStage.setScene(new Scene(root, 895, 370));
        primaryStage.show();

        // Stream API
        Streams.start();

        // Паттерны (3)
        Patterns.singleton();
        Patterns.adapter();
        Patterns.mediator();


        // тут код не удаляй, он нужен для объяснения, но этот текст можешь удалить (нет)(да)(жопа)
        // Synchronized
        /*
        public class Program {
            public static void main(String[] args) {

                CommonResource commonResource= new CommonResource();
                for (int i = 1; i < 6; i++){

                    Thread t = new Thread(new CountThread(commonResource));
                    t.setName("Thread "+ i);
                    t.start();
                }
            }
        }

        class CommonResource{
            int x;
            synchronized void increment(){
                x=1;
                for (int i = 1; i < 5; i++){
                    System.out.printf("%s %d \n", Thread.currentThread().getName(), x);
                    x++;
                    try{
                        Thread.sleep(100);
                    }
                    catch(InterruptedException e){}
                }
            }
        }

        class CountThread implements Runnable{
            CommonResource res;
            CountThread(CommonResource res){
                this.res=res;
            }

            public void run(){
                res.increment();
            }
        }
        */

        // Thread, ThreadPool
        /*
        class EggVoice extends Thread
        {
            @Override
            public void run()
            {
                for(int i = 0; i < 5; i++)
                {
                    try{
                        sleep(1000);		//Приостанавливает поток на 1 секунду
                    }catch(InterruptedException e){}

                    System.out.println("яйцо!");
                }
                //Слово «яйцо» сказано 5 раз
            }
        }

        public class ChickenVoice	//Класс с методом main()
        {
            static EggVoice mAnotherOpinion;	//Побочный поток

            public static void main(String[] args)
            {
                mAnotherOpinion = new EggVoice();	//Создание потока
                System.out.println("Спор начат...");
                mAnotherOpinion.start(); 			//Запуск потока

                for(int i = 0; i < 5; i++)
                {
                    try{
                        Thread.sleep(1000);		//Приостанавливает поток на 1 секунду
                    }catch(InterruptedException e){}

                    System.out.println("курица!");
                }

                //Слово «курица» сказано 5 раз

                if(mAnotherOpinion.isAlive())	//Если оппонент еще не сказал последнее слово
                {
                    try{
                        mAnotherOpinion.join();	//Подождать пока оппонент закончит высказываться.
                    }catch(InterruptedException e){}

                    System.out.println("Первым появилось яйцо!");
                }
                else	//если оппонент уже закончил высказываться
                {
                    System.out.println("Первой появилась курица!");
                }
                System.out.println("Спор закончен!");
            }
        }
        */

        // Executor, ExecutorService
        /*
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            service.submit(new Runnable() {
                public void run() {
                    // snip... piece of code
                }
            });
        }
        */
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static int rnd(int max) { return (int) (Math.random() * ++max); }

}
