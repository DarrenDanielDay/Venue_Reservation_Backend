package cn.ecnuer996.dao;

import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.bean.Site;

import java.util.ArrayList;

public interface VenueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Venue record);

    int insertSelective(Venue record);

    Venue selectByPrimaryKey(Integer id);

    ArrayList<Venue> selectByVenueName(String name);

    int updateByPrimaryKeySelective(Venue record);

    int updateByPrimaryKey(Venue record);
}