package cn.itsource.pethome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品详情类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    /**主键*/
    private Long id;
    /**商品外键*/
    private Long product_id;
    /**详情*/
    private String intro;
    /**注意事项*/
    private String orderNotice;
    /**绑定的商品*/
    private Product product;
}
