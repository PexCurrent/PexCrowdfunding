package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.mapper.ItemMapper;
import cn.qingwei.graduationproject.pojo.*;
import cn.qingwei.graduationproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {


    @Autowired
    CrowfundingService crowfundingService;
    @Autowired
    GearService gearService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;

    @Autowired
    ItemOrderService itemOrderService;

    @RequestMapping("/admin")
    public  String admin(HttpServletRequest request){
        if (request.getSession().getAttribute("admin")!=null)

        return "admin";
        else
            return "/admin/login";
    }
    @RequestMapping("/admin/login")
    public  String adminlogin(){

        return "admin/login";
    }
   @ResponseBody
   @RequestMapping("admin/tologin")
   public Map<String,Object> admintologin(String username, String password, HttpServletRequest request){
       Map<String,Object> result=new HashMap<>();
   if (adminService.searchbyusernameandpassword(username,password)>=1){
       request.getSession().setAttribute("admin",true);
      result.put("status",1);
      result.put("msg","登陆成功1秒后跳转到登陆后台管理页面");
   }else {
       result.put("status",0);
       result.put("msg","用户名和密码不正确");
   }
  return result;

   }



    @RequestMapping("admin/logout")
    public void longout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("admin", null);


        response.sendRedirect("/admin/login");

    }

        @ResponseBody
    @RequestMapping("/getcrowfundingtable")
    public Vo getcrowfundingtable(String keyword){
        Vo vo=new Vo();
        List<Crowdfunding> crowdfundings;
        if(keyword==null){

        crowdfundings=crowfundingService.getcheckCrowfundings();

        }else {
            crowdfundings=crowfundingService.getcheckCrowfundingsbysearch(keyword);
        }
        vo.setData(crowdfundings);
        vo.setCount(crowdfundings.size());
        return vo;

    }
    @ResponseBody
    @RequestMapping("/getallcrowfundingtable")
    public Vo getallcrowfundingtable(String keyword){
        Vo vo=new Vo();
        List<Crowdfunding> crowdfundings;
        if(keyword==null){

            crowdfundings=crowfundingService.getAllCrowfundings();

        }else {
            crowdfundings=crowfundingService.getAllCrowfundingsbysearch(keyword);
        }
        vo.setData(crowdfundings);
        vo.setCount(crowdfundings.size());
        return vo;

    }


    @ResponseBody
    @RequestMapping("/getingcrowfundingtable")
    public Vo getingcrowfundingtable(String keyword){
        Vo vo=new Vo();
        List<Crowdfunding> crowdfundings;
        if(keyword==null){

            crowdfundings=crowfundingService.getingCrowfundings();

        }else {
            crowdfundings=crowfundingService.getingCrowfundingsbysearch(keyword);
        }
        vo.setData(crowdfundings);
        vo.setCount(crowdfundings.size());
        return vo;

    }


@RequestMapping("/UVServlet")
@ResponseBody
public Map<String,Object> UVServlet(String uvid){
    int status=0;

    Map<String,Object> result=new HashMap<>();
    System.out.println(uvid);



    try {
        gearService.deletegearbyuid(Integer.parseInt(uvid));
        Integer rs=crowfundingService.delCrowfundingsbyid(Integer.parseInt(uvid));
    }catch (Exception e){
         status=1;
    }

        result.put("status",status);

return  result;



}

    @RequestMapping("/checkpage")

    public String checkpage(){
        return "admin/checkpage";

    }


    @RequestMapping("/ingpage")

    public String ingpage(){
        return "admin/ingpage";

    }


    @RequestMapping("/allpage")

    public String allpage(){
        return "admin/allpage";

    }
    @RequestMapping("/userpage")

    public String serpage(){
        return "admin/userpage";

    }

    @RequestMapping("/checkcrowfunding")
    @ResponseBody
    public Map<String,Object> checkcrowfunding(String uvid){
        Integer status=0;

        Map<String,Object> result=new HashMap<>();
        try {

            status=crowfundingService.checkCrowfundingsbyid(Integer.parseInt(uvid));
        }catch (Exception e){
            status=0;
        }


        result.put("status",status);

        return  result;
    }

    @ResponseBody
    @RequestMapping("/getalluser")
    public Vo getalluser(String keyword){
        Vo vo=new Vo();
        List<User> users;
        if(keyword==null){

            users=userService.getalluser();

        }else {
            users=userService.getalluserbyname(keyword);
        }
        vo.setData(users);
        vo.setCount(users.size());
        return vo;

    }
    @RequestMapping("/activeuser")
    @ResponseBody
    public Map<String,Object> activeuser(String uvid){
        Map<String,Object> result=new HashMap<>();
        int status=0;
        try {
            userService.activeuser(Integer.parseInt(uvid));

        }catch (Exception e){
         status=1;
        }
     result.put("status",status);
        return result;
    }


    @RequestMapping("/getrefundorderpage")
    @ResponseBody
    public Vo getrefundorderpage(String keyword){
        Vo vo=new Vo();
        List<Order> orders;
        if(keyword==null){
            orders=orderService.getallrefundorder();


        }else {
            orders=orderService.getrefundorderbyoid(keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }

    @RequestMapping("/getallorder")
    @ResponseBody
    public Vo getorder(String keyword){
        Vo vo=new Vo();
        List<Order> orders;
        if(keyword==null){
            orders=orderService.getallorder();


        }else {
            orders=orderService.getallorderbyoid(keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }




    @RequestMapping("/refundpage")
    public String refundpage(){
      return "/admin/refundpage";

    }
    @RequestMapping("/refuserefund")
    @ResponseBody
    public Map<String,Object> refuserefund(String uvid){
        System.out.println(uvid);
        Map<String,Object> result=new HashMap<>();
        if (orderService.refundfail(uvid)!=0){
            result.put("status",0);
            result.put("msg","已经拒绝该用户的退款请求");

        }else {
            result.put("status",0);
            result.put("msg","拒绝失败,出现错误");
        }

        return  result;

    }


    @RequestMapping("/manageexpress/{cid}")
    public  String manageexpress(@PathVariable("cid") String cid, HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("manage_id",cid);

        return "manageexpressadmin";
    }


    @RequestMapping("/getallingorderbycid")
    @ResponseBody
    public Vo getallingorderbycid(String keyword, HttpServletRequest request, HttpServletResponse response){
        int cid=Integer.parseInt(request.getSession().getAttribute("manage_id").toString());
        System.out.println(cid);
        Vo vo=new Vo();
        List<Order> orders;
        if(keyword==null){

            orders=orderService.getallingorderbycid(cid);

        }else {
            orders=orderService.getallingorderbycidandsearch(cid,keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }


    @RequestMapping("editexpress")
    @ResponseBody
    public Map<String,Object> editexpress_id(String oid,String express_id){
        System.out.println("asdad");
        System.out.println(oid);
        System.out.println(express_id);
        Map<String,Object> result=new HashMap<>();

        if(orderService.updateexpress_id(express_id,oid)!=0){
            result.put("status",1);
        }else
            result.put("status",0);


        return result;
    }




    @RequestMapping("/allcrowfundingpage")

    public String allcrowfundingpage(){
        return  "allpage";

    }




  @RequestMapping("/orderpage")
    public String orderpage(){
        return "admin/orderpage";
  }
    @RequestMapping("/itemallpage")
    public String itemallpage(){
        return "admin/itemallpage";
    }


    @RequestMapping("/getallitem")
    @ResponseBody
    public Vo getallitem(String keyword){
        Vo vo=new Vo();
        List<Item> items;
        if(keyword==null){
            items=itemService.getallitem();


        }else {
            items=itemService.getitembysearch(keyword);
        }
        vo.setData(items);
        vo.setCount(items.size());
        return vo;

    }



    @RequestMapping("/getcheckitem")
    @ResponseBody
    public Vo getcheckitem(String keyword){
        Vo vo=new Vo();
        List<Item> items;
        if(keyword==null){
            items=itemService.getallitembystatus(0);


        }else {
            items=itemService.getallitembystatusandsearch(0,keyword);
        }
        vo.setData(items);
        vo.setCount(items.size());
        return vo;

    }



    @RequestMapping("/getallitemorder")
    @ResponseBody
    public Vo getallorder(String keyword){
        Vo vo=new Vo();
        List<ItemOrder> orders;
        if(keyword==null){
            orders=itemOrderService.getallorder();


        }else {
            orders=itemOrderService.getallorderbyoid(keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }
    @RequestMapping("/refuseitemrefund")
    @ResponseBody
    public Map<String,Object> refuseitemrefund(String uvid){
        System.out.println(uvid);
        Map<String,Object> result=new HashMap<>();
        if (itemOrderService.refundfail(uvid)!=0){
            result.put("status",0);
            result.put("msg","已经拒绝该用户的退款请求");

        }else {
            result.put("status",0);
            result.put("msg","拒绝失败,出现错误");
        }

        return  result;

    }

    @RequestMapping("/getrefunditemorderpage")
    @ResponseBody
    public Vo getrefunditemorderpage(String keyword){
        Vo vo=new Vo();
        List<ItemOrder> orders;
        if(keyword==null){
            orders=itemOrderService.getallrefundorder();


        }else {
            orders=itemOrderService.getrefundorderbyoid(keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }

    @RequestMapping("/manageitemexpress/{iid}")
    public  String manageitemexpress(@PathVariable("iid") String iid, HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("manage_iid",iid);

        return "manageitemexpressadmin";
    }


    @RequestMapping("/getalliingitemorderbycid")
    @ResponseBody
    public Vo getalliingitemorderbycid(String keyword, HttpServletRequest request, HttpServletResponse response){
        int iid=Integer.parseInt(request.getSession().getAttribute("manage_iid").toString());
        Vo vo=new Vo();
        List<ItemOrder> orders;
        if(keyword==null){
            orders=itemOrderService.getallingorderbyiid(iid);
        }else {
            orders=itemOrderService.getallingorderbyiidandsearch(iid,keyword);
        }
        vo.setData(orders);
        vo.setCount(orders.size());
        return vo;

    }

    @RequestMapping("edititemexpress")
    @ResponseBody
    public Map<String,Object> edititemexpress(String oid,String express_id){

        System.out.println(oid);
        System.out.println(express_id);
        Map<String,Object> result=new HashMap<>();

        if(itemOrderService.updateexpress_id(express_id,oid)!=0){
            result.put("status",1);
        }else
            result.put("status",0);


        return result;
    }

}
