package test.estest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import test.AbstractTest;


@Slf4j
public class ElasticSearchTest  extends AbstractTest {

    @Test
    public void test(){
        method();
    }

    @AnnotationTest
    public String method(){
        return "hello world!";
    }
}
