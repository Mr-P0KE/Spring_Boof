package com.poke.service;

import com.poke.PoJo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 56207
* @description ��Ա�category(��Ʒ���ײͷ���)�������ݿ����Service
* @createDate 2022-10-31 20:54:02
*/
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
