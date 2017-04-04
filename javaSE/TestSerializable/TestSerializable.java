package TestSerializable;

import java.io.*;

class People implements Serializable{//也要实现Serializable接口，否则Account类不能序列化
    String name;
    int age;
    public People(String name,int age){
        this.name=name;
        this.age=age;
    }
    public void set_age(int age){
        this.age=age;
    }
    public String show(){
        return " name="+name+" age="+age+" ";
    }
}

class Account implements Serializable{//Serializable接口是一个标记接口，没有需要实现的方法
    public int id;
    public People people;
    private transient int password;//transient关键字，表明该成员不参加序列化与反序列化。输出的时候用int的默认值
    public Account(int id,People people,int password){
        this.id=id;
        this.people=people;
        this.password=password;
    }
    public void set_People(People people){
        this.people=people;
    }
    public void show(){
        System.out.println("id="+id+" password="+password+people.show());
    }

    /*
    //自定义序列化与反序列化  自定义的函数明只能用writeObject和readObject 源码没读懂。。。
    private void writeObject(java.io.ObjectOutputStream oos) throws IOException{
        oos.writeInt(id*100);
        oos.writeObject(people);
    }
    private void readObject(java.io.ObjectInputStream ois) throws Exception{
        this.id=ois.readInt();
        this.people=(People)ois.readObject();
    }
    */
}

public class TestSerializable {
    public static void main(String args[]){
        try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("account.text"));
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("account.text")))
                //try-with-resources  申请的资源会在try-catch-finally语句结束后自动关闭。
                //ObjectOutputStream类提供序列化方法writeObject(),ObjectOutputStream是处理流，要基于节点流FileOutputStream
                //ObjectInputStream类提供反序列化方法readObject(),ObjectInputStream是处理流，要基于节点流FileInputStream

        {
            People wcc=new People("wcc",18);
            People tjc=new People("tjc",19);
            Account account1=new Account(1,wcc,123456);
            oos.writeObject(account1);//序列化 序列化account1的过程中，包含对wcc的序列化，称为"递归序列化"
            ((Account)ois.readObject()).show();//反序列化，要类型转换
            //ois.readObject().show();  为什么这样不行，父类引用不是可以调用子类方法吗？

            //多次序列化一个可变类的对象，即使对象成员已经改变，也只会在第一次序列化时把该Java对象转换成字节序列输出。
            wcc.set_age(24);
            oos.writeObject(account1);
            ((Account)ois.readObject()).show();
            //同理
            account1.set_People(tjc);
            oos.writeObject(account1);
            ((Account)ois.readObject()).show();


        }catch (Exception ex){//用的是基类Exception，可以捕捉所有的异常，这样处理最简单，也相当于就没处理。。
            ex.printStackTrace();
        }

    }

}
