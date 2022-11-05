package com.poke.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poke.PoJo.Employee;
import com.poke.Util.R;
import com.poke.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;


@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
         password = DigestUtils.md5DigestAsHex(password.getBytes());
//        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
//        qw.eq(Employee :: getUsername,employee.getUsername());
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.eq("username",employee.getUsername());
        Employee employee1 =  employeeService.getOne(qw);
        log.info("密码为"+password);
        log.info("数据库"+employee1.getPassword());
        log.info("是否为"+employee1.getPassword().equals(password));
        if(employee1 == null)
            return R.error("登录失败，找不到用户");
        if(!employee1.getPassword().equals(password))
            return R.error("登录失败，密码错误");
        if(employee1.getStatus() == 0)
            return R.error("账号被禁用");

        HttpSession session = request.getSession();
        session.setAttribute("employee",employee1.getId());
        return R.success(employee1);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> Insert(@RequestBody Employee employee){
        log.info("传输数据:{}",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) session.getAttribute("employee"));
//        employee.setUpdateUser((Long) session.getAttribute("employee"));
        employeeService.save(employee);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public  R<Page> page(Integer page,Integer pageSize,String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);
//        //构造条件构造器
//        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
//        //添加过滤条件
//        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        //添加排序条件
//        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
//        employeeService.page(pageInfo,queryWrapper);

        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.like(!StringUtils.isEmpty(name),"username",name)
                .orderByDesc("create_time");
        employeeService.page(pageInfo,qw);


        return R.success(pageInfo);
    }

    //不改变json转换器，long类型会在前端丢失，所以需要改变json转换器
    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        log.info("员工信息={}",employee.toString());
//        Long id = (Long) session.getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeService.updateById(employee);
        return R.success("修改成功111");
    }

    @GetMapping("/{id}")
    public R<Employee> getId(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

}
