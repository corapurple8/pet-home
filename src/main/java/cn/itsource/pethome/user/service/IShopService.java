package cn.itsource.pethome.user.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.query.ShopQuery;

public interface IShopService extends IBaseService<Shop, ShopQuery> {
    /**
     * 入驻一个商家
     * @param shop
     */
    void settledIn(Shop shop);

    /**
     * 审核商家
     * @param id
     */
    void shopAudit(Long id);

    /**
     * 激活商家
     * @param id
     */
    void shopActivity(Long id);

    /**
     * 根据管理员id查找店铺
     * @param id
     * @return
     */
    Shop findByAdminId(Long id);
}
