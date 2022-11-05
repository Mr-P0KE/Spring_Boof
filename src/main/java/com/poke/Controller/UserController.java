package com.poke.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.poke.PoJo.User;
import com.poke.Util.R;
import com.poke.Util.SendMessageUtil;
import com.poke.Util.ValidateCodeUtils;
import com.poke.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMeg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(!StringUtils.isEmpty(phone)){
            String s = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("�ֻ���֤��Ϊ={}",s);
            session.setAttribute(phone,s);
            return R.success("���ͳɹ�");
        }
        return R.error("����ʧ��");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //��ȡ�ֻ���
        String phone = map.get("phone").toString();

        //��ȡ��֤��
        String code = map.get("code").toString();

        //��Session�л�ȡ�������֤��
        Object codeInSession = session.getAttribute(phone);

        //������֤��ıȶԣ�ҳ���ύ����֤���Session�б������֤��ȶԣ�
        if(codeInSession != null && codeInSession.equals(code)){
            //����ܹ��ȶԳɹ���˵����¼�ɹ�

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //�жϵ�ǰ�ֻ��Ŷ�Ӧ���û��Ƿ�Ϊ���û�����������û����Զ����ע��
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("��¼ʧ��");
    }
}
