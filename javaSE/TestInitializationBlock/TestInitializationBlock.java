package TestInitializationBlock;

//构造方法
//初始化块两个作用：  1.无参构造方法，可用初始化块代替   2.静态初始化块，填补没有静态构造方法的缺失
/*总体执行顺序：
  1)加载类   父类(静态变量初始化，静态初始化块)二者执行顺序与代码先后顺序一致 -->  子类(静态变量初始化，静态初始化块)  -->
  2)实例化对象  父类(非静态变量初始化，非静态初始化块)  -->   父类构造函数  -->  子类(非静态变量初始化，非静态初始化块)   --> 子类构造函数
  */
class Person{
    static {
        //b=2;  //静态初始化块里，不能访问非静态变量
        c=1;
        System.out.println("静态初始化块");
    }
    {//初始化块
        int a=3;//a的作用于只在{初始化块}内
        b=5;
        System.out.println("非静态初始化块");
    }
    static int c=2;
    int b=4;//b=4还是b=5，看int b=4;与b=5;哪句话在前
    public Person(){
        System.out.println("构造函数");
    }
    public Person(String name){//只要程序员提供了自定义的构造器，默认的那个无参的构造器就无法调用了
        this();//嵌套调用构造函数
        System.out.println("构造函数"+name);
    }
    public void show(){
        System.out.println("b="+b+"     c="+c);
    }
}
public class TestInitializationBlock{
    public static void main(String args[]){
        Person wcc=new Person("wcc");
        wcc.show();
    }
}
