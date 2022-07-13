package cn.itsource.pethome.test;

import cn.itsource.pethome.domain.Pet;
import cn.itsource.pethome.pet.mapper.PetMapper;
import cn.itsource.pethome.query.PetQuery;
import cn.itsource.pethome.test.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PetMapperTest extends BaseTest {
    @Autowired
    private PetMapper petMapper;
     @Test
     public void testX(){
//         List<Pet> all = petMapper.findAll(new PetQuery());
//         all.forEach(System.out::println);
         System.out.println(petMapper.findOne(4L));
     }


}