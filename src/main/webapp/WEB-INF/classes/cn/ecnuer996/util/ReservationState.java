package cn.ecnuer996.util;

import java.util.HashMap;
import java.util.Map;

public class ReservationState {
    public final static Map<Integer,String> states;
    static{
        states=new HashMap<>();
        states.put(1,"未开始");
        states.put(2,"已完成");
        states.put(3,"已取消");
        states.put(4,"未到场");
    }
}
