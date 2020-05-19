package cn.qingwei.graduationproject.service.impl;

import cn.qingwei.graduationproject.mapper.ItemMapper;
import cn.qingwei.graduationproject.pojo.Item;
import cn.qingwei.graduationproject.pojo.ItemCount;
import cn.qingwei.graduationproject.service.ItemService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
        ItemMapper itemMapper;

    @Override
    public Integer insertintem(Item item) {
        return itemMapper.insertintem(item);
    }

    @Override
    public int isexititem(int cid) {
        return itemMapper.isexititem(cid);
    }

    @Override
    public List<Item> getallitem() {
        return itemMapper.getallitem();
    }

    @Override
    public List<Item> getitembysearch(String search) {
        return itemMapper.getitembysearch(search);
    }

    @Override
    public Integer changestatus(int id,int status) {
        return itemMapper.changestatus(id,status);
    }

    @Override
    public List<Item> getallitembystatus(int status) {
        return itemMapper.getallitembystatus(status);
    }

    @Override
    public List<Item> getallitembystatusandsearch(int status, String search) {
        return itemMapper.getallitembystatusandsearch(status,search);
    }

    @Override
    public ItemCount statuscountbyUId(int uid) {
        return itemMapper.statuscountbyUId(uid);
    }

    @Override
    public List<Item> getallitembyuid(int uid) {
        return itemMapper.getallitembyuid(uid);
    }

    @Override
    public List<Item> getallitembyuidandstatus(int uid, int status) {
        return itemMapper.getallitembyuidandstatus(uid,status);
    }

    @Override
    public Integer delitem(int id) {
        return itemMapper.delitem(id);
    }

    @Override
    public Integer minusitemnum(int id) {
        return itemMapper.minusitemnum(id);
    }

    @Override
    public Integer changeitemnum(int id, int itemnum) {
        return itemMapper.changeitemnum(id,itemnum);
    }

    @Override
    public List<Item> getallitemsbyrules(String rules) {
        return itemMapper.getallitemsbyrules(rules);
    }

    @Override
    public List<Item> getallitemsbyruleslimit(String rules, int currentPage, int pageSize) {
        return itemMapper.getallitemsbyruleslimit(rules,currentPage,pageSize);
    }

    @Override
    public Integer getallonlineitemnum() {
        return itemMapper.getallonlineitemnum();
    }


    @Override
    public Integer itemischeckbyid(int id) {
        return itemMapper.itemischeckbyid(id);
    }

    @Override
    public Item getitembyid(int id) {
        return itemMapper.getitembyid(id);
    }

    @Override
    public Integer getcidbyid(int id) {
        return itemMapper.getcidbyid(id);
    }

    @Override
    public Integer getitembyidandstatus(int id, int status) {
        return itemMapper.getitembyidandstatus(id,status);
    }

    @Override
    public Integer pay(int id) {
        return itemMapper.pay(id);
    }

    @Override
    public Integer refund(int id) {
        return itemMapper.refund(id);
    }
}
