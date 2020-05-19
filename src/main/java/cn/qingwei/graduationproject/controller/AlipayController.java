package cn.qingwei.graduationproject.controller;

import cn.qingwei.graduationproject.config.AlipayConfig;
import cn.qingwei.graduationproject.pojo.*;
import cn.qingwei.graduationproject.service.*;
import cn.qingwei.graduationproject.util.JsonUtils;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import cn.qingwei.graduationproject.pojo.ItemOrder;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class AlipayController {

    String gatewayUrl="https://openapi.alipaydev.com/gateway.do";
    String appid="2016093000632290";

    String sign_type="RSA2";


//    String privatekey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCm2crJx+qxQ8yTWDIpcXX6tabP6wtLixzaa/hAU2e7BKklzi2BKMImZ2HaeHbVEVqNjQOur6RX1uoAt9Q+vNLBex4HeOkDBsOEsswwDeHj3Jfuw0bIoq7DstwUOCxjrQTNcHL5KGWb5P5hRmsJrvmm7lgY4L5ELeMmWklyhpF91Wqhf5kYkTqulbtMJgWo63CZz0tXbA0ODUzSNaMorlmK8cOOdolU8e9/K/0LmqnzXsF3s7S9/7tFtw1xDhuWwHV0cNN831j0OclAL+SMvfwkbVP8cyJH2EzL3S6hkLScs/zSJGrBFZwUVoTlqUr6TfVKkgqTSZvsHKVm0jZSDBPbAgMBAAECggEAKd9YbAE23du0+HlWxV3LDOsPfPhwsjmsOQiFmacUzjNiO7Gda9+NsdOaFBjNQbWEMyfjd7Bunvi5awq3QjNi9Nlv9NIcL/0/jgrNAXZKbUPMAUiSeAGTuFsnrg9W3IDsRp5kzDCeJ1x1lnX0oM5/qqwAAeZM+ZFGgzZ/XjV1R82ioyEx6VS9aSyNa5RM3RAOBdvlPvhkoaElKPt+nOaPBLTBxGzzA4ybuwz1kG7saLa86QUF2tXXVAOqkFIwqNVtGqIJEVTEQMSjOvt7v72m+jN453eXKro0Rq0s0/9+b0eg6An8nu3n7N0rNsV0mm395KyqxmUiwpeh/MfIToVi8QKBgQDQcV+u7eZPebU2U+Kp5FJ/wvd9Lg1Jsmb6U+Zmv0swGRxtHYQIHKWUKNPqFC3APg7JJg/xt9z6KkNM4RbP46J0onfoPXFNT0E5ucEdyy9LyUZXuxtTRAzATvRW+Y/4NNx9DhGN4eEbnqbPs+u6sR5R7CxRYPcSQ/hWvc8Xk2ivQwKBgQDM6yCBa6xS2zDzawOE1rdue4IKKtbC1BSqtvTa/7KDp/jxf5U7Ryc+v9l1JTXkf+P4IsolvYdxVo2YzyYVuCiyispuNQzs6RXymmw+UvXz1VDuixU+AUSNXIbqCTfAhLLXG21NNPX6lmMkQKgz0KvjxFchwyRWg5K7pBTk5l2DiQKBgHOZjNE9Cu+ktgUtPJWwFLjabds0wRXQJSBLi4oMKSzBoGw7ZFADYyjqA6zcCAuH3zdbLpiFub3b1ns6kiY1PpGk0Q8xlDal76yOAjX9y3siYyqgkCT7pn64yvW5bkjfODD6rm2bifaBZE3ooKFnPUN90Y5IUn4EyCODHOkcTYB1AoGAfg72xCb0r1Pf3R6r34Am/3RDHvYTf27v98TtA/vgWVVvs0CFER5IajWjFIl7icGTAAaGzIi3VWWlXaexChNsFcmDliBUfzk0IzYT+JMYcpN7YgN1YdttzHhrFnXtMiVvzxw0NRaraygwfRC5aIap90fFQiPFquLt1TivEdEVHeECgYBOztH7Ta1ilxUwAiawG/1qZOpK/eRtEumnyktWHkqrygLPetpliC6UwTx6/hdj5sekyaGzFDPCpWo56+BKx/Vp36g9IhTvsc72KUDW4Na2CE+iUxEbb+fpiyuDopovEjYbGt1naPrbgs6WagtDRQZeiNQB6jdupIgw+MYhWfb5xA==";
//    String publickey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApJljwwbDNHMV4/HGKpkuq4p7xS3Lx4VL6mERmdU73aqI97/IZKGI0ZA/j0k5kizHyW+/ypidlnCQW9hP43u4T4IHDykCcW0R2LsY/kvn5b1fyIeuP2a0PbOWA9atVCRs/EIwlSwJQgtiNpoyVV3Y4K5XBq04M0GR4J4vk2c6H84VB3hppM1HXLwm2oEhiafGrHiX5ZpFc+pfTnl2EVmmssUYzzFmj8/nuGzsyDNvO9H0I189udc9YF9aDcbmz3FeNYzbACmoIK6cXqwL6BDT7AeSvFdAJz0iE8gFohNstsaD2rU1vGZTYWNA7y1bzt5ehI+vkrgrf5Thyf/oYG1nEQIDAQAB";
//# 使用密钥字符串
String privatekey ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBSUOE9Zhm/ENBR6ugeLqRp8fz0iObb3GqLxPdDfyu1TPqt4wlaRDQWk8R2ioxN8J+M7pMiTvY6lORnoa6APhrfMo/A51TuzCvreiUHPWczI591asErwSm66enbfEc0PAe+Xrxr82T6EZuo+gxQ3EuYGif0+ULDcNCdJBwctfT+uZloYaoVuMHeqJhaRMWa9sAUX1k29THYPxC3gRig1/bOIvDiW8kVZOMDR28iqphshqIJ6xNZcxr9LOrtTRl71iyzUuzqhAoJTgE3aXY59nDkp0N8ysWOHQzuXoWTQKpkeJHrWLWUaaDhso7DnikfFgb8yqK4VzgZxEr0A9AFLWFAgMBAAECggEAd2av3CHvbKcnUNtabX5HGpWAEuHcdzfICq6cYbEzSDmSocuTOsUFywAO2mNgLxlX/6gqzP71xpEy4MECT0MsMxJaycSM2t3HPK98/opJO/ythZSJ2hHYGNl4rxhem0ODIk9RoJ3OrDEP2WdNl6km8NRDrYAzfLmaxqHFXIHFTEAeWZV09oMsE+FVTvPfhfobvRI1SRTlAo23s5FGOINEi49Zt40Hl/TDsUQYcxaR6FqkE52nJbEB3ePEbm274WkeF9szgPQzJgZ/eE+tM6UyAu6EghipqfCXjBZ9YCzJ7SZlRZqSYRoG4NbFMctiDj45rbN7wY9TCARRTUaK3eH/YQKBgQDhxiAZDeCgYfAlqOo2iQPNNyFjTqdxucF/aqx2Q42MokDTJ5JT5guiiikm5xuXPq0Xgk/39XweWrJDxBNVh6PCcw2GKz6s3t4jZlbh4rNESeBBHuRATuY2xAmnLujQvADB6gCKxX2/KEDQM3SH7S3+QVwDrbBaaqvNdT6hAIrKyQKBgQDbKbJkfdxViLqx758GehwoHxWXnZreWVcb9oU6D1hJCJhIB4BcFTQynjk3lV2kMCmNNhQjVarD/1Qw5d33Lj26tAOptZHnECEwIh0ufSSLNJrLrQKQFemMQJWHTEXSzNqyGYX34WZ0Dst+Wryf7NtHj5neMjBq9QL4E3ivwvN23QKBgECQ3mEL3O9A0uKSOdi6ukGXwI6b0bruOm3r3cI8c2CCjjNUn74qja1AAaDEetl37RxKAgCRTZuIqG5ExpqxHZ3gNT/nYhch7TkGbVZP9KBU2DMHWJMjfSawTWpoOeBnw3exKdKFw/5EzrQ74tl0dLC/lEbhBwv0cYliPDTi6M4xAoGBAIEUStxDAK1j7Ul1bvz6mHDM9gOpMJh6mAKBLDBo7z98j2wE9wXE+0xAwkRJdbCJKT/hNPUmtH/hdu2hxRybIIJBGdMvfHJRnqphvTwvOoG+htkk2diQ/0+6jhb0xj8Epl21CKFZIOWD/Tg7KtMqRnAcVKx4dOnZyljf5vnl4U0FAoGAQwX6as/I3YqKANP8ti1pyp69BUKTcH8mfjjkcwHjkiHJDKQMyGzFRsQZZX8DPA6y9k0EDDIeyKqY0CJNX8uG0JH7GdD6bfEuyp/eQkahSZlKoOdwgxe1U+rEquXdg9qZF6nytBy7hC4F3urxX1UPIDi2mrw0NVpwDVzaGto6ilo=";
String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjR/vIf7+gtVbIIoQ9nYh+Jt/ALePkzHZwZPeDHm/pqxte9dZG+YbP3CP4ECCNOV1sP7nZhbqtOgl4X7/n5OvJ/MHBE5oXqjwPgG5rbLbYRYieTDnUf2bRCii5rLeR/hBN+JprJwDaGCjrbwsTpiZFPt4tX+j5osf7wHKa/cHybznL6Thtpq+YackkeDKsNHdPBDJZympDlp80DcxBIDdQXCfCfAnmC7OUtipBN+7vQ/W+fZhvwjEsFkv/GjW5712OOEGsY+yqSh4geDM5dQ7itm3AwRRmB6Eka5aN1VDOlZfJY4VKKms0JhI2Q5SJmIkaEX/bbXMcxwkH1CrZxLrUwIDAQAB";



    @Autowired
    AlipayConfig alipayConfig;
    //TODO 去支付
    @Autowired
    CrowfundingService crowfundingService;
    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;
    @Autowired
    ItemOrderService itemOrderService;








    @RequestMapping(value = "/alipay/gopay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String gopay(String added_money,String support_description,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
        Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingid);
         int totalmoney=crowdfunding.getGear().getSupportAmount()+Integer.parseInt(added_money);
        System.out.println(added_money);
//        Orders orders=ordersService.selectByPrimaryKey(orderid);
//
//        Product product = productService.selectByPrimaryKey(orders.getProductId());
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
         String notify_url = "http://localhost:8800/alipay/alipayNotifyNotice";
         String return_url = "http://localhost:8800/alipay/alipayReturnNotice";
        alipayRequest.setReturnUrl(notify_url);
        alipayRequest.setNotifyUrl(return_url);
//        request.getSession().setAttribute("ordermsg",support_description);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = String.valueOf(totalmoney);
        //订单名称，必填
        String subject = crowdfunding.getTitle();
        //商品描述，可空
        String body = crowdfunding.getTitle();
        request.getSession().setAttribute("ordermsg",support_description);
        request.getSession().setAttribute("pay_cid",crowdfundingid);



        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "30m";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }


    @RequestMapping(value = "/alipay/alipayReturnNotice")
    public void alipayReturnNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名
        System.out.println("_____");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


            String msg= (String) request.getSession().getAttribute("ordermsg");

            int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
            Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingid);
            int userid= (int) request.getSession().getAttribute("user_id");
            User user=userService.getuserbyid(userid);
            Address address=addressService.getDefaultAddressById(userid);
            Order order=new Order();
            order.setOId(out_trade_no);
            order.setMessage(msg);
            order.setBegin_time(new Date());
            order.setAddress(address.getProvince()+address.getCity()+address.getDistrict()+address.getPlace());
            order.setMobile(address.getMobile());
            order.setReceiver(address.getReceiver());
            order.setMoney(Double.valueOf(total_amount).intValue());
            order.setTrade_no(trade_no);
            orderService.insertOrder(order,crowdfundingid,userid);
            crowfundingService.supportcrowdfunding(crowdfundingid,Double.valueOf(total_amount).intValue());


            System.out.println("********************** 支付成功(支付宝同步通知) **********************");
            System.out.println("* 订单号: {}"+ out_trade_no);
            System.out.println("* 支付宝交易号: {}"+ trade_no);
            System.out.println("* 实付金额: {}"+total_amount);
            System.out.println("* 购买产品: {}");
            System.out.println("***************************************************************");
            response.sendRedirect("/Crowdfunding/"+crowdfundingid);

        }else {
            response.sendRedirect("/index");
        }


    }
    @RequestMapping(value = "/alipay/alipayNotifyNotice")
    public void alipayNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws Exception {



        System.out.println("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
//            String body = new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");
            String msg= (String) request.getSession().getAttribute("ordermsg");
            int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
            Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingid);
            int userid= (int) request.getSession().getAttribute("user_id");
            User user=userService.getuserbyid(userid);
            Address address=addressService.getDefaultAddressById(userid);
            Order order=new Order();
            order.setOId(out_trade_no);
            order.setMessage(msg);
            order.setBegin_time(new Date());
            order.setAddress(address.getProvince()+address.getCity()+address.getDistrict()+address.getPlace());
            order.setMobile(address.getMobile());
            order.setReceiver(address.getReceiver());
            order.setMoney(Double.valueOf(total_amount).intValue());
            order.setTrade_no(trade_no);
            orderService.insertOrder(order,crowdfundingid,userid);
            crowfundingService.supportcrowdfunding(crowdfundingid,Double.valueOf(total_amount).intValue());









                System.out.println("********************** 支付成功(支付宝异步通知) **********************");
                System.out.println("* 订单号: {}"+ out_trade_no);
                System.out.println("* 支付宝交易号: {}"+trade_no);
                System.out.println("* 实付金额: {}"+total_amount);
//                System.out.println("* 购买产品: {}"+product.getName());
                System.out.println("***************************************************************");

            System.out.println("支付成功...");
            response.sendRedirect("/Crowdfunding/"+crowdfundingid);
            }


        else {
            System.out.println("支付, 验签失败...");
            response.sendRedirect("/index");

        }


    }



    @RequestMapping(value = "/alipay/gofreepay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String gofreepay(String added_money,String description,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
        Crowdfunding crowdfunding=crowfundingService.getCrowfundingbyId(crowdfundingid);


        System.out.println(added_money);
//        Orders orders=ordersService.selectByPrimaryKey(orderid);
//
//        Product product = productService.selectByPrimaryKey(orders.getProductId());
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        String notify_url = "http://localhost:8800/alipay/alipayfreeNotifyNotice";
        String return_url = "http://localhost:8800/alipay/alipayfreeReturnNotice";
        alipayRequest.setReturnUrl(notify_url);
        alipayRequest.setNotifyUrl(return_url);
//        request.getSession().setAttribute("ordermsg",support_description);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = added_money;
        //订单名称，必填
        String subject = crowdfunding.getTitle()+" "+"无偿打赏";
        //商品描述，可空
        String body = description;
        request.getSession().setAttribute("ordermsg",description);


        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "30m";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }



    @RequestMapping(value = "/alipay/alipayfreeReturnNotice")
    public void alipayfreeReturnNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名
        System.out.println("_____");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


            String msg= (String) request.getSession().getAttribute("ordermsg");

            int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
            crowfundingService.Freesupportcrowdfunding(crowdfundingid,Double.valueOf(total_amount).intValue());


            System.out.println("********************** 支付成功(支付宝同步通知) **********************");
            System.out.println("* 订单号: {}"+ out_trade_no);
            System.out.println("* 支付宝交易号: {}"+ trade_no);
            System.out.println("* 实付金额: {}"+total_amount);
            System.out.println("* 购买产品: {}");
            System.out.println("***************************************************************");
            response.sendRedirect("/Crowdfunding/"+crowdfundingid);

        }else {
            response.sendRedirect("/index");
        }


    }
    @RequestMapping(value = "/alipay/alipayfreeNotifyNotice")
    public void alipayfreeNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws Exception {



        System.out.println("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
//            String body = new String(request.getParameter("body").getBytes("ISO-8859-1"),"UTF-8");
            String msg= (String) request.getSession().getAttribute("ordermsg");
            int crowdfundingid= (int) request.getSession().getAttribute("pay_cid");
            crowfundingService.Freesupportcrowdfunding(crowdfundingid,Double.valueOf(total_amount).intValue());
            System.out.println("********************** 支付成功(支付宝异步通知) **********************");
            System.out.println("* 订单号: {}"+ out_trade_no);
            System.out.println("* 支付宝交易号: {}"+trade_no);
            System.out.println("* 实付金额: {}"+total_amount);
//                System.out.println("* 购买产品: {}"+product.getName());
            System.out.println("***************************************************************");

            System.out.println("支付成功...");
            response.sendRedirect("/Crowdfunding/"+crowdfundingid);
        }


        else {
            System.out.println("支付, 验签失败...");
            response.sendRedirect("/index");

        }


    }

    @RequestMapping("/alipayRefundRequest")
    @ResponseBody
    public    Map<String,Object> alipayRefund(String uvid,String trade_no,String money,String cid){
        if (cid==null){
            System.out.println("ssss");
            return null;
        }
       if (money==null){
           System.out.println("ssssttt");
           return null;
       }
        System.out.println(money);
        System.out.println(cid);
        System.out.println(uvid);
        System.out.println(trade_no);
        // 发送请求
        Map<String,Object> result=new HashMap<>();
        String strResponse = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayRefundInfo alidata= new AlipayRefundInfo();//这里我封装了一个类用来传递json数据
            alidata.setOut_trade_no(uvid);
            alidata.setRefund_amount(Double.parseDouble(money));
            alidata.setTrade_no(trade_no);
            request.setBizContent(JsonUtils.ObjectToJson(alidata));//将数据格式化成json格式
            AlipayTradeRefundResponse response;
            response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                orderService.refundsuccess(uvid);
                crowfundingService.refund(Integer.parseInt(cid),Integer.parseInt(money));
                System.out.println("退款成功");//没有提示消息就是好消息strResponse=null
                result.put("status",1);
                result.put("msg","退款成功");
            }else {
                result.put("status",0);

                strResponse=response.getSubMsg();//退款失败的提示信息
                result.put("msg","退款失败"+strResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",0);
            result.put("msg","退款失败出现错误");
        }
        return result;
    }



    @RequestMapping("/refundall")
    @ResponseBody
    public Map<String,Object> refundall(int cid){
        Map<String,Object> result=new HashMap<>();
        List<Order> orders=orderService.getallingorderbycid(cid);
        for (Order order:orders){
            try {
                Refund(order,cid);
            } catch (Exception e) {
                e.printStackTrace();
           result.put("status",0);
            }
            result.put("status",1);
            return result;
        }




        return null;

    }

    public void Refund(Order order,int cid) throws  Exception{

        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayRefundInfo alidata= new AlipayRefundInfo();//这里我封装了一个类用来传递json数据
        alidata.setOut_trade_no(order.getOId());
        alidata.setRefund_amount(Double.valueOf(order.getMoney()));
        alidata.setTrade_no(order.getTrade_no());
        request.setBizContent(JsonUtils.ObjectToJson(alidata));//将数据格式化成json格式
        try {
        AlipayTradeRefundResponse response;
        response = alipayClient.execute(request);
        if ("10000".equals(response.getCode())) {
            orderService.refundsuccess(order.getOId());
            crowfundingService.refund(cid,order.getMoney());
            System.out.println("退款成功");//没有提示消息就是好消息strResponse=null

        }

    } catch(Exception e) {
        e.printStackTrace();
        return;
    }


    }




    @RequestMapping(value = "/alipay/goitempay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goitempay(String added_money,String support_description,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id= (int) request.getSession().getAttribute("pay_iid");
        Item item=itemService.getitembyid(id);
        int totalmoney=item.getPrice()+Integer.parseInt(added_money);
        System.out.println(added_money);
//        Orders orders=ordersService.selectByPrimaryKey(orderid);
//
//        Product product = productService.selectByPrimaryKey(orders.getProductId());
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        String notify_url = "http://localhost:8800/alipay/alipayitemNotifyNotice";
        String return_url = "http://localhost:8800/alipay/alipayitemReturnNotice";
        alipayRequest.setReturnUrl(notify_url);
        alipayRequest.setNotifyUrl(return_url);
//        request.getSession().setAttribute("ordermsg",support_description);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no =UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount = String.valueOf(totalmoney);
        //订单名称，必填
        String subject = item.getCrowdfunding().getTitle();
        //商品描述，可空
        String body = item.getCrowdfunding().getTitle();
        request.getSession().setAttribute("ordermsg",support_description);
        request.getSession().setAttribute("pay_iid",id);



        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "30m";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }


    @RequestMapping(value = "/alipay/alipayitemReturnNotice")
    public void alipayitemReturnNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名
        System.out.println("_____");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


            String msg= (String) request.getSession().getAttribute("ordermsg");

            int iid= (int) request.getSession().getAttribute("pay_iid");
            Item item=itemService.getitembyid(iid);


            int userid= (int) request.getSession().getAttribute("user_id");
            User user=userService.getuserbyid(userid);
            Address address=addressService.getDefaultAddressById(userid);

            ItemOrder order= new ItemOrder();
            order.setOId(out_trade_no);
            order.setMessage(msg);
            order.setBegin_time(new Date());
            order.setAddress(address.getProvince()+address.getCity()+address.getDistrict()+address.getPlace());
            order.setMobile(address.getMobile());
            order.setReceiver(address.getReceiver());
            order.setMoney(Double.valueOf(total_amount).intValue());
            order.setTrade_no(trade_no);
            itemOrderService.insertOrder(order,iid,userid);
            itemService.pay(iid);


            System.out.println("********************** 支付成功(支付宝同步通知) **********************");
            System.out.println("* 订单号: {}"+ out_trade_no);
            System.out.println("* 支付宝交易号: {}"+ trade_no);
            System.out.println("* 实付金额: {}"+total_amount);
            System.out.println("* 购买产品: {}");
            System.out.println("***************************************************************");
            response.sendRedirect("/item/"+iid);

        }else {
            response.sendRedirect("/index");
        }


    }
    @RequestMapping(value = "/alipay/alipayitemNotifyNotice")
    public void alipayitemNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws Exception {



        System.out.println("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,publickey, "UTF-8", sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");


            String msg= (String) request.getSession().getAttribute("ordermsg");

            int iid= (int) request.getSession().getAttribute("pay_iid");
            Item item=itemService.getitembyid(iid);


            int userid= (int) request.getSession().getAttribute("user_id");
            User user=userService.getuserbyid(userid);
            Address address=addressService.getDefaultAddressById(userid);

            ItemOrder order= new ItemOrder();
            order.setOId(out_trade_no);
            order.setMessage(msg);
            order.setBegin_time(new Date());
            order.setAddress(address.getProvince()+address.getCity()+address.getDistrict()+address.getPlace());
            order.setMobile(address.getMobile());
            order.setReceiver(address.getReceiver());
            order.setMoney(Double.valueOf(total_amount).intValue());
            order.setTrade_no(trade_no);
            itemOrderService.insertOrder(order,iid,userid);
            itemService.pay(iid);


            System.out.println("********************** 支付成功(支付宝同步通知) **********************");
            System.out.println("* 订单号: {}"+ out_trade_no);
            System.out.println("* 支付宝交易号: {}"+ trade_no);
            System.out.println("* 实付金额: {}"+total_amount);
            System.out.println("* 购买产品: {}");
            System.out.println("***************************************************************");
            response.sendRedirect("/item/"+iid);

        }else {
            response.sendRedirect("/index");
        }


    }





    @RequestMapping("/alipayitemRefundRequest")
    @ResponseBody
    public    Map<String,Object> alipayitemRefund(String uvid,String trade_no,String money,String iid){
        System.out.println(money);
        System.out.println(iid);
        System.out.println(uvid);
        System.out.println(trade_no);
        // 发送请求
        Map<String,Object> result=new HashMap<>();
        String strResponse = null;
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appid, privatekey, "json", "UTF-8", publickey, "RSA2");
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayRefundInfo alidata= new AlipayRefundInfo();//这里我封装了一个类用来传递json数据
            alidata.setOut_trade_no(uvid);
            alidata.setRefund_amount(Double.parseDouble(money));
            alidata.setTrade_no(trade_no);
            request.setBizContent(JsonUtils.ObjectToJson(alidata));//将数据格式化成json格式
            AlipayTradeRefundResponse response;
            response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                itemOrderService.refundsuccess(uvid);
                itemService.refund(Integer.parseInt(iid));
                System.out.println("退款成功");//没有提示消息就是好消息strResponse=null
                result.put("status",1);
                result.put("msg","退款成功");
            }else {
                result.put("status",0);
                strResponse=response.getSubMsg();//退款失败的提示信息
                result.put("msg","退款失败"+strResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",0);
            result.put("msg","退款失败出现错误");
        }
        return result;
    }



}


