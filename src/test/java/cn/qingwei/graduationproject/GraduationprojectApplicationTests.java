package cn.qingwei.graduationproject;

import cn.qingwei.graduationproject.Schedu.ScheduTask;
import cn.qingwei.graduationproject.config.AlipayConfig;
import cn.qingwei.graduationproject.mapper.*;
import cn.qingwei.graduationproject.pojo.Crowdfundingplus;
import cn.qingwei.graduationproject.service.CommentService;
import com.alipay.api.AlipayClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@SpringBootTest
class GraduationprojectApplicationTests {
@Autowired
Usermapper mapper;
    @Autowired
    GearMapper gearMapper;
    @Autowired
    Crowfundingmapper crowfundingmapper;
    @Autowired
    AlipayConfig config;
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    Addressmapper addressmapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    Adminmapper adminmapper;
    @Autowired
    Usermapper usermapper;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    CommentMapper commentService;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemOrderMapper itemOrderMapper;

    @Test


    void contextLoads() {
//        System.out.println(mapper.getuserbyid(6));
//        Gear gear=new Gear();
//        gear.setCid(7);
//        gear.setWay("sadads");
//        gear.setRelease_month(2);
//        gear.setGearimg("/asdasd/asdadasddsadsada.png");
//        gear.setTitle("sadasdas");
//        gear.setRelease_year(2019);
//        gear.setIntroduction("sdadas");
//        gear.setSupportAmount(88888);
////        System.out.println(gearMapper.insertgear(gear));


//        String details="<p>This is my textarea to be replaced with CKEditor.123123</p>";
//        System.out.println(crowfundingmapper.insertcrowfunding_details(details,10));
//        System.out.println(config.toString());
//        System.out.println(addressmapper.getDefaultAddressById(6));
//        Order a=new Order();
//        a.setOId("12354645");
//        a.setMoney(600000);
//        a.setReceiver("sadada");
//        a.setAddress("sadadasdada-dsada");
//        a.setMobile("117855541222");
//        a.setBegin_time(new Date());
//        a.setMessage("sadadas");
//        System.out.println(orderMapper.insertOrder(a,13,6));
//           String money="300.05";
//           int uid=6;
//        System.out.println(crowfundingmapper.StatusCrowfundingcountbyUId(uid));
//        System.out.println(crowfundingmapper.getCrowfundingbyId(13));
//
//         Gear gear=new Gear();
//         gear.setCid(17);
//         gear.setSupportAmount(200);
//         gear.setIntroduction("sadas");
//         gear.setRelease_year(2017);
//         gear.setRelease_month(3);
//         gear.setWay("dsada");
//         gearMapper.insertgear(gear);

//         List<Crowdfunding> crowdfundings=crowfundingmapper.getcheckCrowfundingsbysearch("s");
//         for(Crowdfunding crowdfunding:crowdfundings){
//             System.out.println(crowdfunding.getId());
//         }

//        List<String> emails=usermapper.getallnotifyemail(29);
//        for (String email:emails){
//            System.out.println(email);
//        }
//        System.out.println(crowfundingmapper.gettitlebyid(29));


        System.out.println(itemOrderMapper.getallorder());


//        System.out.println(addressmapper.test(address));
//        System.out.println(addressmapper.test2(address,6));
//        System.out.println(addressmapper.qudefault());
//        Habitation test=new Habitation();
//        test.setProvince("ss");
//        test.setCity("sff");
//        test.setDistrict("dds");
//        String a=test.toString();
//
//        String[] b=a.split("-");
//        for(String str:b){
//            System.out.println(str);
//        }

    }

}
