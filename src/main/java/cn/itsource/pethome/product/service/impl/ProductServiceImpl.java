package cn.itsource.pethome.product.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.Product;
import cn.itsource.pethome.domain.ProductDetail;
import cn.itsource.pethome.dto.ProductDto;
import cn.itsource.pethome.product.mapper.ProductMapper;
import cn.itsource.pethome.product.service.IProductService;
import cn.itsource.pethome.query.ProductQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, ProductQuery> implements IProductService {
    @Autowired//注入商品mapper
    private ProductMapper productMapper;
    /**
     * 添加一个商品
     * @param product
     */
    @Transactional
    @Override
    public void save(Product product) {
        //用product子类productDto接收 到info 和orderNotice
        //新增方法 首先将类型转换强制转换
        ProductDto productDto = (ProductDto) product;
        Product productReal = new Product();
        //将一个新的product对象复制所有的属性 以免同一个地址不同修改
        BeanUtils.copyProperties(productDto,productReal);
        //将这个商品写进数据库 并且自动设置回主键
        productMapper.save(productReal);
        //创建一个新的商品详情对象
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct_id(productReal.getId());
        productDetail.setIntro(productDto.getIntro());
        productDetail.setOrderNotice(productDto.getOrderNotice());
        //写进数据库
        productMapper.saveProductDetail(productDetail);
    }

    /**
     * 根据商品id查询商品详情 (包括商品信息) 用于表单回显
     * @param id
     * @return
     */
    @Override
    public ProductDetail findProductDetail(Long id) {
        return productMapper.findProductDetail(id);
    }

    /**
     * 批量上下架的方法
     * @param ids
     * @param state
     */
    @Override
    public void changeSale(Long[] ids, Integer state) {
        Date offsaletime = null;
        Date onsaletime = null;
        if (state== -1){
            offsaletime = new Date();
        }
        if (state == 0){
            onsaletime = new Date();
        }
        productMapper.updateBatch(ids,state,onsaletime,offsaletime);
    }

    @Override
    public void update(Product product) {
        //用product子类productDto接收 到info 和orderNotice
        //新增方法 首先将类型转换强制转换
        ProductDto productDto = (ProductDto) product;
        Product productReal = new Product();
        //将一个新的product对象复制所有的属性 以免同一个地址不同修改
        BeanUtils.copyProperties(productDto,productReal);
        //将这个商品写进数据库 并且自动设置回主键
        productMapper.update(productReal);
        //创建一个新的商品详情对象
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct_id(productReal.getId());
        productDetail.setIntro(productDto.getIntro());
        productDetail.setOrderNotice(productDto.getOrderNotice());
        //写进数据库
        productMapper.updateProductDetail(productDetail);
    }
}
