package cn.itsource.pethome.test;

import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.test.util.BaseTest;
import cn.itsource.pethome.user.service.IShopService;
import cn.itsource.pethome.util.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ShopServiceImplTest extends BaseTest {
@Autowired
    private IShopService shopService;
@Test
public void testX(){
    Page<Shop> page = shopService.findPage(new ShopQuery());
    page.getRows().forEach(System.out::println);
}
}