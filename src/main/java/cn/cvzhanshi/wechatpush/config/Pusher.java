package cn.cvzhanshi.wechatpush.config;


import cn.cvzhanshi.wechatpush.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;


public class Pusher {

    private static String appId = "wxb586496cea81d8ad";
    private static String secret = "3897a2c74a99341e6b0f0375473b78e6";

    public static void main(String[] args) {
        push();
    }

    public static void push() {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                .toUser("oV9pc5oL8GHrGwYZolFqsJ8us1f8")
                .toUser("oV9pc5rAA0C4Qmf89uNtDaQ94KsA")//用户微信id
                .templateId("v6pi1uZbsmdpp4ebNX4QUypANnk88N5mFHZxdnbhv_0")//消息模板id
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("riqi", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi", weather.getText_now(), "#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
        templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("windclass", weather.getWind_class() + "", "#42B857"));
        templateMessage.addData(new WxMpTemplateData("winddir", weather.getWind_dir() + "", "#B95EA3"));
        templateMessage.addData(new WxMpTemplateData("caihongpi", CaiHongPiUtils.getCaiHongPi(), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri1", JiNianRiUtils.getBirthday_Jo() + "", "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("shengri2", JiNianRiUtils.getBirthday_Hui() + "", "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("en", map.get("en") + "", "#C71585"));
        templateMessage.addData(new WxMpTemplateData("zh", map.get("zh") + "", "#C71585"));
        String beizhu = "wxx❤hjq";
        if (JiNianRiUtils.getLianAi() % 365 == 0) {
            beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if (JiNianRiUtils.getBirthday_Jo() == 0) {
            beizhu = "今天是hjq生日，生日快乐呀！";
        }
        if (JiNianRiUtils.getBirthday_Hui() == 0) {
            beizhu = "今天是wxx生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu", beizhu, "#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
