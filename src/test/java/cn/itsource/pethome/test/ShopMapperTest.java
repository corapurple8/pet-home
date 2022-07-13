package cn.itsource.pethome.test;

import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.test.util.BaseTest;
import cn.itsource.pethome.user.mapper.ShopMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopMapperTest extends BaseTest {
    @Autowired
    private ShopMapper shopMapper;

    @Test
    public void testX(){
        List<Shop> shops = shopMapper.findPage(new ShopQuery());
        shops.forEach(System.out::println);
        /*shopMapper.delete(30L);*/
    }


}