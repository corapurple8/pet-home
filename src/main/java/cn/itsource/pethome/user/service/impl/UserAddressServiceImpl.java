package cn.itsource.pethome.user.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.domain.UserAddress;
import cn.itsource.pethome.query.UserAddressQuery;
import cn.itsource.pethome.user.mapper.UserAddressMapper;
import cn.itsource.pethome.user.service.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl extends BaseServiceImpl<UserAddress, UserAddressQuery> implements IUserAddressService {
    @Autowired//注入用户地址mapper
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findAll(Long id) {
        return userAddressMapper.findAll(id);
    }
}
