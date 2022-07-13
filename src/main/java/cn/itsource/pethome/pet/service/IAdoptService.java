package cn.itsource.pethome.pet.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Adopt;
import cn.itsource.pethome.query.AdoptQuery;
import cn.itsource.pethome.util.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface IAdoptService extends IBaseService<Adopt, AdoptQuery> {
    /**
     * 添加一条寻主信息
     * @param adopt
     * @param request
     * @return
     */
    AjaxResult save(Adopt adopt, HttpServletRequest request);
}
