package cn.ecnuer996.controller;

import cn.ecnuer996.bean.Reservation;
import cn.ecnuer996.bean.Site;
import cn.ecnuer996.bean.User;
import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.service.ReservationService;
import cn.ecnuer996.service.UserService;
import cn.ecnuer996.service.VenueService;
import cn.ecnuer996.transfer.ReservationDetail;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
public class ReservationController {

    @Autowired
    private UserService userService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value="/venue")
    public ArrayList<Venue> getVenues(HttpServletRequest request){
        String venue_name = request.getParameter("name");
        return venueService.getVenueByName("%" + venue_name + "%");
    }

    @RequestMapping(value="/site")
    public ArrayList<Site> getSites(HttpServletRequest request){
        int venue_id = Integer.parseInt(request.getParameter("venue_id"));
        return venueService.getSiteByVenueId(venue_id);
    }

    @RequestMapping(value="all-venues")
    public JSONObject getAllVenues(){
        return venueService.getAllVenues();
    }

    @RequestMapping(value="venue-detail")
    public JSONObject getVenueDetail(HttpServletRequest request){
        int venue_id = Integer.parseInt(request.getParameter("venue_id"));
        return venueService.getVenueDetail(venue_id);
    }

    @RequestMapping(value="/site-time-list")
    public JSONObject getSiteTimeList(HttpServletRequest request){
        int siteId=Integer.parseInt(request.getParameter("site_id"));
        String date=request.getParameter("book_date");
        JSONObject response=new JSONObject();
        try{
            response.put("date",venueService.getSiteTimes(siteId,date));
            response.put("code",200);
            response.put("message","成功返回此场地预约时段信息");
        }catch(ParseException parseException){
            response.put("code",500);
            response.put("message","日期参数格式不正确");
        }
        return response;
    }

    @RequestMapping(value="/reserve",method= RequestMethod.POST)
    public JSONObject generateReservation(@RequestBody JSONObject postBody){
        int siteId=postBody.getInteger("siteId");
        int userId=postBody.getInteger("userId");
        String date=postBody.getString("bookDate");
        int beginPeriod=postBody.getInteger("beginPeriod");
        int endPeriod=postBody.getInteger("endPeriod")+1; // 数据库中的endPeriod是不算在预定时段里的，所以要加一
        JSONObject response=new JSONObject();
        // 验证想预约的时段是否已被预约
        try{
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+0")); //mysql时区问题尚未解决，只能将日期按照GMT+0时区解析
            Date realDate=formatter.parse(date);
            List<Boolean> bookList=venueService.getReservationsBySiteIdAndDate(siteId,realDate);
            for(int i=beginPeriod;i<endPeriod;++i){
                if(!bookList.get(i)){ //时段已被预约
                    response.put("code",500);
                    response.put("message","您所选的时段已被预约，请重新选择预约时间！");
                    return response;
                }
            }
            Date bookTime=new Date();
            Reservation reservation=new Reservation();
            reservation.setSiteId(siteId);
            reservation.setUserId(userId);
            reservation.setBookTime(bookTime);
            reservation.setDate(realDate);
            reservation.setCost(venueService.calculatePrice(siteId,endPeriod-beginPeriod));
            reservation.setBeginTime(beginPeriod);
            reservation.setEndTime(endPeriod);
            reservation.setState(1);
            venueService.addReservation(reservation);
            response.put("code","200");
            response.put("message","预定成功！");
            ReservationDetail reservationDetail=venueService.getLatestReservation(userId);
            JSONObject reservationDetailJson=new JSONObject();
            reservationDetailJson.put("reservationDetail",reservationDetail);
            response.put("data",reservationDetailJson);
            System.out.println(bookTime.getTime());
        }catch(ParseException pe){
            ;
        }
        return response;
    }

    @RequestMapping(value="/orders",method=RequestMethod.POST)
    public JSONObject searchOrders(@RequestBody JSONObject postBody) {
        JSONObject response = new JSONObject();
        int userId = postBody.getInteger("id");
        User user = userService.getUserById(userId);
        if(user.getId() < 0){
            response.put("code",250);
            response.put("results","illegal userId");
            response.put("message","你传了个假用户,拒绝");
        }
        else{
            List<Reservation> reservations = reservationService.getReservationByUserId(userId);
            response.put("code",200);
            response.put("result",reservations);
            response.put("messages","查询成功");
        }
        return response;
    }

}
