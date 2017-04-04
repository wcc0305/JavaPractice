package TestPolymorphism;

interface people{
    void fun();//接口中的方法，默认都是public
}

class student implements people{//父类
    public void fun(){//public 关键字不能省，接口中的方法，自动都是public。实现接口中的方法，如果省略，默认是default（同包可见），级别严于public，是不可以的
        System.out.println("i am a student");
    }
    void fun(int no){//重载
        System.out.println("i am student "+no);
    }
}

class boy_student extends student{//子类
    public void fun(){//重写
        System.out.println("i am a boy student");
    }
    void show(){
        System.out.println("hehe");
    }
}

public class TestPolymorphism{
    public static void main(String args[]){
        people aa=new student();//接口引用指向对象，接口回调，aa只能调用people里的方法(!!)，具体实现到student类里去找
        student bb=new boy_student();//父类引用指向子类对象，向上转型，调用方法时首先在boy_student里找，没有再去student里找
        aa.fun();
        //aa.fun(3);  aa只能调用people里的方法
        bb.fun();//先去子类里找
        bb.fun(3);//子类里没有，再去父类里找
        //bb.show();  指向子类实例的父类引用，不能调用父类中没有的子类方法！！
    }
}