package cn.ecnuer996.controller;

import cn.ecnuer996.bean.Site;
import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.service.VenueService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
public class ReservationController {

    @Autowired
    private VenueService venueService;

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

}
