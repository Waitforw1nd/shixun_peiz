package com.realestate.dao;

import com.realestate.domain.LayoutAnalysis;
import java.util.List;

public interface LayoutAnalysisDao {
    List<LayoutAnalysis> findAll();
    List<LayoutAnalysis> findByLayout(String layout);
}