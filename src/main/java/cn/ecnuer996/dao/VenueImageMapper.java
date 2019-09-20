package cn.ecnuer996.dao;

import cn.ecnuer996.bean.VenueImage;

public interface VenueImageMapper {
    int insert(VenueImage record);

    int insertSelective(VenueImage record);
}