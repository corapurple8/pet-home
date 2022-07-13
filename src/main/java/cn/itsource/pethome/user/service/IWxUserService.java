package cn.itsource.pethome.user.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.WxUser;
import cn.itsource.pethome.dto.WxUserDto;
import cn.itsource.pethome.query.WxUserQuery;
import cn.itsource.pethome.util.AjaxResult;

public interface IWxUserService extends IBaseService<WxUser, WxUserQuery> {

    /**
     * 微信登录
     * @param code
     * @return
     */
    AjaxResult wechatLogin(String code);

    /**
     * 微信绑定注册方法
     * @param wxUserDto
     * @return
     */
    AjaxResult wxReg(WxUserDto wxUserDto);
}
