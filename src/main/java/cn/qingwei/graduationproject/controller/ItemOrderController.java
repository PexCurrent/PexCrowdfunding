package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.pojo.ItemOrder;
import cn.qingwei.graduationproject.pojo.Order;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.service.ItemOrderService;
import cn.qingwei.graduationproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemOrderService itemOrderService;


    @RequestMapping("/getmyitemorderbyuid")
    @ResponseBody
    public Map<String,Object> getmyorderbyuid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result=new HashMap<>();
        int user_id=(int)request.getSession().getAttribute("user_id");
        OrderStatusCount orderStatusCount=itemOrderService.StatusordercountbyUId(user_id);
        List<ItemOrder> orders=itemOrderService.getOrderbyid(user_id);
        List<ItemOrder> shippingorders=itemOrderService.getOrderkindbyid(user_id,0);
        List<ItemOrder> receivingorders=itemOrderService.getOrderkindbyid(user_id,1);
        List<ItemOrder> completedorders=itemOrderService.getOrderkindbyid(user_id,2);
        List<ItemOrder> refundingorders=itemOrderService.getOrderkindbyid(user_id,3);
        List<ItemOrder> refundedorders=itemOrderService.getOrderkindbyid(user_id,4);

        result.put("orderStatusCount",orderStatusCount);
        result.put("orders",orders);
        result.put("shippingorders",shippingorders);
        result.put("receivingorders",receivingorders);
        result.put("completedorders",completedorders);
        result.put("refundingorders",refundingorders);
        result.put("refundedorders",refundedorders);



        return result;
    }


    @RequestMapping("/getitemorderdetail")
    @ResponseBody
    public Map<String,Object> getorderdetail(String id,HttpServletRequest request, HttpServletResponse response){
        ItemOrder order=itemOrderService.getOrderbyoid(id);
        Map<String,Object> result=new HashMap<>();
        result.put("order",order);
        return result;

    }

    @RequestMapping("/itemorderrefund")
    @ResponseBody
    public Map<String,Object> refund(String oid,HttpServletRequest request, HttpServletResponse response){
        System.out.println(oid);
        Map<String,Object> result=new HashMap<>();
        if (itemOrderService.changeorderstatus(3,oid)!=0){
            result.put("status",1);
            result.put("msg","已经发送退款请求");
        }else {
            result.put("status",0);
            result.put("msg","请求失败");
        }

        return  result;

    }
    @RequestMapping("/cancelitemrefund")
    @ResponseBody
    public Map<String,Object> cancelrefund(String oid,HttpServletRequest request, HttpServletResponse response){
        System.out.println(oid);
        Map<String,Object> result=new HashMap<>();
        if (itemOrderService.changeorderstatus(0,oid)!=0){
            result.put("status",1);
            result.put("msg","已经取消退款请求");
        }else {
            result.put("status",0);
            result.put("msg","请求失败");
        }

        return  result;

    }

    @RequestMapping("/confirmitemorder")
    @ResponseBody
    public Map<String,Object> confirmitem(String oid,HttpServletRequest request, HttpServletResponse response){
        System.out.println(oid);
        Map<String,Object> result=new HashMap<>();
        if (itemOrderService.changeorderstatus(2,oid)!=0){
            result.put("status",1);
            result.put("msg","已经确认收货");
        }else {
            result.put("status",0);
            result.put("msg","请求失败");
        }

        return  result;

    }
    @RequestMapping("/getitemorder")
    @ResponseBody
    public Map<String,Object> getorder(String oid,HttpServletRequest request, HttpServletResponse response){
        System.out.println(oid);
        Map<String,Object> result=new HashMap<>();
        ItemOrder order=itemOrderService.getOrderbyoid(oid);
        result.put("order",order);
        return  result;



    }

    @RequestMapping("/edititemorderinfo")
    @ResponseBody
    public Map<String,Object> editorderinfo(String oid,String receiver,String address,String mobile, HttpServletRequest request, HttpServletResponse response){
        System.out.println(oid);


        System.out.println("____");
        Map<String,Object> result=new HashMap<>();
        if (itemOrderService.editorderaddress(oid,receiver,address,mobile)!=0){
            result.put("status",1);
            result.put("msg","已经更改地址信息");
        }else {
            result.put("status",0);
            result.put("msg","请求失败");
        }

        return  result;

    }



}
