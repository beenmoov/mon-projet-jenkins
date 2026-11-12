package hello;

public class HelloWorld {
    public String sayHello() {
        return "Hello World!";
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld().sayHello());
    }
}