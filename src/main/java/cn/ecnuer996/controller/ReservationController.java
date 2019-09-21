package cn.ecnuer996.controller;

import cn.ecnuer996.bean.Venue;
import cn.ecnuer996.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReservationController {

    @Autowired
    private VenueService venueService;

    @RequestMapping(value="/venue")
    public Venue getVenue(HttpServletRequest request){
        int venue_id=Integer.parseInt(request.getParameter("id"));
        return venueService.getVenueById(venue_id);
    }
}
