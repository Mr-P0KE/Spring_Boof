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
        log.info("����Ϊ"+password);
        log.info("���ݿ�"+employee1.getPassword());
        log.info("�Ƿ�Ϊ"+employee1.getPassword().equals(password));
        if(employee1 == null)
            return R.error("��¼ʧ�ܣ��Ҳ����û�");
        if(!employee1.getPassword().equals(password))
            return R.error("��¼ʧ�ܣ��������");
        if(employee1.getStatus() == 0)
            return R.error("�˺ű�����");

        HttpSession session = request.getSession();
        session.setAttribute("employee",employee1.getId());
        return R.success(employee1);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("employee");
        return R.success("�˳��ɹ�");
    }

    @PostMapping
    public R<String> Insert(@RequestBody Employee employee){
        log.info("��������:{}",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) session.getAttribute("employee"));
//        employee.setUpdateUser((Long) session.getAttribute("employee"));
        employeeService.save(employee);
        return R.success("��ӳɹ�");
    }
    @GetMapping("/page")
    public  R<Page> page(Integer page,Integer pageSize,String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);
//        //��������������
//        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
//        //��ӹ�������
//        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        //�����������
//        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // ִ�в�ѯ
//        employeeService.page(pageInfo,queryWrapper);

        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.like(!StringUtils.isEmpty(name),"username",name)
                .orderByDesc("create_time");
        employeeService.page(pageInfo,qw);


        return R.success(pageInfo);
    }

    //���ı�jsonת������long���ͻ���ǰ�˶�ʧ��������Ҫ�ı�jsonת����
    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        log.info("Ա����Ϣ={}",employee.toString());
//        Long id = (Long) session.getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        employeeService.updateById(employee);
        return R.success("�޸ĳɹ�111");
    }

    @GetMapping("/{id}")
    public R<Employee> getId(@PathVariable Long id){
        log.info("����id��ѯԱ����Ϣ...");
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("û�в�ѯ����ӦԱ����Ϣ");
    }

}
