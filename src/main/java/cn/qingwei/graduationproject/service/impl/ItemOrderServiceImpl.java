package cn.qingwei.graduationproject.service.impl;

import cn.qingwei.graduationproject.mapper.ItemOrderMapper;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.service.ItemOrderService;
import cn.qingwei.graduationproject.service.ItemService;
import cn.qingwei.graduationproject.pojo.ItemOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemOrderServiceImpl implements ItemOrderService {
    @Autowired
    ItemOrderMapper itemOrderMapper;
    @Override
    public Integer insertOrder(ItemOrder order, int iid, int uid) {
        return itemOrderMapper.insertOrder(order,iid,uid);
    }

    @Override
    public OrderStatusCount StatusordercountbyUId(int uid) {
        return itemOrderMapper.StatusordercountbyUId(uid);
    }

    @Override
    public List<ItemOrder> getOrderbyid(int uid) {
        return itemOrderMapper.getOrderbyid(uid);
    }

    @Override
    public List<ItemOrder> getOrderkindbyid(int uid, int status) {
        return itemOrderMapper.getOrderkindbyid(uid,status);
    }

    @Override
    public ItemOrder getOrderbyoid(String oid) {
        return itemOrderMapper.getOrderbyoid(oid);
    }

    @Override
    public List<ItemOrder> getallrefundorder() {
        return itemOrderMapper.getallrefundorder();
    }

    @Override
    public List<ItemOrder> getrefundorderbyoid(String oId) {
        return itemOrderMapper.getrefundorderbyoid(oId);
    }

    @Override
    public Integer refundsuccess(String oId) {
        return itemOrderMapper.refundsuccess(oId);
    }

    @Override
    public Integer refundfail(String oId) {
        return itemOrderMapper.refundfail(oId);
    }

    @Override
    public Integer changeorderstatus(int status, String oId) {
        return itemOrderMapper.changeorderstatus(status,oId);
    }

    @Override
    public Integer editorderaddress(String oID, String receiver, String address, String mobile) {
        return itemOrderMapper.editorderaddress(oID,receiver,address,mobile);
    }

    @Override
    public Integer sentitem(int iid) {
        return itemOrderMapper.sentitem(iid);
    }

    @Override
    public List<ItemOrder> getallingorderbyiid(int iid) {
        return itemOrderMapper.getallingorderbyiid(iid);
    }

    @Override
    public List<ItemOrder> getallingorderbyiidandsearch(int iid, String search) {
        return itemOrderMapper.getallingorderbyiidandsearch(iid,search);
    }

    @Override
    public Integer updateexpress_id(String express_id, String oId) {
        return itemOrderMapper.updateexpress_id(express_id,oId);
    }

    @Override
    public List<ItemOrder> getallorder() {
        return itemOrderMapper.getallorder();
    }

    @Override
    public List<ItemOrder> getallorderbyoid(String oId) {
        return itemOrderMapper.getallorderbyoid(oId);
    }


}
