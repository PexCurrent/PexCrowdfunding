package cn.qingwei.graduationproject.pojo;

import lombok.Data;

@Data
public class ItemCount {
    private int totalcount;  //
    private int checkcount;
    private int onlinecount;  //待发货
    private  int outlinecount;//待收货

}
