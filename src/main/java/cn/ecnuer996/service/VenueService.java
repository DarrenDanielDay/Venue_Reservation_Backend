package cn.ecnuer996.service;

import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.bean.Site;
import cn.ecnuer996.dao.SiteMapper;
import cn.ecnuer996.dao.VenueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service("VenueService")
public class VenueService {

    @Autowired
    private VenueMapper venueDao;
    @Autowired
    private SiteMapper siteDao;

    public ArrayList<Venue> getVenueByName(String name){
        return venueDao.selectByVenueName(name);
    }

    public ArrayList<Site> getSiteByVenueId(int venue_id){
        return siteDao.selectByVenueId(venue_id);
    }
}
