package cn.qingwei.graduationproject.mapper;

import cn.qingwei.graduationproject.pojo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface  ItemMapper {


    @Insert("insert into item(cid,price,num,uid) values(#{cid},#{price},#{num},#{uid})")

    public Integer insertintem(Item item);

    @Select("select count(1) from item where cid=#{cid} ")
    public int isexititem(int cid);


    @Select("select * from item")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",one = @One(select="cn.qingwei.graduationproject.mapper.Usermapper.getuserbyid",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitem();

    @Select("select * from item,crowdfunding where item.cid=crowdfunding.id and crowdfunding.title like '%${search}%'")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Item> getitembysearch(String search );

@Update("update item set itemstatus=#{status} where id=#{id}")
    public Integer changestatus(int id,int status);


    @Select("select * from item where itemstatus=#{status}")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitembystatus(int status);

    @Select("select * from item,crowdfunding where itemstatus=#{status} and item.cid=crowdfunding.id and crowdfunding.title like '%${search}%' ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitembystatusandsearch(int status,String search);

    @Select("select count(1) totalcount,sum(IF((`itemstatus`=0),1,0)) checkcount,sum(IF((`itemstatus`=1),1,0))  onlinecount, sum(IF((`itemstatus`=2),1,0)) outlinecount from item where uid=#{uid}")
    public ItemCount statuscountbyUId(int uid);

    @Select("select * from item where uid=#{uid}")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitembyuid(int uid);
    @Select("select * from item where uid=#{uid} and itemstatus=#{status}")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitembyuidandstatus(int uid,int status);


   @Delete("delete from item where id=#{id}")
    public Integer delitem(int id);

    @Update("update item set num=num-1 where id=#{id}")
   public Integer  minusitemnum(int id);
    @Update("update item set num=#{itemnum} where id=#{id}")
    public  Integer changeitemnum(int id,int itemnum);


    @Select("select * from item where itemstatus=1 order by #{rules}  ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",one = @One(select="cn.qingwei.graduationproject.mapper.Usermapper.getuserbyid",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitemsbyrules(String rules);


    @Select("select * from item where itemstatus=1 order by #{rules} limit #{currentPage},#{pageSize}  ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",one = @One(select="cn.qingwei.graduationproject.mapper.Usermapper.getuserbyid",fetchType= FetchType.EAGER))
    })
    public List<Item> getallitemsbyruleslimit(String rules,int currentPage,int pageSize);

    @Select("select count(1) from item where itemstatus=1 ")
    public Integer getallonlineitemnum();




    @Select("select count(1) from item where itemstatus=0 and id=#{id}")
    public Integer itemischeckbyid(int id);


    @Select("select * from item where id=#{id}")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",one = @One(select="cn.qingwei.graduationproject.mapper.Usermapper.getuserbyid",fetchType= FetchType.EAGER))
    })
    public Item getitembyid(int id);


    @Select("select cid from item where id=#{id}")
    Integer getcidbyid(int id);

    @Select("select count(1) from item where id=#{id} and itemstatus=#{status}")
    Integer getitembyidandstatus(int id,int status);

    @Update("update item set num=num-1 ,time=time+1 where id=#{id}")
    Integer pay(int id);
    @Update("update item set num=num+1 , time=time-1 where id=#{id}")
    Integer refund(int id);


}
