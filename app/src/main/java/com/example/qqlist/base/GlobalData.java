package com.example.qqlist.base;

public class  GlobalData {


    /**
     * sp保存本地数据关键key
     */
    public static final String sp_key_0 = "sp_key_0";//标记：用户登录状态。value为用户id
    public static final String sp_key_1 = "sp_key_1";//标记：设备无网，可以继续操作一小时，0：上次有网；1：无网，开始操作；2：无网，结束操作
    public static final String sp_key_2= "sp_key_2";//标记：当前界面是否在操控界面。1：是；0：不是
    public static final String sp_key_3= "sp_key_3";//标记：时卡套餐使用中(体雕)，保存临时数据。结构：oid_projectEndtime_orderUseId_packageUseId
    public static final String sp_key_4= "sp_key_4";//标记：时卡套餐使用中（爆脂），保存临时数据。结构：oid_projectEndtime_orderUseId_packageUseId

}
