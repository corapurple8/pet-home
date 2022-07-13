package cn.itsource.pethome.pet.mapper;

import cn.itsource.pethome.basic.mapper.BaseMapper;
import cn.itsource.pethome.domain.Pet;
import cn.itsource.pethome.domain.PetDetail;
import cn.itsource.pethome.query.PetQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface PetMapper extends BaseMapper<Pet, PetQuery> {

    /**
     * 保存宠物详情
     * @param petDetail
     */
    void savePetDetail(PetDetail petDetail);

    /**
     * 批量上下架
     * @param list
     * @param state
     * @param onsaletime
     * @param offsaletime
     */
    void changeState(@Param("list") Long[] list, @Param("state") Integer state, @Param("onsaletime") Date onsaletime,@Param("offsaletime") Date offsaletime);
}
