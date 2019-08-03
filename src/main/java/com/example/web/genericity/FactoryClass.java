package com.example.web.genericity;

/**
 * @Auther: 36560
 * @Date: 2019/7/13 :8:42
 * @Description:
 */
interface Factory<T>{
    T creat();
}
class HumanFactory implements Factory<Integer>{
    @Override
    public Integer creat() {
        return new Integer(0);
    }
}
class Foo<T>{
    private Class<T> kind;
    T creat(){
        try {
            return kind.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
class NameClass<T>{
    private T  inner ;
    public NameClass(Factory<T> factory){
        inner = factory.creat();
    }
    public void print(){
        System.out.println("i am a nameclass");
    }
}
public class FactoryClass<T> {
    private T x;
    public <F extends Factory<T>> FactoryClass(F factory){
        x=factory.creat();
    }
    public void print(){
        System.out.println("i am a class");
    }
    public static void main(String[] args){
        FactoryClass<Integer> integerFactoryClass = new FactoryClass<>(new HumanFactory());
        integerFactoryClass.print();
        NameClass<Integer> integerNameClass = new NameClass<>(new HumanFactory());
        integerNameClass.print();
    }
}
