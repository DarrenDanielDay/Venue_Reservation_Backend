package cn.ecnuer996.dao;

import cn.ecnuer996.bean.VenueImage;

import java.util.List;

public interface VenueImageMapper {
    int insert(VenueImage record);

    int insertSelective(VenueImage record);

    List<String> getVenueImagesByVenueId(Integer venueId);

    String getVenueCoverByVenueId(Integer venueId);
}