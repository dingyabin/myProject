package net.dingyabin.mapstruct;

import java.util.HashMap;
import java.util.Map;

public class TestMapStruct {


    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jim");
        map.put("age", 16);
        Student student = TestMapper.INSTANCE.toStudent(map);
        System.out.println(student);

    }


}
