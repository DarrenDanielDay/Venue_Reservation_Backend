package cn.ecnuer996.service;

import cn.ecnuer996.bean.Reservation;
import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.bean.Site;
import cn.ecnuer996.dao.ReservationMapper;
import cn.ecnuer996.dao.SiteMapper;
import cn.ecnuer996.dao.VenueImageMapper;
import cn.ecnuer996.dao.VenueMapper;
import cn.ecnuer996.transfer.ReservationDetail;
import cn.ecnuer996.transfer.VenueInList;
import cn.ecnuer996.util.ReservationState;
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

    private String urlPrefix="https://ecnuer996.cn/images";

    final static int ReservationPeriod=30; // Ԥ������Сʱ�䵥Ԫ����λΪ����

    public Venue getVenueById(int id) {
        return venueDao.selectByPrimaryKey(id);
    }

    public ArrayList<Venue> getVenueByName(String name){
        return venueDao.selectByVenueName(name);
    }

    public ArrayList<Site> getSiteByVenueId(int venue_id){
        ArrayList<Site> sites=siteDao.selectByVenueId(venue_id);
        for(Site site:sites){
            site.setImage(urlPrefix+site.getImage());
        }
        return sites;
    }

    public JSONObject getAllVenues(){
        List<Venue> rawVenues=venueDao.selectAllVenues();
        List<VenueInList> listVenues=new ArrayList<VenueInList>();
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysqlʱ��������δ�����ֻ�ܽ����ڰ���GMT+0ʱ������
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
        for(int i=0;i<images.size();++i){
            images.set(i,urlPrefix+images.get(i));
        }
        JSONObject venueDetail=new JSONObject();
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysqlʱ��������δ�����ֻ�ܽ����ڰ���GMT+0ʱ������
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
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysqlʱ��������δ�����ֻ�ܽ����ڰ���GMT+0ʱ������
        Date realDate=formatter.parse(date);
        List<Reservation> reservations=reservationDao.selectBySiteIdAndDate(siteId,realDate);
        //reservations.sort(new ReservationComparator()); // ����ԤԼ��ʼʱ�θ�����ԤԼ��������

        Site site=siteDao.selectByPrimaryKey(siteId);
        Venue venue=venueDao.selectByPrimaryKey(site.getVenueId());
//        int minutes=(int)((venue.getEndTime().getTime()-venue.getBeginTime().getTime())/60000);

        int beginMinutes=(int)((((venue.getBeginTime().getTime())/60000)%1440));
        int endMinutes=(int)((((venue.getEndTime().getTime())/60000)%1440));
        int beginId=beginMinutes/ReservationPeriod;
        int endId=endMinutes/ReservationPeriod;

        int[] bookableList=new int[48];
        for(int i=0;i<bookableList.length;++i){
            bookableList[i]=-1;
        }
        for(int i=beginId;i<endId;++i){
            bookableList[i]=0;
        }
        for(Reservation r:reservations){
            for(int i=r.getBeginTime();i<=r.getEndTime();++i){
                bookableList[i]=1; // ��Ǵ�ʱ���ѱ�ԤԼ
            }
        }

//        boolean[] timeList=new boolean[minutes/ReservationPeriod]; //��Ǹ�ʱ���Ƿ�ԤԼ
//        for(int i=0;i<timeList.length;++i)
//            timeList[i]=true;
//
//        for(Reservation r:reservations){
//            for(int i=r.getBeginTime();i<r.getEndTime();++i){
//                timeList[i]=false; // ��Ǵ�ʱ���ѱ�ԤԼ
//            }
//        }
        JSONObject ret=new JSONObject();
        List<JSONObject> siteTimes=new ArrayList<JSONObject>();
        for(int i=0;i<bookableList.length;++i){
            JSONObject siteTime=new JSONObject();
//            siteTime.put("period",printPeriod(beginMinutes,i));
            siteTime.put("period",simplePrintPeriod(i));
            siteTime.put("bookable",bookableList[i]);
            siteTime.put("periodId",i);
            siteTimes.add(siteTime);
        }
        ret.put("siteTimes",siteTimes);
        ret.put("bookDate",date);
        return ret;
    }

    // ����ҪԤ���ĳ���ID�����ڷ���һ������ֵ�б��ʾ��ʱ���Ƿ��ԤԼ
    public int[] getReservationsBySiteIdAndDate(int siteId,Date date){
        List<Reservation> reservations=reservationDao.selectBySiteIdAndDate(siteId,date);
        Site site=siteDao.selectByPrimaryKey(siteId);
        Venue venue=venueDao.selectByPrimaryKey(site.getVenueId());
//        int minutes=(int)((venue.getEndTime().getTime()-venue.getBeginTime().getTime())/60000); // ���ݿ��ݵķ�����

        int beginMinutes=(int)((((venue.getBeginTime().getTime())/60000)%1440));
        int endMinutes=(int)((((venue.getEndTime().getTime())/60000)%1440));
        int beginId=beginMinutes/ReservationPeriod;
        int endId=endMinutes/ReservationPeriod;

        int[] bookableList=new int[48];
        for(int i=0;i<bookableList.length;++i){
            bookableList[i]=-1;
        }
        for(int i=beginId;i<endId;++i){
            bookableList[i]=0;
        }
        for(Reservation r:reservations){
            for(int i=r.getBeginTime();i<r.getEndTime();++i){
                bookableList[i]=1; // ��Ǵ�ʱ���ѱ�ԤԼ
            }
        }
        return bookableList;

//        List<Boolean> timeList=new ArrayList<>(); //��Ǹ�ʱ���Ƿ�ԤԼ
//        for(int i=0;i<minutes/ReservationPeriod;++i)
//            timeList.add(true);

//        for(Reservation r:reservations){
//            for(int i=r.getBeginTime();i<r.getEndTime();++i){
//                timeList.set(i,false); // ��Ǵ�ʱ���ѱ�ԤԼ
//            }
//        }
//        return timeList;
    }

    public Float calculatePrice(int siteId,int periodNum){
        return siteDao.selectByPrimaryKey(siteId).getPrice()*periodNum/2;
    }

    public int addReservation(Reservation reservation){
        return reservationDao.insertSelective(reservation);
    }

    public ReservationDetail getLatestReservation(int userId){
        ReservationDetail reservationDetail=new ReservationDetail();
        Reservation latestReservation=reservationDao.selectLatestReservationByUserId(userId);
        SimpleDateFormat dateTimeFormatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8")); //mysqlʱ��������δ�����ֻ�ܽ����ڰ���GMT+8ʱ������
        SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysqlʱ��������δ�����ֻ�ܽ����ڰ���GMT+0ʱ������

        Site site=siteDao.selectByPrimaryKey(latestReservation.getSiteId());
        Venue venue=venueDao.selectByPrimaryKey(site.getVenueId());

        reservationDetail.setVenueName(venue.getName());
        reservationDetail.setSiteName(site.getName());
        reservationDetail.setSiteImage(urlPrefix+site.getImage());
        reservationDetail.setBookTime(dateTimeFormatter.format(latestReservation.getBookTime()));
        reservationDetail.setCost(latestReservation.getCost());
        reservationDetail.setReserveDate(dateFormatter.format(latestReservation.getDate()));
        reservationDetail.setState(ReservationState.states.get(latestReservation.getSiteId()));

//        int beginMinutes=(int)((((venue.getBeginTime().getTime())/60000)%1440));
//        reservationDetail.setBeginTime(printTime(beginMinutes,latestReservation.getBeginTime()));
//        reservationDetail.setEndTime(printTime(beginMinutes,latestReservation.getEndTime()));
        reservationDetail.setBeginTime(simplePrintPeriod(latestReservation.getBeginTime()));
        reservationDetail.setEndTime(simplePrintPeriod(latestReservation.getEndTime()+1));

        return reservationDetail;
        //return reservationDao.selectLatestReservationByUserId(userId);
    }
//    public Reservation getReservationByUserIdAndBookTime(int userId,Date date){
//        return reservationDao.selectByUserIdAndBookTime(userId,date);
//    }

    private String printPeriod(int beginMinutes,int periodNum){ // �����ʱ��ε�Ԫ30����ʱ
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

    private String printTime(int beginMinutes,int periodNum){ // �����ʱ��ε�Ԫ30����ʱ
        int totalMins=beginMinutes+periodNum*ReservationPeriod;
        int hour=totalMins/60;
        int min=totalMins%60;
        if(min==30)
            return hour+":30";
        else
            return hour+":00";
//        int endMins=totalMins+30;
//        int endHour=endMins/60;
//        int endMin=endMins%60;
//        return hour+":"+min+"~"+endHour+":"+endMin;
    }

    private String simplePrintPeriod(int periodId){
        if(periodId%2==0){
            if(periodId/2<10)
                return "0"+(periodId/2)+":00";
            else
                return (periodId/2)+":00";
        }else{
            if(periodId/2<10)
                return "0"+(periodId/2)+":30";
            else
                return (periodId/2)+":30";
        }
    }

//    class ReservationComparator implements Comparator<Reservation>{
//
//        @Override
//        public int compare(Reservation r1, Reservation r2) {
//            return r1.getBeginTime()-r2.getBeginTime();
//        }
//    }
}


