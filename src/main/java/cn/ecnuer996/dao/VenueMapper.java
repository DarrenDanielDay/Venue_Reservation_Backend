package cn.ecnuer996.dao;

import cn.ecnuer996.bean.Venue;

public interface VenueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Venue record);

    int insertSelective(Venue record);

    Venue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Venue record);

    int updateByPrimaryKey(Venue record);
}