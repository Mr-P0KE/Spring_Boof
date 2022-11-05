package com.poke.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.poke.DTO.DishDTO;
import com.poke.PoJo.Category;
import com.poke.PoJo.Dish;
import com.poke.PoJo.DishFlavor;
import com.poke.Util.R;
import com.poke.service.CategoryService;
import com.poke.service.DishFlavorService;
import com.poke.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name){
        log.info("page = {},pageSize = {}" ,page,pageSize);

        /*
        * ����Page���صĲ���������չʾ��
        * ��������Page*/
        Page<Dish> pageInfo = new Page(page,pageSize);
        Page<DishDTO> page1 = new Page();

        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.like(name != null,Dish::getName,name)
                .orderByAsc(Dish::getSort);
        dishService.page(pageInfo,qw);

        //��pageInfo�����Կ�������page1��������records����Ҳ����list<Dish>
        //records�������ڱ���չʾ������
        BeanUtils.copyProperties(pageInfo,page1,"records");

        //��õ�List<Dish>����
        List<Dish> list = pageInfo.getRecords();
        //�½�һ��List<DishDTO> �����ڱ�������
        List<DishDTO> list1 =new ArrayList<>();
        //������List<Dish>����dish���ݿ�����DishDTO�ϣ���ΪCategoryName���Ը�ֵ�������뵽List<DishDTO>������
        for(Dish dish : list){
            DishDTO d = new DishDTO();
            BeanUtils.copyProperties(dish,d);
            Long categoryId = dish.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            d.setCategoryName(byId.getName());
            list1.add(d);
        }
        //��Page<DishDTO>�е�records���Ը�ֵ
        page1.setRecords(list1);
        return R.success(page1);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDTO dishDTO){
        log.info("���Dish={}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return R.success("��ӳɹ�");
    }
    @GetMapping("/{id}")
    public R<DishDTO> getId(@PathVariable Long id){
        return R.success(dishService.getByIdWithFlavor(id));
    }

    @PutMapping
    public R<String> update(@RequestBody DishDTO dishDTO){
        log.info("��ȡ���޸ĵ�����={}",dishDTO.toString());
        dishService.updateWithFlavor(dishDTO);

        return R.success("��ӳɹ�");
    }
//    dish?ids=1397849739276890114
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ɾ���б�={}",ids);
        dishService.removeBeachWithDishFlavorById(ids);
        return R.success("ɾ���ɹ�");
    }
//    http://localhost:8080/dish/status/0?ids=1397850140982161409

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,@RequestParam String ids){
        log.info("״̬Ϊ={},idsΪ={}",status,ids);
        //תlist����Ϊ��StringʱMybatis�ᱨ��ʧ�������⣬ҵ���޷�����
        List<String> list = Arrays.asList(ids.split(","));
        LambdaUpdateWrapper<Dish> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Dish::getStatus,status)
                        .in(Dish::getId,list);
       dishService.update(lambdaUpdateWrapper);
       return R.success("״̬�޸ĳɹ�");

    }

//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.eq(Dish::getCategoryId,dish.getCategoryId())
//                .eq(Dish::getStatus,1)
//                .orderByAsc(Dish::getSort)
//                .orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(lambdaQueryWrapper);
//        return R.success(list);
//    }

    //��дlist�������п�ζ����

    @GetMapping("/list")
    public R<List<DishDTO>> list(Dish dish){
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Dish::getCategoryId,dish.getCategoryId())
                .eq(Dish::getStatus,1)
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lambdaQueryWrapper);
        List<DishDTO> dtoList = new LinkedList<>();
        for(Dish dish1 : list){
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(dish1,dishDTO);
            //��ȡ��ζDishFlavor
            Long id = dish1.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> list1 = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDTO.setFlavors(list1);

            //���CategoryName

            Category category = categoryService.getById(dish.getCategoryId());
            dishDTO.setCategoryName(category.getName());

            dtoList.add(dishDTO);
        }
        log.info("dtoList��ֵΪ={}",dtoList);
        return R.success(dtoList);
    }
}
