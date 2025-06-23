package com.realestate.dao;

import com.realestate.domain.DecorationAnalysis;
import java.util.List;

public interface DecorationAnalysisDao {
    List<DecorationAnalysis> findAll();
    List<DecorationAnalysis> findByDecoration(String decoration);
}