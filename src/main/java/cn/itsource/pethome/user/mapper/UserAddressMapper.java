package cn.itsource.pethome.user.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.UserAddress;
import cn.itsource.pethome.query.UserAddressQuery;

import java.util.List;

public interface UserAddressMapper extends BaseMapper<UserAddress, UserAddressQuery> {
    /**
     * 根据用户查询所有用户地址
     * @param id
     * @return
     */
    List<UserAddress> findAll(Long id);
}
