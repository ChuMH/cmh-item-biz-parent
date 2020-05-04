package test.countdownlatchtest;

/**
 * @author：
 * @data：
 * @description：
 */
public class Test1 {
    public static void main(String[] args) {
        for(int i = 0;i<10;i++){
            Test2 test2 = new Test2();
            System.out.println(i);
        }
    }
}

class Test2{
    private static int a;
    static {
        System.out.println("执行"+a+++"次");
    }
}
