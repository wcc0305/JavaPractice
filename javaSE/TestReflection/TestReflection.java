package TestReflection;


/*理解反射，在于“反着来”，以及对动态性的弥补，关键在于Class类。
 Reflection机制允许程序在正在执行的过程中，利用Reflection APIs取得任何已知名称的类的内部信息，
 包括：package、 type parameters、 superclass、 implemented interfaces、 inner classes、 outer classes、 fields、 constructors、
 methods、 modifiers等，并可以在执行的过程中，动态生成instances、变更fields内容或唤起methods。
 "正着来"在于，由类生成对象，通过对象调用方法和访问变量。反射机制允许由对象，获得类的信息，
 Java不允许在运行过程中更改变量类型，所以不是动态语言，反射机制是对动态性的弥补，可以动态加载类。
 Boy a=new Boy()是正常的写法，一遇到Boy，JVM就会加载Boy类加载到方法区，堆里面生成一个Class<Boy>对象实例，这个对象里有上面写的那一大堆关于这个类的信息
 之后才是new Boy()在堆里创建一个Boy的对象实例。但这样程序都被写死了，通过反射，可以在运行时通过字符串给一个名字，确定加载哪个类，调用哪个对象的哪个方法，
 这就似乎动态起来了。
 */
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

abstract class Student{
    public String name;
    public int age;
    public abstract String toString();
}
class Boy extends Student{
    private String secret="打死也不说";
    public String toString(){
        return "my name is "+name+", i am a "+age+"-year-old boy!";
    }
}
class Girl extends Student{
    public String toString(){
        return "my name is "+name+", i am a "+age+"-year-old girl!";
    }
}
public class TestReflection {
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        //通过一个对象实例，获得类(名)，及相关信息(如继承的父类(名)，实现的接口(名))
        Boy a=new Boy();
        System.out.println(a.getClass().getName());
        Class<?> parentClass = a.getClass().getSuperclass();
        System.out.println("父类为：" + parentClass.getName());

        //通过类名，生成对象实例
        Class<?> class1 = null; //Class类，
        class1 =Class.forName("TestReflection.Boy");//根据字符串，加载该类，但可能有ClassNotFoundException
        Student b=(Student) class1.newInstance();
        System.out.println(b.toString());

        //获得类的属性，同样的道理还可以获得类的方法
        Field[] fields = class1.getSuperclass().getDeclaredFields();//DeclaredFields本类属性，继承来的不算。getFields()获得全部public属性
        for (int i = 0; i < fields.length; i++) {
            int mo = fields[i].getModifiers();// 权限修饰符
            String priv = Modifier.toString(mo);
            Class<?> type = fields[i].getType(); // 属性类型
            System.out.println(priv + " " + type.getName() + " " + fields[i].getName() + ";");
        }

        //修改对象的属性值，同样的道理也可以修改属性的权限、调用方法
        Field field=class1.getField("name");//可能有NoSuchFieldException
        field.set(b, "wcc");
        Method method=class1.getMethod("toString");
        System.out.println(method.invoke(b));//调用b实例的method方法

        //通过反射访问私有成员
        Field private_field = class1.getDeclaredField("secret");
        private_field.setAccessible(true);
        System.out.println(private_field.get(b));

        //在ArrayList<Integer>中，加入String
        //通过反射，可以无视范型约束，也可以修改数组的大小（但其实也创建新数组了，又copy进去）
        // Java泛型作用于编译时期，在运行时起泛型会被擦除，而反射的功能和目的恰好是获取一个对象的运行时信息，
        //因此只需要利用反射获取到list的add方法即可
        List<Integer> array=new ArrayList<Integer>();
        Method add=array.getClass().getMethod("add", Object.class);//注意参数是Object.class，或者是Object.getclass()，获得类名
        //Method add=array.getClass().getMethod("add", Integer.class);   奇怪为什么Integer.class不行？
        add.invoke(array, "无视范型约束,hehe");
        System.out.println(array.get(0));

    }
}
