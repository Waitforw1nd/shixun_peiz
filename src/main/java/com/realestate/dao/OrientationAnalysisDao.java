package com.realestate.dao;

import com.realestate.domain.OrientationAnalysis;
import java.util.List;

public interface OrientationAnalysisDao {
    List<OrientationAnalysis> findAll();
    List<OrientationAnalysis> findByOrientation(String orientation);
}