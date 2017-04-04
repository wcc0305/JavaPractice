package TestThread;

import sun.awt.geom.AreaOp;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class FirstThread extends Thread{//继承Thread，用Thread子类来创建线程实例
    private int i;
    public void run(){//重写run()方法
        for(;i<100;i++){
            System.out.println(getName()+" "+i);
        }
    }
}

class TestRunnable implements Runnable{//用实现Runnable接口的类创建实例，作为target传入Thread（）构造函数，创建线程实例
    private int i;
    public void run(){//重写run()方法
        for(;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);//Thread.currentThread()方法
        }
    }
}




public class TestThread1 {
    public static void main(String args[]) {
        TestRunnable tr1=new TestRunnable();//实现了Runnable接口的实例，是Thread的target
        TestRunnable tr2=new TestRunnable();

        //第三种创建线程的方式，还没搞懂。。。
        FutureTask<Integer> task=new FutureTask<Integer>((Callable<Integer>)()->{
            int i=0;
            for(;i<100;i++){
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
            return i;
        });


        for(int i=0;i<100;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
            if(i==20){
                //多线程方法1
                new FirstThread().start();
                new FirstThread().start();
                //多线程方法2
                new Thread(tr1).start();//所有以tr1为target的线程实例，共享TestRunnable类里的非静态成员变量
                new Thread(tr1).start();
                new Thread(tr2).start();//以tr2为target的线程实例，不与以tr1为target的线程实例共享非静态成员变量
                        // 但静态变量则是共享的
                //多线程方法3
                new Thread(task).start();
                new Thread(task).start();
                try{
                    System.out.println(task.get());
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}