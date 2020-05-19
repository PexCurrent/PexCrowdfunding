package cn.qingwei.graduationproject.service;

import cn.qingwei.graduationproject.pojo.Crowdfunding;
import cn.qingwei.graduationproject.pojo.Crowdfundingplus;
import cn.qingwei.graduationproject.pojo.StatusCount;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CrowfundingService {

    Integer insertCrowfunding(Crowdfunding crowdfunding);
    Crowdfunding getCrowfundingbyId(int id);
    Integer CrowfundingisexitbyId(int id);
   Integer insertcrowfunding_details(String details,int id);
   Integer supportcrowdfunding(int id,int supportamout);
   Integer Freesupportcrowdfunding(int id,int supportamout);
   List<Crowdfundingplus> MyCrowfundingbyUId(int uid);
   StatusCount StatusCrowfundingcountbyUId(int uid);
    List<Crowdfundingplus> MyCrowfundkindingbyUId(int uid,int status);
    List<Crowdfunding> finishCrowfundings();
    Integer updateCrowfundingstauts(int id,int status);
    List<Crowdfunding> getcheckCrowfundings();
    List<Crowdfunding> getcheckCrowfundingsbysearch(String search);
    Integer delCrowfundingsbyid(int id);
    Integer checkCrowfundingsbyid(int id);
    List<Crowdfunding> getAllCrowfundingsbysearch(String search);
   List<Crowdfunding> getAllCrowfundings();
    List<Crowdfunding> getingCrowfundingsbysearch(String search);
    List<Crowdfunding> getingCrowfundings();
    Integer refund(int id,int supportamout);
    public  String gettitlebyid(int id);
    public Integer sentitem(int id,int status);
    public List<Crowdfunding> getingCrowfundingspage(int currentPage,int pageSize,int status);
    public Integer getCrowfundingcountbystatus(int status);
    public List<Crowdfundingplus> getCrowfundingpluscountbystatus(int status,int currentPage,int pageSize);
    public List<Crowdfundingplus> getCrowfundingpluscountbyendtime(int status,int currentPage,int pageSize);
    public List<Crowdfundingplus> getCrowfundingpluscountbycurrentamout(int status,int currentPage,int pageSize);
    public List<Crowdfundingplus> getCrowfundingpluscountbycount(int status,int currentPage,int pageSize);
    public Integer getsearchcount(String search);
    List<Crowdfundingplus> getsearch(int status,int currentPage,int pageSize,String search);
    public Integer CrowfundingisexitbyIdbystatus(int id,int status);
    List<Crowdfundingplus>  gettop_timepreheat(int currentPage,int pageSize,int uid);
    List<Crowdfundingplus>  gethotpreheat(int currentPage,int pageSize,int uid);
    Integer getpreheatcount();
    Integer isexitreservation(int id);
    Crowdfundingplus getreservationbyid(int id,int uid);
    Integer updatereservationsum(int id);
    Integer queeservationsum(int id);
    List<Crowdfunding> notifyreservation();
    public Integer ischeck(int id);

}
