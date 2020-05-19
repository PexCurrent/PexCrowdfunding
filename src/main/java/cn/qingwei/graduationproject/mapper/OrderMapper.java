package cn.qingwei.graduationproject.mapper;

import cn.qingwei.graduationproject.pojo.Gear;
import cn.qingwei.graduationproject.pojo.Order;
import cn.qingwei.graduationproject.pojo.OrderStatusCount;
import cn.qingwei.graduationproject.pojo.StatusCount;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {

    @Insert("insert into crowdfungdingorder(oId,trade_no,receiver,money,mobile,Address,message,begin_time,cid,uid) values(#{order.oId},#{order.trade_no},#{order.receiver},#{order.money},#{order.mobile},#{order.Address},#{order.message},#{order.begin_time},#{cid},#{uid})")
//    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer insertOrder(@Param(value="order")Order order, @Param(value="cid")int cid, @Param(value="uid")int uid);


    @Select("select * from crowdfungdingorder where uid=#{uid} order by begin_time desc")
    @Results({

            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })

    public List<Order> getOrderbyid(int uid);


    @Select("select count(1) totalcount,sum(IF((`status`=0),1,0)) shippingcount,sum(IF((`status`=1),1,0)) receivingcount, sum(IF((`status`=2),1,0)) completedcount ,sum(IF((`status`=3),1,0)) refundingcount,sum(IF((`status`=4),1,0)) refundedcount from crowdfungdingorder where uid=#{uid}")
    public OrderStatusCount StatusordercountbyUId(int uid);

    @Select("select * from crowdfungdingorder where uid=#{uid} and status=#{status} order by begin_time desc")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getOrderkindbyid(int uid,int status);

    @Select("select * from crowdfungdingorder where oId=#{oId}")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public Order getOrderbyoid(String oid);


@Select("select * from crowdfungdingorder where status=3")
@Results({
        @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
})
    public List<Order> getallrefundorder();


    @Select("select * from crowdfungdingorder where status=3 and oId like  '%${oId}%'  ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getrefundorderbyoid(String oId);


    @Select("select * from crowdfungdingorder ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getallorder();


    @Select("select * from crowdfungdingorder where oId like  '%${oId}%'  ")
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getallorderbyoid(String oId);


    @Update("update  crowdfungdingorder set status=4 where oId=#{oId}")
    public Integer refundsuccess(String oId);


    @Update("update  crowdfungdingorder set status=0 where oId=#{oId}")  //拒绝退款 说明已经发货了
    public Integer refundfail(String oId);

    @Update("update  crowdfungdingorder set status=#{status} where oId=#{oId}")  //拒绝退款 说明已经发货了
    public Integer changeorderstatus(int status,String oId);


    @Update("update  crowdfungdingorder set receiver=#{receiver},address=#{address},mobile=#{mobile} where oId=#{oId}")
    Integer editorderaddress(String oId,String receiver,String address,String mobile);


    @Update("update  crowdfungdingorder set status=1 where cid=#{cid}")
    public Integer sentitem(int cid);



    @Select("select  * from crowdfungdingorder  where cid=#{cid} and (status=0 or status=1)")  //设置status=1 是因为有的账单可能之前已经退款过了 不允许他重复退款
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getallingorderbycid(int cid);



    @Select("select  * from crowdfungdingorder  where cid=#{cid} and (status=0 or status=1) and oId like '%${search}%' ")  //设置status=1 是因为有的账单可能之前已经退款过了 不允许他重复退款
    @Results({
            @Result(column="cid",property="crowdfunding",one = @One(select="cn.qingwei.graduationproject.mapper.Crowfundingmapper.getCrowfundingbyId",fetchType= FetchType.EAGER))
    })
    public List<Order> getallingorderbycidandsearch(int cid,String search);



    @Update("update crowdfungdingorder set express_id=#{express_id} where oId=#{oId}")
    public  Integer updateexpress_id(String express_id,String oId);
}



