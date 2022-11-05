package com.poke.service;

import com.poke.PoJo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 56207
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-10-31 20:54:02
*/
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
