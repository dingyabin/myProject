package net.dingyabin.mapstruct;

import java.util.Map;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T19:52:38+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class TestMapperImpl implements TestMapper {

    @Override
    public Student toStudent(Map<String, Object> map) {
        if ( map == null ) {
            return null;
        }

        Student student = new Student();

        if ( map.containsKey( "name" ) ) {
            student.setName( Conventer.objToString( map.get( "name" ) ) );
        }
        if ( map.containsKey( "age" ) ) {
            student.setAge( Conventer.objToInt( map.get( "age" ) ) );
        }

        return student;
    }
}
