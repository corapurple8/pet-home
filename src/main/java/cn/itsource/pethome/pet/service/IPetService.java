package cn.itsource.pethome.pet.service;

import cn.itsource.pethome.basic.service.IBaseService;
import cn.itsource.pethome.domain.Pet;
import cn.itsource.pethome.query.PetQuery;

public interface IPetService extends IBaseService<Pet, PetQuery> {
    /**
     * 批量下架
     * @param ids
     * @param state
     */
    void offPets(Long[] ids, Integer state);

    /**
     * 批量上架
     * @param ids
     * @param state
     */
    void onPets(Long[] ids, Integer state);
}
