package cn.qingwei.graduationproject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Data
@Alias("item")
@Component
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Item {
    private int id;
    private  int cid;
    private int price;
    private  int num;
    private ItemStatus itemstatus;
    private Crowdfunding crowdfunding;
    private int uid;
    private  User user;
    private int time;

}
