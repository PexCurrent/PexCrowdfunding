package cn.qingwei.graduationproject.mapper;


import cn.qingwei.graduationproject.pojo.Crowdfunding;
import cn.qingwei.graduationproject.pojo.Crowdfundingplus;
import cn.qingwei.graduationproject.pojo.StatusCount;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface Crowfundingmapper {

    @Insert("insert into crowdfunding(title,Showpic,content,begin_date,end_date,targetamount,creatername,uid) values(#{title}," +
            "#{Showpic},#{content},#{begin_date},#{end_date},#{targetamount},#{creatername},#{uid})")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer insertcrowfunding(Crowdfunding crowdfunding);

    @Update("update crowdfunding set details=#{details} where id=#{id} ")

    public Integer insertcrowfunding_details(String details,int id);




@Select("select count(1) from crowdfunding where id=#{id} ")
@Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer CrowfundingisexitbyId(int id);



    @Select("select count(1) from crowdfunding where id=#{id} and status=#{status} and date_format(now(),'%Y-%m-%d')>=begin_date ")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer CrowfundingisexitbyIdbystatus(int id,int status);



    @Select("select * from crowdfunding where id=#{id}")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    @Results(
            value = {
                    @Result(property = "gear",column = "id",one = @One(select = "cn.qingwei.graduationproject.mapper.GearMapper.getGearbyID",fetchType= FetchType.EAGER))
            })

    public Crowdfunding getCrowfundingbyId(int id);


    @Update("update crowdfunding set currentamout=currentamout+#{supportamout},supporttime=supporttime+1 where id=#{id} ")
//    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer supportcrowdfunding(int id,int supportamout);

    @Update("update crowdfunding set currentamout=currentamout+#{supportamout},supporttime=supporttime+1,freesupporttime=freesupporttime+1 where id=#{id} ")
    public Integer Freesupportcrowdfunding(int id,int supportamout);



    @Select("select  crowdfunding.* ,IF(crowdfunding.begin_date>=NOW() ,0,1) AS isbegin from crowdfunding where uid=#{uid}")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    @Results(
            value = {
                    @Result(property = "gear",column = "id",one = @One(select = "cn.qingwei.graduationproject.mapper.GearMapper.getGearbyID",fetchType= FetchType.EAGER))
            })
    public List<Crowdfundingplus> MyCrowfundingbyUId(int uid);


    @Select("select count(1) totalcount,sum(IF((`status`=0),1,0)) Reviewedcount,sum(IF((`status`=1),1,0)) Releasecount, sum(IF((`status`=2),1,0)) Successcount,sum(IF((`status`=3),1,0)) Failedcount ,sum(IF((`status`=4),1,0)) competedcount,sum(IF((`status`=5),1,0)) refundcount from crowdfunding where uid=#{uid}")
    public StatusCount StatusCrowfundingcountbyUId(int uid);


    @Select("select crowdfunding.*,IF(crowdfunding.begin_date>=NOW() ,0,1) AS isbegin  from crowdfunding where uid=#{uid} and status=#{status} ")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    @Results(
            value = {
                    @Result(property = "gear",column = "id",one = @One(select = "cn.qingwei.graduationproject.mapper.GearMapper.getGearbyID",fetchType= FetchType.EAGER))
            })
    public List<Crowdfundingplus> MyCrowfundkindingbyUId(int uid,int status);

    @Select("select * from crowdfunding where date_format(now(),'%Y-%m-%d')>=end_date and status=1")
    public List<Crowdfunding> finishCrowfundings();

    @Select("update crowdfunding set status=#{status} where id=#{id} and status=1")
    public Integer updateCrowfundingstauts(int id,int status);

    @Select("select * from crowdfunding where  status=0")
    public List<Crowdfunding> getcheckCrowfundings();

    @Select("select * from crowdfunding where  title like   '%${search}%' and status=0 ")
    public List<Crowdfunding> getcheckCrowfundingsbysearch(String search);
    @Delete("delete from crowdfunding where id=#{id}")
    public Integer delCrowfundingsbyid(int id);

    @Update("update crowdfunding set status=1 where id=#{id} ")
    public Integer checkCrowfundingsbyid(int id);

    @Select("select * from crowdfunding where  title like   '%${search}%'  and status=1")
    public List<Crowdfunding> getingCrowfundingsbysearch(String search);

    @Select("select * from crowdfunding where status=1")
    public List<Crowdfunding> getingCrowfundings();

    @Select("select * from crowdfunding where  title like   '%${search}%'  ")
    public List<Crowdfunding> getAllCrowfundingsbysearch(String search);

    @Select("select * from crowdfunding ")
    public List<Crowdfunding> getAllCrowfundings();

    @Update("update crowdfunding set currentamout=currentamout-#{supportamout},supporttime=supporttime-1 where id=#{id} ")
//    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer refund(int id,int supportamout);

    @Select("select title from crowdfunding where id=#{id}")
    public  String gettitlebyid(int id);
    @Select("update crowdfunding set status=#{status} where id=#{id} and status=2")
    public Integer sentitem(int id,int status);


    @Select("select * from crowdfunding where status=#{status} limit #{currentPage},#{pageSize} ")
    public List<Crowdfunding> getingCrowfundingspage(int currentPage,int pageSize,int status);


    @Select("select count(1) from crowdfunding where status=#{status} and now()>=begin_date ")
    public Integer getCrowfundingcountbystatus(int status);


    @Select("SELECT crowdfunding.*,user.acvtor FROM crowdfunding,user WHERE user.id=crowdfunding.uid AND crowdfunding.status=#{status} and now()>=begin_date ORDER BY begin_date desc limit #{currentPage},#{pageSize} ")
    public List<Crowdfundingplus> getCrowfundingpluscountbystatus(int status,int currentPage,int pageSize);


    @Select("SELECT crowdfunding.*,user.acvtor FROM crowdfunding,user WHERE user.id=crowdfunding.uid AND crowdfunding.status=#{status} and now()>=begin_date ORDER BY end_date  limit #{currentPage},#{pageSize} ")
    public List<Crowdfundingplus> getCrowfundingpluscountbyendtime(int status,int currentPage,int pageSize);


    @Select("SELECT crowdfunding.*,user.acvtor FROM crowdfunding,user WHERE user.id=crowdfunding.uid AND crowdfunding.status=#{status} and now()>=begin_date ORDER BY currentamout desc limit #{currentPage},#{pageSize} ")
    public List<Crowdfundingplus> getCrowfundingpluscountbycurrentamout(int status,int currentPage,int pageSize);


    @Select("SELECT crowdfunding.*,user.acvtor FROM crowdfunding,user WHERE user.id=crowdfunding.uid AND crowdfunding.status=#{status} and now()>=begin_date ORDER BY supporttime desc limit #{currentPage},#{pageSize} ")
    public List<Crowdfundingplus> getCrowfundingpluscountbycount(int status,int currentPage,int pageSize);


    @Select("select count(1) from crowdfunding where  title like   '%${search}%' and status=1")
    public Integer getsearchcount(String search);


    @Select("SELECT crowdfunding.*,user.acvtor FROM crowdfunding,user WHERE crowdfunding.title like   '%${search}%' and user.id=crowdfunding.uid AND crowdfunding.status=#{status} ORDER BY supporttime desc limit #{currentPage},#{pageSize} ")
     public List<Crowdfundingplus> getsearch(int status,int currentPage,int pageSize,String search);



    @Select("SELECT crowdfunding.*,user.acvtor,IF((EXISTS(SELECT * FROM reservation WHERE reservation.cid=crowdfunding.id AND reservation.uid=#{uid}) ),1,0) isreservation FROM crowdfunding,user WHERE user.id=crowdfunding.uid and now()<begin_date and status=1 order by begin_date  limit #{currentPage},#{pageSize}")
    public  List<Crowdfundingplus>  gettop_timepreheat(int currentPage,int pageSize,int uid);


    @Select("SELECT crowdfunding.*,user.acvtor,IF((EXISTS(SELECT * FROM reservation WHERE reservation.cid=crowdfunding.id AND reservation.uid=#{uid}) ),1,0) isreservation FROM crowdfunding,user WHERE user.id=crowdfunding.uid and now()<begin_date and status=1 order by reservationnum  limit #{currentPage},#{pageSize}")
    public  List<Crowdfundingplus>  gethotpreheat(int currentPage,int pageSize,int uid);

    @Select("select count(1) from crowdfunding where status=1 and now()<begin_date ")
    public Integer getpreheatcount();


    @Select("select count(1) from crowdfunding where status=1 and now()<begin_date and id=#{id}")
    public Integer isexitreservation(int id);


    @Select("select crowdfunding.*,IF((EXISTS(SELECT * FROM reservation WHERE reservation.cid=crowdfunding.id AND reservation.uid=#{uid}) ),1,0) isreservation from crowdfunding where status=1 and now()<begin_date and id=#{id}")
    @Results(
            value = {
                    @Result(property = "gear",column = "id",one = @One(select = "cn.qingwei.graduationproject.mapper.GearMapper.getGearbyID",fetchType= FetchType.EAGER))
            })
    public Crowdfundingplus getreservationbyid(int id,int uid);


    @Update("Update crowdfunding set reservationnum=reservationnum+1 where id=#{id}")
    Integer updatereservationsum(int id);

    @Update("Update crowdfunding set reservationnum=reservationnum-1 where id=#{id}")
    Integer queeservationsum(int id);


    @Select("\n" +
            "select * from crowdfunding where status=1 and date_format(now(),'%Y-%m-%d')=begin_date")
    List<Crowdfunding> notifyreservation();

    @Select("select count(1) from crowdfunding where id=#{id} and status=0 ")
    @Options(useGeneratedKeys=true,keyProperty = "id",keyColumn = "id")
    public Integer ischeck(int id);

}
