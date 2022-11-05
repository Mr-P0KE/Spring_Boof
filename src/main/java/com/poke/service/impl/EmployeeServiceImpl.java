package com.poke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.PoJo.Employee;
import com.poke.service.EmployeeService;
import com.poke.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 56207
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




