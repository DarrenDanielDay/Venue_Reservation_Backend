package cn.ecnuer996.dao;

import cn.ecnuer996.bean.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reservation record);

    int insertSelective(Reservation record);

    Reservation selectByPrimaryKey(Integer id);

    List<Reservation> selectByUserId(Integer id);

    List<Reservation> selectBySiteIdAndDate(int siteId, Date date);

    Reservation selectLatestReservationByUserId(int userId);

//    Reservation selectByUserIdAndBookTime(int userId,Date date);

    int updateByPrimaryKeySelective(Reservation record);

    int updateByPrimaryKey(Reservation record);

}