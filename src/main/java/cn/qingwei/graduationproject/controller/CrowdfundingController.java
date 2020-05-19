package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.pojo.*;
import cn.qingwei.graduationproject.service.CommentService;
import cn.qingwei.graduationproject.service.CrowfundingService;
import cn.qingwei.graduationproject.service.GearService;
import cn.qingwei.graduationproject.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class CrowdfundingController {
    @Autowired
    CrowfundingService crowfundingService;
    @Autowired
    GearService gearService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;


@RequestMapping("/release")
    public String release(){
        return "release";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/postrelease")
    @ResponseBody
    public Map<String,Object> release(@RequestBody Crowdfunding crowdfunding , HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println(crowdfunding);
        Map<String,Object> result=new HashMap<>();
        String imgurl=(String) request.getSession().getAttribute("imgurl");
        String craeatname=(String)request.getSession().getAttribute("user_name");
        int user_id=(int)request.getSession().getAttribute("user_id");
        Gear gear=crowdfunding.getGear();




        crowdfunding.setShowpic(imgurl);
        crowdfunding.setUid(user_id);
        crowdfunding.setCreatername(craeatname);
        System.out.println(crowfundingService.insertCrowfunding(crowdfunding));
        System.out.println(crowdfunding.getId());


        gear.setCid(crowdfunding.getId());

        if (request.getSession().getAttribute("gear_img")==null){
            gear.setGearimg("");
        }else {
            gear.setGearimg((String) request.getSession().getAttribute("gear_img"));
        }
        System.out.println(gear);
        crowfundingService.insertcrowfunding_details(crowdfunding.getDetails(),crowdfunding.getId());
        gearService.insertGear(gear);

        result.put("message", "登陆成功 2秒后跳转到主页面");
        result.put("status",true);
        return result;
    }
    private static void copy(String url1, String url2) throws Exception {
        FileInputStream in = new FileInputStream(new File(url1));
        FileOutputStream out = new FileOutputStream(new File(url2));
        byte[] buff = new byte[512];
        int n = 0;
        System.out.println("复制文件：" + "\n" + "源路径：" + url1 + "\n" + "目标路径："
                + url2);
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();
        System.out.println("复制完成");
    }


    @PostMapping("/uploadimgfile")
    @ResponseBody
    public String uploadimgfile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws  Exception{

        System.out.println(file);
        System.out.println("________");
        System.out.println(file.getOriginalFilename());
        String folderPath = "/gearimg/";
        String imageFilePath= ResourceUtils.getURL("classpath:static").getPath()+folderPath;
// 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + suffixName;
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageFilePath + newFileName));
        request.getSession().setAttribute("gear_img",folderPath + newFileName);

    return "ss";
    }
   @RequestMapping("/Crowdfunding/{id}")
    public String getCrowdfunding(@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response){
       if (crowfundingService.CrowfundingisexitbyIdbystatus(id,1)!=0||crowfundingService.ischeck(id)==0){
           System.out.println("test");
           request.getSession().setAttribute("crowdfunding_id",id);
           return "Crowdfunding";
       }else {
           return "error";
       }




    }


    @RequestMapping("/Crowdfundingdemo/{id}")
    public String getCrowdfundingdemo(@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response){
        System.out.println("test");
        request.getSession().setAttribute("crowdfunding_id",id);
        return "Crowdfundingdemo";





    }


@RequestMapping("/getcrowdfundingbyid")
@ResponseBody
    public Map<String,Object> getcrowdfundingbyid(HttpServletRequest request, HttpServletResponse response){

    int crowdfundingId= (int) request.getSession().getAttribute("crowdfunding_id") ;
    System.out.println(crowdfundingId);
    Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingId);
    crowdfunding.setId(crowdfundingId);
    System.out.println(crowdfunding);
    User user=userService.getuserbyid(crowdfunding.getUid());
    String creater_acvtor=user.getAcvtor();
    String  creater_habitation=user.getHabitation();
    List<Comment> comments=commentService.getcomment(crowdfundingId);
    Map<String,Object> result=new HashMap<>();
    result.put("crowdfunding",crowdfunding);
    result.put("creater_acvtor",creater_acvtor);
    result.put("creater_habitation",creater_habitation);
    result.put("comments",comments);

    return result;


    }

    @RequestMapping("/getmycrowdfundingbyuid")
    @ResponseBody
    public Map<String,Object> getmycrowdfundingbyuid(HttpServletRequest request, HttpServletResponse response){

        int userid= (int) request.getSession().getAttribute("user_id");
        List<Crowdfundingplus> crowdfundings=crowfundingService.MyCrowfundingbyUId(userid);
        StatusCount statusCount=crowfundingService.StatusCrowfundingcountbyUId(userid);
        List<Crowdfundingplus> shenhecrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,0);
        List<Crowdfundingplus> ingcrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,1);
        List<Crowdfundingplus> scuesscrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,2);
        List<Crowdfundingplus> failcrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,3);
        List<Crowdfundingplus> competedcrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,4);
        List<Crowdfundingplus> refundcrowdfundings=crowfundingService.MyCrowfundkindingbyUId(userid,5);



        Map<String,Object> result=new HashMap<>();
        result.put("crowdfundings",crowdfundings);
        result.put("statusCount",statusCount);
        result.put("shenhecrowdfundings",shenhecrowdfundings);
        result.put("ingcrowdfundings",ingcrowdfundings);
        result.put("scuesscrowdfundings",scuesscrowdfundings);
        result.put("failcrowdfundings",failcrowdfundings);
        result.put("competedcrowdfundings",competedcrowdfundings);
        result.put("refundcrowdfundings",refundcrowdfundings);



        return result;


    }





}
