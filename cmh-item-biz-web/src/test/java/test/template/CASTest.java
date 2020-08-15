package test.template;

/**
 * @author：
 * @data：
 * @description：
 */
public class CASTest {

    private Simulated value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }

    public static void main(String[] args) {
        CASTest casTest = new CASTest();
        casTest.value = new Simulated();
        int a = casTest.increment();
        System.out.println(a);
    }
}
