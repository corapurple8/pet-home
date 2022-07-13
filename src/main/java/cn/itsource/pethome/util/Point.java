package cn.itsource.pethome.util;

import lombok.Data;

/**
 * 存放经纬度
 * 百度地图工具类
 */
@Data
public class Point {
    //经度
    private Double lng;
    //维度
    private Double lat;
}
