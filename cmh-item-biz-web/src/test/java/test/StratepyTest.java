package test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author：
 * @data：
 * @description：策略模式
 */
public class StratepyTest implements Stratepy{
    private static Map<String,String> personMap = new HashMap<>(8);

    static {
        personMap.put("美国人","美国话");
        personMap.put("中国人","中国话");
        personMap.put("韩国人","韩国话");
        personMap.put("日本人","日本话");
        personMap.put("缅甸人","缅甸话");
        personMap.put("泰国人","泰国话");
        personMap.put("英国人","英国话");

        //测试case为什么不可以跟枚举类的get方法
        switch (UserEnum.getByValue(1)){
            case PERSON_ONE :
                System.out.println("匹配成功！");
            break;
        }

    }

    @Override
    public String saySomething(String message) {
        return personMap.get(message);
    }

    /**
     * String.format()练习
     */
    public void stringFormatTest(){
        String str = String.format(User.PERSON_ONE.getName(),"123");
        System.out.println(str);
    }

    /**
     * String.format()练习使用
     */
    enum User{
        PERSON_ONE(1,"张三1%s"),
        PERSON_TWO(2,"李四2%s");

        private Integer id;

        private String name;

        User(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    enum UserEnum{
        PERSON_ONE(1);

        private int number;

        UserEnum(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public static int getNumber2() {
            return 1;
        }

        public static UserEnum getByValue(Integer value){
            for(UserEnum userEnum : values()){
                if (userEnum.getNumber2() == value) {
                    return userEnum;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        StratepyTest stratepyTest = new StratepyTest();
    }
}


