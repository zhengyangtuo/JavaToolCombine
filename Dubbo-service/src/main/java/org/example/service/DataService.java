package org.example.service;

import java.util.Date;

public interface DataService {
    public long caculateUv(Date start, Date end);
    public long caculateDau(Date start,Date end);
    public void recordUv(String ip);
    public void recordDau(int userId);
    public void recordUv(Date date,String ip);
    public void recordDau(Date date,int userId);
}
