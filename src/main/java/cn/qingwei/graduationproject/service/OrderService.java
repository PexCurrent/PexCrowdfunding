package cn.qingwei.graduationproject.service;

import cn.qingwei.graduationproject.pojo.Order;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderService {
    public Integer insertOrder(Order order, int cid,int uid);
    OrderStatusCount StatusordercountbyUId(int uid);
    List<Order> getOrderbyid(int uid);
    List<Order> getOrderkindbyid(int uid,int status);
    Order getOrderbyoid(String oid);
    List<Order> getallrefundorder();
    List<Order> getrefundorderbyoid(String oId);
    Integer refundsuccess(String oId);
    Integer refundfail(String oId);
    Integer changeorderstatus(int status,String oId);
    Integer editorderaddress(String oID,String receiver,String address,String mobile);
    Integer sentitem(int cid);
    List<Order> getallingorderbycid(int cid);
    List<Order> getallingorderbycidandsearch(int cid,String search);
    Integer updateexpress_id(String express_id,String oId);
    List<Order> getallorder();
    List<Order> getallorderbyoid(String oId);



}
