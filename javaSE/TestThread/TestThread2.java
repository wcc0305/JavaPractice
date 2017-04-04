package TestThread;

class NumThread extends Thread{
    private Object obj;
    public NumThread(Object obj){
        this.obj=obj;
    }
    public void run(){
        synchronized (obj){
            for(int i=1;i<=26;i++) {
                System.out.println(i);
                obj.notifyAll();
                //Thread.yield();    yield()不能实现字母数字的相间打印，
                // yield()只是把cpu占有权交出去，可能交出去之后，重新分配又还回来了，是说不准的
                try {
                    //Thread.sleep(5);
                    obj.wait();//使用wait(),notify()方法控制线程，更为准确
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

class CharRunnable implements Runnable{
    private Object obj;
    public CharRunnable(Object obj) {
        this.obj = obj;
    }
    public void run() {
        synchronized(obj) {//同步代码块，同步监视器是obj
            for (int i = 0; i < 26; i++) {
                System.out.println((char) ('a' + i));
                //Thread.yield();
                obj.notifyAll();//若使用同步方法，同步监视器默认是this，notifyAll()前可省略
                try {
                    //Thread.sleep(5);
                    obj.wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

public class TestThread2 {
    public static void main(String args[]){
        Object obj=new Object();//obj是同步监视器
        CharRunnable target=new CharRunnable(obj);
        new NumThread(obj).start();
        new Thread(target).start();
    }
}
