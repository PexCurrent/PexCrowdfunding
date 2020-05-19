package cn.qingwei.graduationproject.service;

import cn.qingwei.graduationproject.pojo.Order;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.pojo.ItemOrder;

import java.util.List;

public interface ItemOrderService {

    public Integer insertOrder(ItemOrder order, int iid, int uid);
    OrderStatusCount StatusordercountbyUId(int uid);
    List<ItemOrder> getOrderbyid(int uid);
    List<ItemOrder> getOrderkindbyid(int uid,int status);
    ItemOrder getOrderbyoid(String oid);
    List<ItemOrder> getallrefundorder();
    List<ItemOrder> getrefundorderbyoid(String oId);
    Integer refundsuccess(String oId);
    Integer refundfail(String oId);
    Integer changeorderstatus(int status,String oId);
    Integer editorderaddress(String oID,String receiver,String address,String mobile);
    Integer sentitem(int iid);
    List<ItemOrder> getallingorderbyiid(int iid);
    List<ItemOrder> getallingorderbyiidandsearch(int iid,String search);
    Integer updateexpress_id(String express_id,String oId);
    List<ItemOrder> getallorder();
    List<ItemOrder> getallorderbyoid(String oId);


}
