package cn.qingwei.graduationproject.service.impl;

import cn.qingwei.graduationproject.mapper.Addressmapper;
import cn.qingwei.graduationproject.mapper.Crowfundingmapper;
import cn.qingwei.graduationproject.pojo.Crowdfunding;
import cn.qingwei.graduationproject.pojo.Crowdfundingplus;
import cn.qingwei.graduationproject.pojo.StatusCount;
import cn.qingwei.graduationproject.service.CrowfundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CrowfundingServiceImpl implements CrowfundingService {

    @Autowired
    Crowfundingmapper crowfundingmapper;

    @Override
    public List<Crowdfunding> finishCrowfundings() {
        return crowfundingmapper.finishCrowfundings();
    }

    @Override
    public List<Crowdfunding> getAllCrowfundingsbysearch(String search) {
        return crowfundingmapper.getAllCrowfundingsbysearch(search);
    }

    @Override
    public List<Crowdfunding> getAllCrowfundings() {
        return crowfundingmapper.getAllCrowfundings();
    }

    @Override
    public List<Crowdfunding> getingCrowfundingsbysearch(String search) {
        return crowfundingmapper.getingCrowfundingsbysearch(search);
    }

    @Override
    public List<Crowdfunding> getingCrowfundings() {
        return crowfundingmapper.getingCrowfundings();
    }

    @Override
    public Integer sentitem(int id, int status) {
        return crowfundingmapper.sentitem(id,status);
    }

    @Override
    public String gettitlebyid(int id) {
        return crowfundingmapper.gettitlebyid(id);
    }

    @Override
    public Integer refund(int id, int supportamout) {
        return crowfundingmapper.refund(id,supportamout);
    }

    @Override
    public Integer checkCrowfundingsbyid(int id) {
        return crowfundingmapper.checkCrowfundingsbyid(id);
    }

    @Override
    public Integer delCrowfundingsbyid(int id) {
        return crowfundingmapper.delCrowfundingsbyid(id);
    }

    @Override
    public List<Crowdfunding> getcheckCrowfundingsbysearch(String search) {
        return crowfundingmapper.getcheckCrowfundingsbysearch(search);
    }

    @Override
    public List<Crowdfunding> getcheckCrowfundings() {
        return crowfundingmapper.getcheckCrowfundings();
    }

    @Override
    public Integer updateCrowfundingstauts(int id, int status) {
        return crowfundingmapper.updateCrowfundingstauts(id,status);
    }

    @Override
    public Integer insertcrowfunding_details(String details, int id) {
        return crowfundingmapper.insertcrowfunding_details(details,id);
    }

    @Override
    public Integer supportcrowdfunding(int id, int supportamout) {
        return crowfundingmapper.supportcrowdfunding(id,supportamout);
    }

    @Override
    public List<Crowdfundingplus> MyCrowfundingbyUId(int uid) {
        return crowfundingmapper.MyCrowfundingbyUId(uid);
    }

    @Override
    public StatusCount StatusCrowfundingcountbyUId(int uid) {
        return crowfundingmapper.StatusCrowfundingcountbyUId(uid);
    }

    @Override
    public List<Crowdfundingplus> MyCrowfundkindingbyUId(int uid, int status) {
        return crowfundingmapper.MyCrowfundkindingbyUId(uid,status);
    }

    @Override
    public Integer Freesupportcrowdfunding(int id, int supportamout) {
        return crowfundingmapper.Freesupportcrowdfunding(id,supportamout);
    }

    @Override
    public Integer CrowfundingisexitbyId(int id) {
        return crowfundingmapper.CrowfundingisexitbyId(id);
    }



    @Override
    public Crowdfunding getCrowfundingbyId(int id) {
        return crowfundingmapper.getCrowfundingbyId(id);
    }

    @Override
    public Integer insertCrowfunding(Crowdfunding crowdfunding) {
        return crowfundingmapper.insertcrowfunding(crowdfunding);
    }


    @Override
    public List<Crowdfunding> getingCrowfundingspage(int currentPage, int pageSize, int status) {
        return crowfundingmapper.getingCrowfundingspage(currentPage,pageSize,status);
    }

    @Override
    public List<Crowdfundingplus> getCrowfundingpluscountbystatus(int status,int currentPage,int pageSize) {
        return crowfundingmapper.getCrowfundingpluscountbystatus(status,currentPage,pageSize);
    }

    @Override
    public Integer getCrowfundingcountbystatus(int status) {
        return crowfundingmapper.getCrowfundingcountbystatus(status);
    }


    @Override
    public List<Crowdfundingplus> getCrowfundingpluscountbyendtime(int status, int currentPage, int pageSize) {
        return crowfundingmapper.getCrowfundingpluscountbyendtime(status,currentPage,pageSize);
    }

    @Override
    public List<Crowdfundingplus> getCrowfundingpluscountbycurrentamout(int status, int currentPage, int pageSize) {
        return crowfundingmapper.getCrowfundingpluscountbycurrentamout(status,currentPage,pageSize);
    }

    @Override
    public List<Crowdfundingplus> getsearch(int status, int currentPage, int pageSize, String search) {
        return crowfundingmapper.getsearch(status,currentPage,pageSize,search);
    }

    @Override
    public Integer getsearchcount(String search) {
        return crowfundingmapper.getsearchcount(search);
    }

    @Override
    public List<Crowdfundingplus> getCrowfundingpluscountbycount(int status, int currentPage, int pageSize) {
        return crowfundingmapper.getCrowfundingpluscountbycount(status,currentPage,pageSize);
    }

    @Override
    public Integer CrowfundingisexitbyIdbystatus(int id, int status) {
        return crowfundingmapper.CrowfundingisexitbyIdbystatus(id,status);
    }

    @Override
    public List<Crowdfundingplus> gettop_timepreheat(int currentPage, int pageSize,int uid) {
        return crowfundingmapper.gettop_timepreheat(currentPage,pageSize, uid);
    }

    @Override
    public List<Crowdfundingplus> gethotpreheat(int currentPage, int pageSize,int uid) {
        return crowfundingmapper.gethotpreheat(currentPage,pageSize, uid);
    }

    @Override
    public Integer getpreheatcount() {
        return crowfundingmapper.getpreheatcount();
    }

    @Override
    public Integer isexitreservation(int id) {
        return crowfundingmapper.isexitreservation(id);
    }

    @Override
    public Crowdfundingplus getreservationbyid(int id,int uid) {
        return crowfundingmapper.getreservationbyid(id,uid);
    }

    @Override
    public Integer updatereservationsum(int id) {
        return crowfundingmapper.updatereservationsum(id);
    }

    @Override
    public Integer queeservationsum(int id) {
        return crowfundingmapper.queeservationsum(id);
    }

    @Override
    public List<Crowdfunding> notifyreservation() {
        return crowfundingmapper.notifyreservation();
    }

    @Override
    public Integer ischeck(int id) {
        return crowfundingmapper.ischeck(id);
    }
}
