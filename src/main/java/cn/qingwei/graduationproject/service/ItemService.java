package cn.qingwei.graduationproject.service;

import cn.qingwei.graduationproject.pojo.Item;
import cn.qingwei.graduationproject.pojo.ItemCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ItemService {
    public Integer insertintem(Item item);
    int isexititem(int cid);
    List<Item> getallitem();
    public List<Item> getitembysearch(String search );
    Integer changestatus(int id,int status);
    List<Item> getallitembystatus(int status);
    List<Item> getallitembystatusandsearch(int status,String search);
    ItemCount statuscountbyUId(int uid);
    List<Item> getallitembyuid(int uid);
    List<Item> getallitembyuidandstatus(int uid,int status);

    Integer delitem(int id);
    public Integer  minusitemnum(int id);
    public  Integer changeitemnum(int id,int itemnum);
    List<Item> getallitemsbyrules(String rules);
    List<Item> getallitemsbyruleslimit(String rules,int currentPage,int pageSize);

    Integer getallonlineitemnum();

    Integer itemischeckbyid(int id);
    Item getitembyid(int id);
    Integer getcidbyid(int id);
    Integer getitembyidandstatus(int id,int status);


    Integer pay(int id);

    Integer refund(int id);
}
