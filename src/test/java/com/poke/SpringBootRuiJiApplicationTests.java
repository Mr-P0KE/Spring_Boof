package com.poke;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poke.Controller.SetmealController;
import com.poke.PoJo.Employee;
import com.poke.Util.SendMessageUtil;
import com.poke.service.EmployeeService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRuiJiApplicationTests {
    @Autowired
    private SetmealController setmealController1;
    @Autowired
    private SetmealController setmealController2;
    //    @Autowired
//    private UserMapper userMapper1;
    @Test
    public void get(){
        System.out.println(setmealController1);
        System.out.println(setmealController2);
    }



}
