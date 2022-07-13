package cn.itsource.pethome.pet.service.impl;

import cn.itsource.pethome.basic.service.impl.BaseServiceImpl;
import cn.itsource.pethome.constant.Constant;
import cn.itsource.pethome.domain.Adopt;
import cn.itsource.pethome.domain.Pet;
import cn.itsource.pethome.domain.PetDetail;
import cn.itsource.pethome.pet.mapper.AdoptMapper;
import cn.itsource.pethome.pet.mapper.PetMapper;
import cn.itsource.pethome.pet.service.IPetService;
import cn.itsource.pethome.query.PetQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PetServiceImpl extends BaseServiceImpl<Pet, PetQuery> implements IPetService {

    @Autowired//注入宠物mapper
    private PetMapper petMapper;
    @Autowired//注入寻主信息mapper
    private AdoptMapper adoptMapper;


    @Override
    @Transactional
    public void save(Pet pet) {
        //先存宠物类型
        petMapper.save(pet);
        //返回主键后
        PetDetail petDetail = pet.getPetDetail();
        petDetail.setPet_id(pet.getId());
        petMapper.savePetDetail(petDetail);
        //保存好后 修改adopt里的信息
        Adopt adopt = adoptMapper.findOne(pet.getAdopt().getId());
        //已处理
        adopt.setState(Constant.ACTIVITY);
        //宠物主人变成商铺
        adopt.setUser(null);
        adoptMapper.update(adopt);
    }

    @Override
    public void offPets(Long[] list, Integer state) {
        Date onsaletime = null;
        Date offsaletime = new Date();
        petMapper.changeState(list,state,onsaletime,offsaletime);

    }

    @Override
    public void onPets(Long[] list, Integer state) {
        Date onsaletime = new Date();
        Date offsaletime = null;
        petMapper.changeState(list,state,onsaletime,offsaletime);
    }
}
