package cn.qingwei.graduationproject.controller;


import cn.qingwei.graduationproject.pojo.User;
import cn.qingwei.graduationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller

public class registerConroller {
    @Autowired
    UserService userFeign;
    @Autowired
    JavaMailSenderImpl mailSender;

@RequestMapping("/register")
 public String regiseter(){
     return "register";
 }

@ResponseBody
@RequestMapping(path = "/Toregister",method = RequestMethod.POST,consumes = "application/json")
    public Map<String,Object> Toregister(@RequestBody User user, HttpServletRequest request){
    Map<String,Object> result=new HashMap<>();
    if (userFeign.getuserbyusername(user.getUsername())!=0){
        result.put("message","注册失败 不要重复注册");
        return result;
    }
    System.out.println("____________________________________________________________________________");
//    System.out.println(user);

//    String code=(String) request.getSession().getAttribute("code");
//    System.out.println(code+"PPPP");
//    if(!code.equalsIgnoreCase(user.getId_captcha())){
//        String msg="验证码不正确";
////        request.setAttribute("message", "验证码不正确");
//        System.out.println(msg);
//        return msg;
//    }
//    if (!password2.equals(user.getPassword())){
//        request.setAttribute("message", "两次输入的密码不同！");
//        return "register";
//    }
//    System.out.println(user.getUsername()+"USERNAME");
//    System.out.println(userFeign.getuserbyusername(user.getUsername())+"请重新选择");
//    if (userFeign.getuserbyusername(user.getUsername())!=0){
//        String msg="用户已经存在，请重新选择用户名！";
//        System.out.println(msg);
////        request.setAttribute("message", "用户已经存在，请重新选择用户名！");
//        return msg;
//    }
//    if (userFeign.getuserbyemail(user.getEmail())!=null){
//        String msg=" 该邮箱地址已被注册，请使用别的邮箱！";
//        System.out.println(msg);
////        request.setAttribute("message", "  该邮箱地址已被注册，请使用别的邮箱！");
//        return msg;
//    }
          String confimcode=getRandomString();
            user.setActiveCode(confimcode);
           userFeign.insertUser(user);
    try {
        sentemail2(user.getEmail(),confimcode);
    } catch (Exception e) {
        e.printStackTrace();
        result.put("message","注册失败,请稍后再尝试");
    }
//    request.setAttribute("message", " 查收邮箱!!");
    result.put("message","注册成功 请使用邮箱激活账号 5秒后自动跳转到登陆页面");


    return result;
    }



    public void sentemail(String email,String activeCode){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("欢迎注册访问Pex众筹，你的梦想。我们帮你实现！");

        message.setText("激活请点击:<a href=\"http://localhost:8800/confirm?activeCode="+activeCode+"\">此</a> 非常感谢你能注册Pex众筹");
        message.setTo(email);
        message.setFrom("17803865542@163.com");

        mailSender.send(message);


    }


    public void sentemail2(String email,String activeCode)throws Exception{

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
            messageHelper.setSubject("\"欢迎注册访问Pex众筹，你的梦想。我们帮你实现！\"");
            //true 表示启动HTML格式的邮件
            String msg="激活请点击:<a href=\"http://localhost:8800/confirm?activeCode="+activeCode+"\">此</a> 非常感谢你能注册Pex众筹";
            messageHelper.setText(msg,true);

            senderImpl.setUsername("17803865542@163.com") ; // 根据自己的情况,设置username
            senderImpl.setPassword("chenqaz19980128") ; // 根据自己的情况, 设置password

            //发送邮件
            senderImpl.send(mailMessage);

            System.out.println("邮件发送成功..");




    }











    @GetMapping(value = "/confirm")
    public String checkCode(String activeCode,HttpServletRequest request){
        User user = userFeign.getuserbyactiveCode(activeCode);
        System.out.println("_______");
        System.out.println(user);
        //如果用户不等于null，把用户状态修改status=1
        if (user !=null){
            user.setActiveCode(null);
            user.setIsactive(1);
            //把code验证码清空，已经不需要了
            System.out.println(user);
            userFeign.updateUserbyid(user);
            System.out.println(userFeign.updateUserbyid(user));

            request.setAttribute("confimmessage","你已经成功激活账户！！");
            return "confim";
        }else{
            request.setAttribute("confimmessage","注册信息已经失效，请重新注册");
            return  "confim";
        }


    }



    public static String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


@RequestMapping(path = "/test",method = RequestMethod.POST,consumes = "application/json")
@ResponseBody
   public String test(@RequestBody User user , HttpServletRequest request ){


    System.out.println(user);
       System.out.println("______ssssss");
       return "test";
   }


    @RequestMapping("check_user_exist")
    public @ResponseBody
    Map<String,Object> checkuserexist(HttpServletRequest request){

        Map<String,Object> result=new HashMap<>();

        System.out.println("success");

        System.out.println(request.getParameter("username"));

        if(userFeign.getuserbyusername(request.getParameter("username")) !=0){
            System.out.println("??????????????");
            result.put("message","该用户名已经被注册请选择其他可用的用户名");
            result.put("status",false);
            return result;
        }
        result.put("message","该用户名可用");
        result.put("status",true);
        return result;

    }

//    check_user_exist
@RequestMapping("check_email_exist")
public @ResponseBody
Map<String,Object> checkemailexist(HttpServletRequest request){
    Map<String,Object> result=new HashMap<>();


    System.out.println("success");

    System.out.println(request.getParameter("email"));

    if(userFeign.getuserbyemail(request.getParameter("email")) !=0){
        result.put("message","该邮箱已经被注册 请选择其他可用的邮箱");
        result.put("status",false);
        return result;
    }
    result.put("message","该邮箱可用");
    result.put("status",true);
    return result;
}

    @RequestMapping("/check_captch")
    public @ResponseBody
    Map<String,Object> checkcaptcha(HttpServletRequest request){
        System.out.println("________");
        Map<String,Object> result=new HashMap<>();

       String  captcha=request.getParameter("captcha");

        if(!captcha.equalsIgnoreCase((String) request.getSession().getAttribute("code"))){
            result.put("message","该验证码不正确");
            result.put("status",false);
            return result;
        }
        result.put("message","验证码正确");
        result.put("status",true);
        return result;
    }


}
