package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.pojo.Address;
import cn.qingwei.graduationproject.pojo.Crowdfunding;
import cn.qingwei.graduationproject.pojo.Item;
import cn.qingwei.graduationproject.service.AddressService;
import cn.qingwei.graduationproject.service.ItemService;
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
public class PayItem {
    @Autowired
    ItemService itemService;
    @Autowired
    AddressService addressService;
    @RequestMapping("payitem/{id}")
    public String gear_support(@PathVariable("id") int id, HttpServletRequest request){
        System.out.println(id+"_____");



        if (itemService.getitembyidandstatus(id,1)==0){
            return "404";
        }else {
            request.getSession().setAttribute("pay_iid",id);
            return "payitem";
        }
    }


    @RequestMapping("/getpayitem")
    @ResponseBody
    public Map<String,Object> gear_support(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result= new HashMap<>();
//        System.out.println("______");
//    Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(id);
//    crowdfunding.setId(id);
//    System.out.println(crowdfunding);
//    modelMap.put("crowdfunding",crowdfunding);

        int iid= (int) request.getSession().getAttribute("pay_iid");
//        System.out.println(crowdfundingid+"----------------");

        int user_id= (int) request.getSession().getAttribute("user_id");
        Item item=itemService.getitembyid(iid);
//        Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingid);
//        System.out.println(crowdfunding);
        List<Address> addresses=addressService.getAddressByUserId(user_id);
//        System.out.println(addresses);
        Address defaultaddress=addressService.getDefaultAddressById(user_id);

        result.put("item",item);
        result.put("addresses",addresses);
        result.put("defaultaddress",defaultaddress);
//        System.out.println(result);
        return result;
    }






    @ResponseBody
    @RequestMapping("/payitem/deladdress/{id}")
    public Map<String,Object> deladdress(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> result= new HashMap<>();

        int coun= addressService.delAddressById(id);
        if (coun!=1){
            result.put("msg","未知错误");
            result.put("status",false);
        }else {
            result.put("msg","你已经成功删除");
            result.put("status",true);
        }

        return result;




    }


    @RequestMapping("/payitem/insertaddress")
    public  void insertaddress(Address address,HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uid=(int)request.getSession().getAttribute("user_id");
        int iid=(int)request.getSession().getAttribute("pay_iid");
        addressService.insertAddress(address,uid);




        response.sendRedirect("/payitem/"+iid);



    }

    @ResponseBody
    @RequestMapping("/payitem/getedit")
    public Address getedit(HttpServletRequest request, HttpServletResponse response){

        System.out.println(addressService.getAddressById(( Integer.parseInt(request.getParameter("id")))));


        return addressService.getAddressById(( Integer.parseInt(request.getParameter("id"))));
    }

    @RequestMapping("/payitem/updateaddress")
    public void updateaddress(Address address,HttpServletRequest request, HttpServletResponse response)throws IOException {
        System.out.println(address);
        System.out.println(addressService.updateAddress(address));
        int iid=(int)request.getSession().getAttribute("pay_iid");
        response.sendRedirect("/payitem/"+iid);
    }

    @RequestMapping("payitem/setdefaultaddress/{id}")

    public void setdefaultaddress(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(addressService.qudefault());
        System.out.println(addressService.defaultaddress(id));

        int iid=(int)request.getSession().getAttribute("pay_iid");
        response.sendRedirect("/payitem/"+iid);


    }

}
