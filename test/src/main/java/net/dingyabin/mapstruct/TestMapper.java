package net.dingyabin.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(uses = Conventer.class)
public interface TestMapper {

    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    @Mapping(source = "name", target = "name", qualifiedByName = "objToString")
    @Mapping(source = "age", target = "age", qualifiedByName = "objToInt")
    @Mapping(target = "address", ignore = true)
    Student toStudent(Map<String, Object> map);


}
