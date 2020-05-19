package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.pojo.Crowdfunding;
import cn.qingwei.graduationproject.pojo.Item;
import cn.qingwei.graduationproject.service.CrowfundingService;
import cn.qingwei.graduationproject.service.ItemService;
import cn.qingwei.graduationproject.service.OrderService;
import cn.qingwei.graduationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OperationController {
    @Autowired
    UserService userService;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    CrowfundingService crowfundingService;
    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;
@ResponseBody
@RequestMapping("/tosentmsg")
  public Map<String,Object> tosentmsg(int cid,String msg) throws Exception{
    System.out.println(Thread.currentThread().getName());
      Map<String,Object> result=new HashMap<>();
    List<String> emails=userService.getallnotifyemail(cid);
    String title =crowfundingService.gettitlebyid(cid);
    sentemail(emails,msg,title);
    result.put("status",1);

      return result;


  }


  @RequestMapping("/sentitem")
  @ResponseBody
  public Map<String,Object> sentitem(int cid) throws Exception{
      System.out.println(cid);
      Map<String,Object> result=new HashMap<>();
      List<String> emails=userService.getallnotifyemail(cid);
      String title =crowfundingService.gettitlebyid(cid);
      String msg="你的参与项目已经开始发货,请在您的订单详情查看快递等详细信息";
      sentemail(emails,msg,title);
      result.put("status",1);
//         orderService.sentitem(cid);
         crowfundingService.sentitem(cid,4);
      System.out.println("————————");
      result.put("status",1);
      result.put("msg","发货成功");

      return result;


  }







//    @Async
//    public void sentemail(List<String> emails,  String msg,String title){
//        System.out.println(Thread.currentThread().getName());
//        for (String email:emails){
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("你在Pex众筹参与的项目--"+title+"有了一则更新");
//
//        message.setText(msg);
//        message.setTo(email);
//        message.setFrom("17803865542@163.com");
//
//        mailSender.send(message);
//        }
//
//    }

    @RequestMapping("/shangjia")
    @ResponseBody
    public Map<String,Object> shangjia(int cid, int price, int num, HttpServletRequest request){
        System.out.println(cid);
        System.out.println(price);
        System.out.println(num);
        int uid= (int) request.getSession().getAttribute("user_id");
        Map<String,Object> result=new HashMap<>();
        if (itemService.isexititem(cid)!=0){
            result.put("status", 0);
        }else {
            Item item = new Item();
            item.setCid(cid);
            item.setNum(num);
            item.setPrice(price);
            item.setUid(uid);

            itemService.insertintem(item);

            result.put("status", 1);
        }
        return result;


    }

    @RequestMapping("/line")
    @ResponseBody
    public Map<String,Object> line(int id,int status){
        System.out.println(id);
        System.out.println(status);
        Map<String,Object> result=new HashMap<>();

        itemService.changestatus(id,status);
        result.put("status",0);
        return result;


    }

    @RequestMapping("/reline")
    @ResponseBody
    public Map<String,Object> reline(int id,int status){
        System.out.println(id);
        System.out.println(status);
        Map<String,Object> result=new HashMap<>();

        itemService.changestatus(id,status);
        result.put("status",1);
        return result;


    }

    @Async
    public void sentemail(List<String> emails,  String msg,String title)throws Exception{

        for (String email:emails){

            SimpleMailMessage message = new SimpleMailMessage();
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

            //设定mail server
            senderImpl.setHost("smtp.163.com");

            //建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true, "utf-8");

            //设置收件人，寄件人
            messageHelper.setTo(email);
            messageHelper.setFrom("17803865542@163.com");
            messageHelper.setSubject("你在Pex众筹参与的项目--"+title+"已经开始发货");
            //true 表示启动HTML格式的邮件
            messageHelper.setText(msg,true);

            senderImpl.setUsername("17803865542@163.com") ; // 根据自己的情况,设置username
            senderImpl.setPassword("chenqaz19980128") ; // 根据自己的情况, 设置password

            //发送邮件
            senderImpl.send(mailMessage);

            System.out.println("邮件发送成功..");








//            message.setSubject("你在Pex众筹参与的项目--"+title+"有了一则更新");
//
//            message.setText(msg);
//
//            message.setTo(email);
//            message.setFrom("17803865542@163.com");
//
//            mailSender.send(message);
        }

    }


}
