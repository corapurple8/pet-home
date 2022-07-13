package cn.itsource.pethome.pet.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Adopt;
import cn.itsource.pethome.domain.Employee;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.pet.mapper.AdoptMapper;
import cn.itsource.pethome.pet.service.IAdoptService;
import cn.itsource.pethome.query.AdoptQuery;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.user.mapper.ShopMapper;
import cn.itsource.pethome.util.AjaxResult;
import cn.itsource.pethome.util.DistanceUtil;
import cn.itsource.pethome.util.Point;
import cn.itsource.pethome.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdoptServiceImpl extends BaseServiceImpl<Adopt, AdoptQuery> implements IAdoptService {
    @Autowired //注入寻主信息mapper
    private AdoptMapper adoptMapper;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 添加一条寻主信息
     * @param adopt
     * @param request
     * @return
     */
    @Override
    public AjaxResult save(Adopt adopt, HttpServletRequest request) {
        //获取前台的令牌
        String userToken = request.getHeader(Constant.USER_TOKEN);
        String type = request.getHeader(Constant.USER_TYPE);
        String loginUserStr = RedisUtils.INSTANCE.get(userToken);
        //判断类型 为1 用user解析 为2用employee解析
        if ("1".equals(type)){
            User user = JSON.parseObject(loginUserStr, User.class);
            //登录的用户就是当前的送养人 前台普通用户
            adopt.setUser(user);
            //平台自动派单
            //调用工具类方法Shop getNearestShop (Point point, List<Shop> shops)
            Point pointUser = DistanceUtil.getPoint(adopt.getAddress());
            //先找符合条件的商铺
            List<Shop> shops = shopMapper.findAll(new ShopQuery());
            //最合适的商铺
            Shop nearestShop = DistanceUtil.getNearestShop(pointUser, shops);
            //将最近商铺设进表单
            adopt.setShop(nearestShop);
            //待处理
            adopt.setState(Constant.AUDIT);
            //存进数据库
            adoptMapper.save(adopt);
        }else {
            //是商家用户自己
            Employee employee = JSON.parseObject(loginUserStr, Employee.class);
            //查找到商家
            Shop shop = shopMapper.findByAdminId(employee.getId());
            //将商家赋值回去
            adopt.setShop(shop);
            //直接上架//待激活上架
            adopt.setState(Constant.ACTIVITY);
        }
        //存进数据库
        adoptMapper.save(adopt);
        return AjaxResult.me().setMsg("添加成功");
    }
}
