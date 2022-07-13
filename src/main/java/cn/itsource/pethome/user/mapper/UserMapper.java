package cn.itsource.pethome.user.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Shop;
import cn.itsource.pethome.domain.User;
import cn.itsource.pethome.query.ShopQuery;
import cn.itsource.pethome.query.UserQuery;

import javax.management.Query;


public interface UserMapper extends BaseMapper<User, UserQuery> {

    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 登录：根据用户名/邮箱/手机号查询前台用户
     * @param username
     * @return
     */
    User findByUsernameOrEmailOrPhone(String username);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
