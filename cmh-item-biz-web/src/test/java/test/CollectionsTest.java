package test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
public class CollectionsTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()){
            String str = (String) listIterator.next();
            listIterator.set("wangwu");
        }
        System.out.println(list);
    }
}
