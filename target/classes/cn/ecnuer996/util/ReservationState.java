package cn.ecnuer996.util;

import java.util.HashMap;
import java.util.Map;

public class ReservationState {
    public final static Map<Integer,String> states;
    static{
        states=new HashMap<>();
        states.put(1,"δ��ʼ");
        states.put(2,"�����");
        states.put(3,"��ȡ��");
        states.put(4,"δ����");
    }
}
