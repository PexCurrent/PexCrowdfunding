package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.pojo.*;
import cn.qingwei.graduationproject.service.CommentService;
import cn.qingwei.graduationproject.service.CrowfundingService;
import cn.qingwei.graduationproject.service.ItemService;
import cn.qingwei.graduationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    CrowfundingService crowfundingService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @RequestMapping("/getmyitembyuid")
    @ResponseBody
    public Map<String, Object> getmycrowdfundingbyuid(HttpServletRequest request, HttpServletResponse response) {

        int uid = (int) request.getSession().getAttribute("user_id");
        List<Item> items = itemService.getallitembyuid(uid);
        ItemCount statusCount = itemService.statuscountbyUId(uid);
        List<Item> checkitems = itemService.getallitembyuidandstatus(uid, 0);
        List<Item> onlineitems = itemService.getallitembyuidandstatus(uid, 1);
        List<Item> outlineitems = itemService.getallitembyuidandstatus(uid, 2);


        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("statuscount", statusCount);
        result.put("checkitems", checkitems);
        result.put("onlineitems", onlineitems);
        result.put("outlineitems", outlineitems);


        return result;


    }

    @RequestMapping("/delitem")
    @ResponseBody
    public Map<String, Object> delitem(int id) {
        System.out.println(id);

        Map<String, Object> result = new HashMap<>();

        itemService.delitem(id);
        result.put("status", 0);
        return result;

    }


    @RequestMapping("/allitem/{way}/{cur}")
    public String itemindexpage(@PathVariable("way") String way, @PathVariable("cur") int cur,HttpServletRequest request, HttpServletResponse response){
        if (way.equals("top_time")||way.equals("low_price")){
            request.getSession().setAttribute("way",way);
            request.getSession().setAttribute("cur",cur);
            return "item/indexpage";
        }else {
            return "404";
        }
    }

    @RequestMapping("/allitem/{way}")
    public String iitemindexpage(@PathVariable("way") String way,HttpServletRequest request, HttpServletResponse response){
        if (way.equals("top_time")||way.equals("low_price")){
            request.getSession().setAttribute("way",way);
            request.getSession().setAttribute("cur",1);
            return "item/indexpage";
        }else {
            return "404";
        }
    }

    @RequestMapping("getitems")
    @ResponseBody
    public Map<String,Object> getreservationindex(HttpServletRequest request, HttpServletResponse response){
        int sum=12;
        String way= (String) request.getSession().getAttribute("way");
        int  cur= (int) request.getSession().getAttribute("cur");

        List<Item> items;
        Map<String,Object> result=new HashMap<>();
        if (way.equals("top_time")){
            items=itemService.getallitemsbyruleslimit("id",(cur-1)*sum,sum);
        }else{
            items=itemService.getallitemsbyruleslimit("price",(cur-1)*sum,sum);
        }
        int count=itemService.getallonlineitemnum();
        int cur_page=cur;
        int page_count=count/sum;
        result.put("count",count);
        result.put("items",items);
        result.put("cur_page",cur_page);
        result.put("page_count",page_count);
        result.put("way",way);


        return result;
    }


    @RequestMapping("item/{id}")

    public String itempage(@PathVariable("id") int id,HttpServletRequest request, HttpServletResponse response) {
        if(itemService.itemischeckbyid(id)!=0){
            return "404";
        }else {
            request.getSession().setAttribute("item_id",id);
            return "item";
        }


    }

    @RequestMapping("/getitembyid")
    @ResponseBody
    public Map<String,Object> getitembyid(HttpServletRequest request, HttpServletResponse response){

        int item_id= (int) request.getSession().getAttribute("item_id") ;
        System.out.println(item_id);

        Item item=itemService.getitembyid(item_id);

        int cid=itemService.getcidbyid(item_id);

        String creater_acvtor=item.getUser().getAcvtor();
        String  creater_habitation=item.getUser().getHabitation();
        List<Comment> comments=commentService.getcomment(cid);
        Map<String,Object> result=new HashMap<>();
        result.put("item",item);
        result.put("creater_acvtor",creater_acvtor);
        result.put("creater_habitation",creater_habitation);
        result.put("comments",comments);
        result.put("crowdfunding",item.getCrowdfunding());
        System.out.println("ok");
        return result;


    }


}
