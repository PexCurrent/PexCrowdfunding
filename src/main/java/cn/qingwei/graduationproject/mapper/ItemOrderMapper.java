package cn.qingwei.graduationproject.mapper;

import cn.qingwei.graduationproject.pojo.Gear;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.pojo.StatusCount;
import cn.qingwei.graduationproject.pojo.ItemOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface ItemOrderMapper {

    @Insert("insert into itemorder(oId,trade_no,receiver,money,mobile,Address,message,begin_time,iid,uid) values(#{order.oId},#{order.trade_no},#{order.receiver},#{order.money},#{order.mobile},#{order.Address},#{order.message},#{order.begin_time},#{iid},#{uid})")
//    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer insertOrder(@Param(value="order") ItemOrder order, @Param(value="iid")int iid, @Param(value="uid")int uid);


    @Select("select * from itemorder where uid=#{uid} order by begin_time desc")
    @Results({

            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })

    public List<ItemOrder> getOrderbyid(int uid);


    @Select("select count(1) totalcount,sum(IF((`status`=0),1,0)) shippingcount,sum(IF((`status`=1),1,0)) receivingcount, sum(IF((`status`=2),1,0)) completedcount ,sum(IF((`status`=3),1,0)) refundingcount,sum(IF((`status`=4),1,0)) refundedcount from itemorder  where uid=#{uid}")
    public OrderStatusCount StatusordercountbyUId(int uid);

    @Select("select * from itemorder where uid=#{uid} and status=#{status} order by begin_time desc")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getOrderkindbyid(int uid,int status);

    @Select("select * from itemorder where oId=#{oId}")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public ItemOrder getOrderbyoid(String oid);


    @Select("select * from itemorder where status=3")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getallrefundorder();


    @Select("select * from itemorder where status=3 and oId like  '%${oId}%'  ")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getrefundorderbyoid(String oId);


    @Select("select * from itemorder ")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getallorder();


    @Select("select * from itemorder where oId like  '%${oId}%'  ")
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getallorderbyoid(String oId);


    @Update("update  itemorder set status=4 where oId=#{oId}")
    public Integer refundsuccess(String oId);


    @Update("update  itemorder set status=0 where oId=#{oId}")  //拒绝退款 说明已经发货了
    public Integer refundfail(String oId);

    @Update("update itemorder set status=#{status} where oId=#{oId}")  //拒绝退款 说明已经发货了
    public Integer changeorderstatus(int status,String oId);


    @Update("update  itemorder set receiver=#{receiver},address=#{address},mobile=#{mobile} where oId=#{oId}")
    Integer editorderaddress(String oId,String receiver,String address,String mobile);


    @Update("update  itemorder set status=1 where iid=#{iid}")
    public Integer sentitem(int iid);



    @Select("select  * from itemorder  where (status=0 or status=1) and iid=#{iid}  ")  //设置status=1 是因为有的账单可能之前已经退款过了 不允许他重复退款
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getallingorderbyiid(int iid);



    @Select("select  * from itemorder  where iid=#{iid} and (status=0 or status=1) and oId like '%${search}%' ")  //设置status=1 是因为有的账单可能之前已经退款过了 不允许他重复退款
    @Results({
            @Result(column="iid",property="item",one = @One(select="cn.qingwei.graduationproject.mapper.ItemMapper.getitembyid",fetchType= FetchType.EAGER))
    })
    public List<ItemOrder> getallingorderbyiidandsearch(int iid,String search);



    @Update("update itemorder set express_id=#{express_id},status=1 where oId=#{oId}")
    public  Integer updateexpress_id(String express_id,String oId);
}
