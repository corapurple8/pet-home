package cn.itsource.pethome.product.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Product;
import cn.itsource.pethome.domain.ProductDetail;
import cn.itsource.pethome.query.ProductQuery;

public interface IProductService extends IBaseService<Product, ProductQuery> {
    /**
     * 根据产品id查询商品详情(包括商品信息)
     * @param id
     * @return
     */
    ProductDetail findProductDetail(Long id);

    /**
     * 批量上下架方法
     * @param ids
     * @param state
     */
    void changeSale(Long[] ids, Integer state);
}
