package cn.ecnuer996.service;

import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.bean.Site;
import cn.ecnuer996.dao.SiteMapper;
import cn.ecnuer996.dao.VenueImageMapper;
import cn.ecnuer996.dao.VenueMapper;
import cn.ecnuer996.transfer.VenueInList;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service("VenueService")
public class VenueService {

    @Autowired
    private VenueMapper venueDao;
    @Autowired
    private SiteMapper siteDao;
    @Autowired
    private VenueImageMapper venueImageDao;

    public ArrayList<Venue> getVenueByName(String name){
        return venueDao.selectByVenueName(name);
    }

    public ArrayList<Site> getSiteByVenueId(int venue_id){
        ArrayList<Site> sites=siteDao.selectByVenueId(venue_id);
        String urlPrefix="https://ecnuer996.cn/images";
        for(Site site:sites){
            site.setImage(urlPrefix+site.getImage());
        }
        return sites;
    }

    public JSONObject getAllVenues(){
        List<Venue> rawVenues=venueDao.selectAllVenues();
        List<VenueInList> listVenues=new ArrayList<VenueInList>();
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysql时区问题尚未解决，只能将日期按照GMT+0时区解析
        String urlPrefix="https://ecnuer996.cn/images";
        for(Venue venue:rawVenues){
            VenueInList venueItem=new VenueInList();
            venueItem.id=venue.getId();
            venueItem.name=venue.getName();
            venueItem.address=venue.getAddress();
            venueItem.beginTime=formatter.format(venue.getBeginTime());
            venueItem.endTime=formatter.format(venue.getEndTime());
            venueItem.cover=urlPrefix+venueImageDao.getVenueCoverByVenueId(venue.getId());
            listVenues.add(venueItem);
        }
        JSONObject venues=new JSONObject();
        venues.put("venues",listVenues);
        return venues;
    }

    public JSONObject getVenueDetail(int venue_id){
        Venue venue=venueDao.selectByPrimaryKey(venue_id);
        List<String> images=venueImageDao.getVenueImagesByVenueId(venue_id);
        String urlPrefix="https://ecnuer996.cn/images";
        for(int i=0;i<images.size();++i){
            images.set(i,urlPrefix+images.get(i));
        }
        JSONObject venueDetail=new JSONObject();
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysql时区问题尚未解决，只能将日期按照GMT+0时区解析
        venueDetail.put("id",venue.getId());
        venueDetail.put("name",venue.getName());
        venueDetail.put("address",venue.getAddress());
        venueDetail.put("introduction",venue.getIntroduction());
        venueDetail.put("phone",venue.getPhone());
        venueDetail.put("beginTime",formatter.format(venue.getBeginTime()));
        venueDetail.put("endTime",formatter.format(venue.getEndTime()));
        venueDetail.put("images",images);
        ArrayList sites=getSiteByVenueId(venue_id);
        venueDetail.put("sites",sites);
        return venueDetail;
    }
}
