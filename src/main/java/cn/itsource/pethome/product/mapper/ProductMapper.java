package cn.itsource.pethome.product.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Product;
import cn.itsource.pethome.domain.ProductDetail;
import cn.itsource.pethome.query.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ProductMapper extends BaseMapper<Product, ProductQuery> {
    /**
     * 添加商品详情
     * @param productDetail
     */
    void saveProductDetail(ProductDetail productDetail);

    /**
     *根据商品id查询商品详情
     * @param id
     * @return
     */
    ProductDetail findProductDetail(Long id);

    /**
     * 根据商品外键修改商品详情
     * @param productDetail
     */
    void updateProductDetail(ProductDetail productDetail);

    /**
     * 批量上下架
     * @param list
     * @param state
     */
    void updateBatch(@Param("list") Long[] list, @Param("state") Integer state, @Param("onsaletime") Date onsaletime,@Param("offsaletime") Date offsaletime);
}
