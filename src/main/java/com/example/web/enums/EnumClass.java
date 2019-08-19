package com.example.web.enums;

import java.awt.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

import static com.example.web.enums.EnumClass.Signal.GREEN;
import static com.example.web.enums.EnumClass.Signal.RED;
import static com.example.web.enums.EnumClass.Signal.YELLOW;

/**
 * @Auther: 36560
 * @Date: 2019/8/12 :6:18
 * @Description:
 */
enum Shrubbery{GROUD,CRAWLING,HANGING}
enum Explore{HERE,THERE}
public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s:Shrubbery.values()){
            System.out.println(s+" ordinal:"+s.ordinal());
            System.out.println(s.compareTo(Shrubbery.CRAWLING));
            System.out.println(s.equals(Shrubbery.CRAWLING));
            System.out.println(s==Shrubbery.CRAWLING);
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("-----------------------------");
        }
        for (String s:"GROUD CRAWLING HANGING".split(" ")){
            Shrubbery shrubbery = Enum.valueOf(Shrubbery.class, s);
            System.out.println(shrubbery);
        }
    }
    enum Signal{RED,GREEN,YELLOW}
    static class TraficLight{
        Signal color = RED;
        public void change(){
            switch (color){
                case RED:color=GREEN;
                    break;
                case GREEN:color=YELLOW;
                    break;
                case YELLOW:color=RED;
                    break;
            }
        }
        public static void main(String[] args) {
            TraficLight traficLight = new TraficLight();
            for (int i=0;i<7;i++){
                System.out.println(traficLight.color);
                traficLight.change();
            }
        }
    }
    static class Reflection{
        public static Set<String> analyze(Class<? extends Enum> enumClass){
            System.out.println("-------Analyzing"+enumClass+"--------");
            System.out.println("Inteerfaces:");
            for (Type t:enumClass.getGenericInterfaces()){
                System.out.println(t);
            }
            System.out.println("Base:"+enumClass.getSuperclass());
            System.out.println("Methods:");
            TreeSet<String> methods = new TreeSet<>();
            for (Method method:enumClass.getMethods()){
                methods.add(method.getName());
            }
            System.out.println(methods);
            return methods;
        }

        public static void main(String[] args) {
            Set<String> analyze = analyze(Explore.class);
            Set<String> analyze1 = analyze(Enum.class);
            System.out.println("Explore containsAll(enum)?:"+analyze.containsAll(analyze1));
            System.out.println("Explore removeAll (enum):");
            analyze.removeAll(analyze1);
            System.out.println(analyze);
        }
    }
}
