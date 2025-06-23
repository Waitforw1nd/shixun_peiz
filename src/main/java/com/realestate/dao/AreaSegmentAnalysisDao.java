package com.realestate.dao;

import com.realestate.domain.AreaSegmentAnalysis;
import java.util.List;

public interface AreaSegmentAnalysisDao {
    List<AreaSegmentAnalysis> findAll();
    List<AreaSegmentAnalysis> findByAreaSegment(String areaSegment);
}