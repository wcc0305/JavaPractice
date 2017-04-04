package TestGeneric;
/*
 所谓泛型，就是允许在定义类、接口、方法时使用类型参数
 java泛型设计原则：只要代码在编译时不出错，运行时就不会遇到ClassCastException
 静态变量、方法、初始化块中，不允许使用泛型。Student<Integer>与Student<String>是同一个类
 String是Object的子类，数组String[]也是Object[]的子类，但List<String>却不是List<Object>的子类
 <T>与<？>， <T>是类型形参，注入类型实参后，T就代表该类型；<？>是通配符。 <T>与<？>都可以加extends，设定类型上界
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Student<T>{ //class Student<T extends java.io.Serializable>则给泛型T设置了上界，即必须是实现了Serializable接口的类
    public Student(T id){
        this.id=id;
    }
    private T id;
    public void show(){
        System.out.println("my id is "+id);
    }
}
//class BoyStudent extends Student<T>{//Student类要派生出新类的时候，必须只能类型，不能再带泛型了。
class BoyStudent extends Student<String>{
    public BoyStudent(String id) {//
        super(id);
    }
}
class GirlStudent extends Student<String>{
    public GirlStudent(String id) {
        super(id);
    }
}


public class TestGeneric {
    public static void main(String args[]){
        //List strList=new ArrayList();     //不加范型的集合，什么对象都能放，放进去的都当成Object，取出来的时候再强制类型转换，
        //这是Java 5之间的用法，不提倡
        List<String> strList=new ArrayList<>();//java 7之后，用<>就行，不用再写一遍了


        Student<String> wcc=new Student<String>("wcc");
        wcc.show();
        BoyStudent tjc=new BoyStudent("wcc");
        tjc.show();
        Student<Integer> hehe=new Student<Integer>(555);
        hehe.show();
        List<Student<?>> list =new ArrayList<>();
        //List<? extends Student<String>> list =new ArrayList<>();    //为什么不可以？
        list.add(wcc);
        list.add(tjc);
        list.add(hehe);
        showAll(list);
        //showAll_2(list);   //为什么showAll_2()方法不行？

    }
    public static void showAll(List<Student<?>> list ){
        for(Student<?> s : list){
            s.show();
        }
    }
    public static <T> void showAll_2(List<Student<T>> list ){
        for(Student<T> s : list){
            s.show();
        }
    }
}
