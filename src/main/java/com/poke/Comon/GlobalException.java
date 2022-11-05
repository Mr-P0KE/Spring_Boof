package com.poke.Comon;


import com.poke.Util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalException  {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry"))
            return R.error("用户名已经存在");

        return R.error("添加失败");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler2(CustomException ex){
        log.info(ex.getMessage());

        return R.error(ex.getMessage());
    }
}
