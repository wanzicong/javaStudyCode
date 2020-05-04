package tensquare.sms.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtils {
    //初始化ascClient需要的几个参数,开发者无需替换
    //短信API产品名称（短信产品名固定，无需修改）
    private static final String product = "Dysmsapi";
    //短信API产品域名（接口地址固定，无需修改）
    private static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    //你的accessKeyId
//    private static final String accessKeyId = "LTAIEBP7rsGr4EsC";
//    //你的accessKeySecret
//    private static final String accessKeySecret = "koUKOhSPAQGNDREi66kYaSntPRhFpA";

    //发送短信

    /**
     * @param phoneNumbers  待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
     * @param signName      短信签名-可在短信控制台中找到
     * @param templateCode  短信模板-可在短信控制台中找到
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为。
     *                      友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
     * @param outId         outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
     */
    public static SendSmsResponse sendSms(String phoneNumbers, String signName, String templateCode, String templateParam, String outId, final String accessKeyId, final String accessKeySecret) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //替换成你的AK
        //        final String accessKeyId = "yourAccessKeyId";//你的accessKeyId,参考本文档步骤2
//        final String accessKeySecret = "yourAccessKeySecret";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phoneNumbers);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam(templateParam);
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(outId);
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//            //请求成功
//        }
        return sendSmsResponse;
    }

    /**
     * 短信查询,用于查询短信发送的状态，是否成功到达终端用户手机等
     *
     * @param phoneNumbers 短信接收号码,如果需要查询国际短信,号码前需要带上对应国家的区号,区号的获取详见国际短信支持国家信息查询API接口
     * @param bizId        发送流水号,从调用发送接口返回值中获取，可选
     * @param sendDate     短信发送日期，格式yyyyMMdd,支持最近30天记录查询
     * @param currentPage  当前页码
     * @param pageSize     页大小,Max=50
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySmsSendDetails(String phoneNumbers, String bizId, String sendDate, long currentPage, long pageSize, final String accessKeyId, final String accessKeySecret) throws ClientException {
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //此处需要替换成开发者自己的AK信息
//        final String accessKeyId = "yourAccessKeyId";
//        final String accessKeySecret = "yourAccessKeySecret";
        //初始化ascClient
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumbers);
        //可选-调用发送短信接口时返回的BizId
        request.setBizId(bizId);
        //必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd，如20170513
        request.setSendDate(sendDate);
        //必填-页大小，如10L
        request.setPageSize(pageSize);
        //必填-当前页码从1开始计数，如1L
        request.setCurrentPage(currentPage);
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        //获取返回结果
//        if(querySendDetailsResponse.getCode() != null && querySendDetailsResponse.getCode().equals("OK")){
//            //代表请求成功
//        }
        return querySendDetailsResponse;
    }

}
