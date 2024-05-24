package org.example;

public class Test2 {
    public static void main(String[] args){
        System.out.println("main start");
        A a = new A();
    }
}
class A{
    private int a;
    private static String str;
    {
        a = 1;
        System.out.println("初始化代码块");
    }
    static {
        str = "1231";
        System.out.println("静态代码块");
    }
}
