package cn.itsource.pethome.basic.service.impl;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.basic.query.BaseQuery;
import cn.itsource.pethome.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class BaseServiceImpl<T,Q extends BaseQuery> implements IBaseService<T,Q> {
    @Autowired//注入mapper
    private BaseMapper<T,Q> baseMapper;

    @Override
    @Transactional//读写 开启事务
    public void save(T t) {
        baseMapper.save(t);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        baseMapper.delete(id);
    }

    @Override
    @Transactional
    public void delete(String list) {

        List<Long> ids = new ArrayList<>();
        String[] split = list.split(",");
        for (int i = 0; i <split.length ; i++) {
            ids.add(Long.valueOf(split[i]));
        }
        baseMapper.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    public T findOne(Long id) {
        return baseMapper.findOne(id);
    }

    @Override
    public List<T> findAll(Q q) {
        return baseMapper.findAll(q);
    }

    @Override
    public Page<T> findPage(Q q) {
        List<T> data = baseMapper.findPage(q);
        Long total = baseMapper.findTotal(q);
        return new Page<T>(q.getPageNo(),q.getPageSize(),data,total);
    }

}
