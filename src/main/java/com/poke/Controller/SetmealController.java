package com.poke.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poke.DTO.DishDTO;
import com.poke.DTO.SetmealDto;
import com.poke.PoJo.Category;
import com.poke.PoJo.Setmeal;
import com.poke.PoJo.SetmealDish;
import com.poke.Util.R;
import com.poke.service.CategoryService;
import com.poke.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public R<Page> pageR(Integer page,Integer pageSize,String name){
        Page<Setmeal> page1 =new Page<>(page,pageSize);
        Page<SetmealDto> pageInfo = new Page<>();

        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.like(name!= null,Setmeal::getName,name)
                        .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(page1,qw);
        BeanUtils.copyProperties(page1,pageInfo,"records");
        List<Setmeal> records = page1.getRecords();
        List<SetmealDto> list = new ArrayList<>();
        for(Setmeal setmeal : records){
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);

            Category byId = categoryService.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(byId.getName());
            list.add(setmealDto);
        }
        pageInfo.setRecords(list);
        return  R.success(pageInfo);
    }

    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("保存的套餐为={}",setmealDto);

        setmealService.saveWithDish(setmealDto);
        return R.success("插入成功");
    }

    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info("需要更新的信息为+{}",setmealDto);
        setmealService.UpdateWithSetmeal(setmealDto);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable long id){
        SetmealDto setmealDto = setmealService.getWithDish(id);
        return R.success(setmealDto);
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,@RequestParam String ids){
        List<String> list = Arrays.asList(ids.split(","));
        LambdaUpdateWrapper<Setmeal> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.set(Setmeal::getStatus,status)
                        .in(Setmeal::getId,list);
        setmealService.update(lambdaQueryWrapper);
        return R.success("状态修改成功");
    }
//    http://localhost:8080/setmeal?ids=1588447036640927746
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("删除信息为={}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

//    http://localhost:8080/setmeal/list?categoryId=1413342269393674242&status=1  前端查询套餐页面
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> getList(Setmeal setmeal){
        log.info("setaml={}",setmeal);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId())
                .eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);
        return R.success(list);
    }
}
