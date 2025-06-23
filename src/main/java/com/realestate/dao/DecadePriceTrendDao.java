package com.realestate.dao;

import com.realestate.domain.DecadePriceTrend;
import java.util.List;

public interface DecadePriceTrendDao {
    List<DecadePriceTrend> findAll();
    List<DecadePriceTrend> findByDecade(String decade);
}