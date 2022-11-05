package com.poke.Comon;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;

public class SendMessage {
    public static void main(String [] args) {
        try{
            // ʵ����һ����֤���������Ҫ������Ѷ���˻�secretId��secretKey,�˴�����ע����Կ�Եı���
            // ��Կ��ǰ��https://console.cloud.tencent.com/cam/capi��վ���л�ȡ
            Credential cred = new Credential("SecretId", "SecretKey");
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
            String[] phoneNumberSet1 = {"13033480262"};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId("1400760132");
            req.setTemplateId("1596763");

            String[] templateParamSet1 = {"123456", "30"};
            req.setTemplateParamSet(templateParamSet1);

            // ���ص�resp��һ��SendSmsResponse��ʵ��������������Ӧ
            SendSmsResponse resp = client.SendSms(req);
            // ���json��ʽ���ַ����ذ�
            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}

