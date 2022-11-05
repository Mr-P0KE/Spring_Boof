package com.poke.DTO;


import com.poke.PoJo.Setmeal;
import com.poke.PoJo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
