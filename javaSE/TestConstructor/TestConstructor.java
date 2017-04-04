package TestConstructor;

import java.util.Stack;

//构造器集成，与super、this关键字
class Student{
    public String name;
    public int age;
    public Student(String name){//只要提供了一个自定义的构造函数，默认的无参构造函数就消失了！！
        this.name=name;
        System.out.println("父类有参构造器，my name is "+name);
    }
    public Student(){
        System.out.println("父类无参构造器");
    }
}
//子类构造器，总会调用一次父类构造器,分三种情况
class Boy extends Student{
    public Boy(){}//情况1，子类构造器中既无super也无this，隐式调用父类中无参构造器
    public Boy(String name){//情况2，子类构造器中super，调用父类中对应参数的构造器
        super(name);//super第二种用处，调用父类的构造函数。super第一种用处是指向父类成员
        System.out.println("子类有参构造器1,my name is "+name);
    }
    public Boy(String name,int age){//情况3，子类构造器中this，调用子类中对应参数的构造器
        this(name);//this第二种用处，指向当前类的构造函数
        this.age=age;//this第一种用处，指向当前对象
        System.out.println("子类有参构造器2,my name is "+name+". i am "+age+"-year-old!");
    }

}


public class TestConstructor {
    public static void main(String args[]){
        Boy gg=new Boy();
        System.out.println();
        Boy hehe=new Boy("hehe");
        System.out.println();
        Boy wcc=new Boy("wcc",24);
    }

}
