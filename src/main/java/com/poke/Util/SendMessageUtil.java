package com.poke.Util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class SendMessageUtil {

    @Value("${Demo.Message.secretId}")
    private static String secretId;

    @Value("${Demo.Message.secretKey}")
    private static String secretKey;


    private static String  tag =  "个人的学习记录";
    public static void send(String signName, String templateCode,String phoneNumbers){
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(secretId, secretKey);

        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

        // 实例化一个请求对象,每个接口都会对应一个request对象
        SendSmsRequest req = new SendSmsRequest();
        //设置标签内容 //必须写
        req.setSignName(tag);

        String[] phoneNumberSet1 = {phoneNumbers};
        req.setPhoneNumberSet(phoneNumberSet1);

        //设置应用id
        req.setSmsSdkAppId("1400760132");
        //设置模板id
        req.setTemplateId("1596763");
        //需要添加的参数
        String[] templateParamSet1 = {templateCode, "30"};
        req.setTemplateParamSet(templateParamSet1);
        // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
        SendSmsResponse resp = null;
        try {
            resp = client.SendSms(req);
            log.info("响应体为={}",SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }

    }
}
