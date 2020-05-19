package cn.qingwei.graduationproject.service.impl;

import cn.qingwei.graduationproject.mapper.OrderMapper;
import cn.qingwei.graduationproject.pojo.Order;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public Integer insertOrder(Order order, int cid, int uid) {
        return orderMapper.insertOrder(order,cid,uid);
    }

    @Override
    public Order getOrderbyoid(String oid) {
        return orderMapper.getOrderbyoid(oid);
    }

    @Override
    public Integer refundsuccess(String oId) {
        return orderMapper.refundsuccess(oId);
    }

    @Override
    public Integer sentitem(int cid) {
        return orderMapper.sentitem(cid);
    }

    @Override
    public Integer updateexpress_id(String express_id, String oId) {
        return orderMapper.updateexpress_id(express_id,oId);
    }

    @Override
    public List<Order> getallingorderbycid(int cid) {
        return orderMapper.getallingorderbycid(cid);
    }

    @Override
    public List<Order> getallingorderbycidandsearch(int cid, String search) {
        return orderMapper.getallingorderbycidandsearch(cid,search);
    }

    @Override
    public Integer editorderaddress(String oID, String receiver, String address, String mobile) {
        return orderMapper.editorderaddress(oID,receiver,address,mobile);
    }

    @Override
    public Integer changeorderstatus(int status, String oId) {
        return orderMapper.changeorderstatus(status,oId);
    }

    @Override
    public Integer refundfail(String oId) {
        return orderMapper.refundfail(oId);
    }

    @Override
    public List<Order> getrefundorderbyoid(String oId) {
        return orderMapper.getrefundorderbyoid(oId);
    }

    @Override
    public List<Order> getallrefundorder() {
        return orderMapper.getallrefundorder();
    }

    @Override
    public List<Order> getOrderkindbyid(int uid, int status) {
        return orderMapper.getOrderkindbyid(uid,status);
    }

    @Override
    public List<Order> getOrderbyid(int uid) {
        return orderMapper.getOrderbyid(uid);
    }

    @Override
    public OrderStatusCount StatusordercountbyUId(int uid) {
        return orderMapper.StatusordercountbyUId(uid);
    }

    @Override
    public List<Order> getallorder() {
        return orderMapper.getallorder();
    }

    @Override
    public List<Order> getallorderbyoid(String oId) {
        return orderMapper.getallorderbyoid(oId);
    }
}
