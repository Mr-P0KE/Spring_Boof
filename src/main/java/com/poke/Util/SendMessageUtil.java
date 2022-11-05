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


    private static String  tag =  "���˵�ѧϰ��¼";
    public static void send(String signName, String templateCode,String phoneNumbers){
        // ʵ����һ����֤���������Ҫ������Ѷ���˻�secretId��secretKey,�˴�����ע����Կ�Եı���
        // ��Կ��ǰ��https://console.cloud.tencent.com/cam/capi��վ���л�ȡ
        Credential cred = new Credential(secretId, secretKey);

        // ʵ����һ��httpѡ���ѡ�ģ�û�����������������
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        // ʵ����һ��clientѡ���ѡ�ģ�û�����������������
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // ʵ����Ҫ�����Ʒ��client����,clientProfile�ǿ�ѡ��
        SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

        // ʵ����һ���������,ÿ���ӿڶ����Ӧһ��request����
        SendSmsRequest req = new SendSmsRequest();
        //���ñ�ǩ���� //����д
        req.setSignName(tag);

        String[] phoneNumberSet1 = {phoneNumbers};
        req.setPhoneNumberSet(phoneNumberSet1);

        //����Ӧ��id
        req.setSmsSdkAppId("1400760132");
        //����ģ��id
        req.setTemplateId("1596763");
        //��Ҫ��ӵĲ���
        String[] templateParamSet1 = {templateCode, "30"};
        req.setTemplateParamSet(templateParamSet1);
        // ���ص�resp��һ��SendSmsResponse��ʵ��������������Ӧ
        SendSmsResponse resp = null;
        try {
            resp = client.SendSms(req);
            log.info("��Ӧ��Ϊ={}",SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }

    }
}
