package cn.ecnuer996.service;

import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.dao.VenueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("VenueService")
public class VenueService {

    @Autowired
    private VenueMapper venueDao;

    public Venue getVenueById(int id){
        return venueDao.selectByPrimaryKey(id);
    }
}
