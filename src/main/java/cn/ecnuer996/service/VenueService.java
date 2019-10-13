package cn.ecnuer996.service;

import cn.ecnuer996.bean.Reservation;
import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.bean.Site;
import cn.ecnuer996.dao.ReservationMapper;
import cn.ecnuer996.dao.SiteMapper;
import cn.ecnuer996.dao.VenueImageMapper;
import cn.ecnuer996.dao.VenueMapper;
import cn.ecnuer996.transfer.VenueInList;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("VenueService")
public class VenueService {

    @Autowired
    private VenueMapper venueDao;
    @Autowired
    private SiteMapper siteDao;
    @Autowired
    private VenueImageMapper venueImageDao;
    @Autowired
    private ReservationMapper reservationDao;

    final static int ReservationPeriod=30; // 预定的最小时间单元，单位为分钟

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

    public JSONObject getSiteTimes(int siteId,String date) throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysql时区问题尚未解决，只能将日期按照GMT+0时区解析
        Date realDate=formatter.parse(date);
        List<Reservation> reservations=reservationDao.selectBySiteIdAndDate(siteId,realDate);
        reservations.sort(new ReservationComparator()); // 按照预约开始时段给所有预约升序排序
        Site site=siteDao.selectByPrimaryKey(siteId);
        Venue venue=venueDao.selectByPrimaryKey(site.getVenueId());
        int minutes=(int)((venue.getEndTime().getTime()-venue.getBeginTime().getTime())/60000);
        int beginMinutes=(int)(((venue.getBeginTime().getTime()%86400000)/60000));

        boolean[] timeList=new boolean[minutes/ReservationPeriod]; //标记各时段是否被预约
        for(int i=0;i<timeList.length;++i)
            timeList[i]=true;

        for(Reservation r:reservations){
            for(int i=r.getBeginTime();i<r.getEndTime();++i){
                timeList[i]=false; // 标记此时段已被预约
            }
        }
        JSONObject ret=new JSONObject();
        List<JSONObject> siteTimes=new ArrayList<JSONObject>();
        for(int i=0;i<timeList.length;++i){
            JSONObject siteTime=new JSONObject();
            siteTime.put("period",printPeriod(beginMinutes,i));
            siteTime.put("bookable",timeList[i]);
            siteTimes.add(siteTime);
        }
        ret.put("siteTimes",siteTimes);
        ret.put("bookDate",date);
        return ret;
    }

    private String printPeriod(int beginMinutes,int periodNum){ // 仅针对时间段单元30分钟时
        int totalMins=beginMinutes+periodNum*ReservationPeriod;
        int hour=totalMins/60;
        int min=totalMins%60;
        if(min==30)
            return hour+":30~"+(hour+1)+":00";
        else
            return hour+":00"+"~"+hour+":30";
//        int endMins=totalMins+30;
//        int endHour=endMins/60;
//        int endMin=endMins%60;
//        return hour+":"+min+"~"+endHour+":"+endMin;
    }

    class ReservationComparator implements Comparator<Reservation>{

        @Override
        public int compare(Reservation r1, Reservation r2) {
            return r1.getBeginTime()-r2.getBeginTime();
        }
    }
}


