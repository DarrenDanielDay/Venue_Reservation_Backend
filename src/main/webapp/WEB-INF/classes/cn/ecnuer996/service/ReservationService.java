package cn.ecnuer996.service;

import cn.ecnuer996.bean.Reservation;
import cn.ecnuer996.dao.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("ReservationService")
public class ReservationService {

    @Autowired
    private ReservationMapper reservationDao;

    public List<Reservation> getReservationByUserId(int id) {
        return reservationDao.selectByUserId(id);
    }

}
