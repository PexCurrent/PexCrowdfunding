package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.service.CommentService;
import cn.qingwei.graduationproject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ItemService itemService;

    @RequestMapping("/fabiao")
    @ResponseBody
    public Map<String,Object> fabiao(String msg, HttpServletRequest request, HttpServletResponse response){
        int uid=(int) request.getSession().getAttribute("user_id");
        int cid=(int) request.getSession().getAttribute("crowdfunding_id");
        Integer rs=commentService.fabiao(uid,cid,msg);
        Map<String,Object> result=new HashMap<>();


        if (rs!=0){
            result.put("status",1);
            result.put("msg","你已经成功发表评论");

        }else {
            result.put("status",0);
            result.put("msg","系统繁忙,请稍后再试");
        }

        return result;

    }

    @RequestMapping("/fabiao2")
    @ResponseBody
    public Map<String,Object> fabiao2(String msg, HttpServletRequest request, HttpServletResponse response){
        int uid=(int) request.getSession().getAttribute("user_id");
        int cid=(int) request.getSession().getAttribute("reservationid");
        Integer rs=commentService.fabiao(uid,cid,msg);
        Map<String,Object> result=new HashMap<>();


        if (rs!=0){
            result.put("status",1);
            result.put("msg","你已经成功发表评论");

        }else {
            result.put("status",0);
            result.put("msg","系统繁忙,请稍后再试");
        }

        return result;

    }


    @RequestMapping("/fabiao3")
    @ResponseBody
    public Map<String,Object> fabiao3(String msg, HttpServletRequest request, HttpServletResponse response){
        int uid=(int) request.getSession().getAttribute("user_id");
        int id=(int) request.getSession().getAttribute("item_id");
        int cid=itemService.getcidbyid(id);

        Integer rs=commentService.fabiao(uid,cid,msg);
        Map<String,Object> result=new HashMap<>();


        if (rs!=0){
            result.put("status",1);
            result.put("msg","你已经成功发表评论");

        }else {
            result.put("status",0);
            result.put("msg","系统繁忙,请稍后再试");
        }

        return result;

    }

}
