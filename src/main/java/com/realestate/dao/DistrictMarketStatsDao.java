package com.realestate.dao;

import com.realestate.domain.DistrictMarketStats;
import java.util.List;

public interface DistrictMarketStatsDao {
    List<DistrictMarketStats> findAll();
    List<DistrictMarketStats> findByDistrict(String district);
}