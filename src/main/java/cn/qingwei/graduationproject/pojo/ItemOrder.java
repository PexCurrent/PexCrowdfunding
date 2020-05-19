package cn.qingwei.graduationproject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Alias("itemorder")
@Component
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ItemOrder {
    private String oId;
    private Date begin_time;
    private Date end_time;
    private String receiver; //收件人
    private Integer money;  //支持的金额
    private  String mobile;
    private String Address;//地址
    private OrderStatus status;//订单状态
    private String message; //备注
    private Item item;
    private  String trade_no; //支付宝单号;
    private String express_id; //快递单号


}
