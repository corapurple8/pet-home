package cn.itsource.pethome.test;

import cn.itsource.pethome.pet.mapper.AdoptMapper;
import cn.itsource.pethome.query.AdoptQuery;
import cn.itsource.pethome.test.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AdoptMapperTest extends BaseTest {

    @Autowired
    private AdoptMapper adoptMapper;

    @Test
    public void testX(){
        adoptMapper.findAll(new AdoptQuery());
    }

}