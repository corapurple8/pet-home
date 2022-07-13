package cn.itsource.pethome.user.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.WxUser;
import cn.itsource.pethome.query.WxUserQuery;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper extends BaseMapper<WxUser, WxUserQuery> {
    /**
     * 根据openid和unionid去数据库查询是否是登陆过的微信用户
     * @param openid
     * @param unionid
     * @return
     */
    WxUser findByOpenidAndUnionid(@Param("openid") String openid,@Param("unionid") String unionid);
}
