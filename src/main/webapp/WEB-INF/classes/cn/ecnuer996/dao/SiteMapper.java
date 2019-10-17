package cn.ecnuer996.dao;

import cn.ecnuer996.bean.Site;

import java.util.ArrayList;

public interface SiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(Integer id);

    ArrayList<Site> selectByVenueId(Integer venue_id);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
}