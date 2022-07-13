package cn.itsource.pethome.user.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.UserAddress;
import cn.itsource.pethome.query.UserAddressQuery;

import java.util.List;

public interface IUserAddressService extends IBaseService<UserAddress, UserAddressQuery> {
    /**
     * 根据用户id查询其所有的用户地址
     * @param id
     * @return
     */
    List<UserAddress> findAll(Long id);
}
