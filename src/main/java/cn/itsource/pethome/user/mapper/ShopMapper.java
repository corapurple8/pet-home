package cn.itsource.pethome.user.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.query.UserQuery;


public interface ShopMapper extends BaseMapper<Shop, ShopQuery> {

    /**
     * 根据管理员id查找店铺
     * @param id
     * @return
     */
    Shop findByAdminId(Long id);
}
