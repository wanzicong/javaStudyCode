package tensquare.sms.mqlistener;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tensquare.sms.utils.SmsUtils;

import java.util.Map;

@Component
@RabbitListener(queues = "sms.checkcode")
public class SmsCheckCodeListener {

    @Autowired
    private SmsUtils smsUtils;
    //方法1：直接注入自定义的常量
    //签名
    @Value("${aliyun.sms.sign_name}")
    private String signName;
    //模版编号
    @Value("${aliyun.sms.template_code}")
    private String templateCode;

    //准备发送短信需要的参数
    @RabbitHandler
    public void executeSms(Map<String, String> message) {
        System.out.println("手机号:" + message.get("mobile"));
        System.out.println("验证码:" + message.get("checkcode"));
        String templateParam = "{\"checkcode\":" + message.get("checkcode") + "}";

        //发送短信
        try {
            smsUtils.sendSms(message.get("mobile"), templateCode, signName, templateParam);
            System.out.println("短信发送成功");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
