package com.realestate.dao;

import com.realestate.domain.CommunityRanking;
import java.util.List;

public interface CommunityRankingDao {
    List<CommunityRanking> findAll();
    List<CommunityRanking> findByDistrict(String district);
    List<CommunityRanking> findTop10ByAvgPrice();
}