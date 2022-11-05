package com.poke.service;

import com.poke.DTO.SetmealDto;
import com.poke.PoJo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 56207
* @description ��Ա�setmeal(�ײ�)�������ݿ����Service
* @createDate 2022-10-31 20:54:02
*/
public interface SetmealService extends IService<Setmeal> {

    //�����ײͣ���Ҫ�����������Setmeal��SetmealDish
    public void saveWithDish(SetmealDto setmealDto);

    public SetmealDto getWithDish(Long id);

    void UpdateWithSetmeal(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
