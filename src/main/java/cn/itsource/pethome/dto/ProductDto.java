package cn.itsource.pethome.dto;

import cn.itsource.pethome.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Product {
    /**商品详情*/
    private String intro;
    /**购买须知*/
    private String orderNotice;
}
