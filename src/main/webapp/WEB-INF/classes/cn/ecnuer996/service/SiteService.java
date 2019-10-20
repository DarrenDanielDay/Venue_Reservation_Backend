package cn.ecnuer996.service;

import cn.ecnuer996.bean.Site;
import cn.ecnuer996.dao.SiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SiteService")
public class SiteService {
    @Autowired
    private SiteMapper siteDao;

    public Site getSiteById(int id) {
        return siteDao.selectByPrimaryKey(id);
    }
}
