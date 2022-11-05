package com.poke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.PoJo.AddressBook;
import com.poke.service.AddressBookService;
import com.poke.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 56207
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




