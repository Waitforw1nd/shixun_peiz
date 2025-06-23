package com.realestate.dao;

import com.realestate.domain.DistrictAreaAnalysis;
import java.util.List;

public interface DistrictAreaAnalysisDao {
    List<DistrictAreaAnalysis> findAll();
    List<DistrictAreaAnalysis> findByDistrict(String district);
}