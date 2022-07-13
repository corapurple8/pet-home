package cn.itsource.pethome.user.service.impl;
import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.org.mapper.EmployeeMapper;
import cn.itsource.pethome.user.mapper.ShopMapper;
import cn.itsource.pethome.user.service.IShopService;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop,ShopQuery> implements IShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired//注入邮件工具类
    private MailUtils mailUtils;

    @Override
    @Transactional
    public void settledIn(Shop shop) {
        //首先添加管理员
        Employee admin = shop.getAdmin();
        //设置管理员的状态
        admin.setState(Constant.AUDIT);
        //将管理员添加进员工列表
        employeeMapper.save(admin);
        //设置商铺的状态
        shop.setState(Constant.AUDIT);
        //由于修改了xml 会自动设置id进admin 此时直接添加商铺即可
        shopMapper.save(shop);

    }

    @Override
    public void shopAudit(Long id) {
        //查询该商家
        Shop shop = shopMapper.findOne(id);
        //将该商家和管理员改为待激活
        shop.setState(Constant.ACTIVITY);
        Employee admin = employeeMapper.findOne(shop.getAdmin().getId());
        admin.setState(Constant.ACTIVITY);
        //保存数据
        employeeMapper.update(admin);
        shopMapper.update(shop);
        //发送邮件给商家
        mailUtils.sendActivityMail(shop.getAdmin().getEmail(),shop.getId());
    }

    @Override
    public void shopActivity(Long id) {
        //查询商家
        Shop shop = shopMapper.findOne(id);
        //管理员和商家设置为正常
        shop.setState(Constant.NORMAL);
        Employee admin = employeeMapper.findOne(shop.getAdmin().getId());
        admin.setState(Constant.NORMAL);
        employeeMapper.update(admin);
        shopMapper.update(shop);
    }

    @Override
    public Shop findByAdminId(Long id) {
        return shopMapper.findByAdminId(id);
    }
}
