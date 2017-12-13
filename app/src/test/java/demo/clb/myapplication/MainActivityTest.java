package demo.clb.myapplication;

import android.content.res.Resources;
import android.test.mock.MockContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/11/3.
 */

public class MainActivityTest {

    static String value = "[{'name':'zhangsan', 'age':10, 'sex':'男'}, {'name':'hanmeimei', 'age':10, 'sex':'女'}]";


    @Test
    public void setTheme() throws Exception {

    }

    @Test
    public void testGSON() {
        Gson gson = new Gson();
        List<Student> list = fromJson(value, Student.class);
        System.out.println("MainActivityTest.testGSON, person=" + list);


        MockContext context = new MockContext();
        String packageName = context.getPackageName();
        Resources appName = context.getResources();
        System.out.println("MainActivityTest.testGSON, packageName=" + packageName + ", appName=" + appName);
    }

    private <T> List<T> fromJson(String json, Class<T> clazz) {
        Type type = new TypeToken<List<T>>(){}.getType();
        return new Gson().fromJson(value, type);
    }


    public static class Person<T> {
        T t;

        @Override
        public String toString() {
            return t.toString();
        }
    }

    public static class Student {
        private String name;
        private int age;
        private String sex;

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }


}