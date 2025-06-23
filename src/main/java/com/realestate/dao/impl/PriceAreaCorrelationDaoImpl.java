package com.realestate.dao.impl;

import com.realestate.dao.PriceAreaCorrelationDao;
import com.realestate.domain.PriceAreaCorrelation;
import com.realestate.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceAreaCorrelationDaoImpl implements PriceAreaCorrelationDao {
    @Override
    public PriceAreaCorrelation getCorrelation() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PriceAreaCorrelation correlation = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM price_area_correlation LIMIT 1";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                correlation = new PriceAreaCorrelation();
                correlation.setDistrict(rs.getString("district"));
                correlation.setCount(rs.getInt("count"));
                correlation.setPriceAreaCorr(rs.getDouble("price_area_corr"));
                correlation.setUnitPriceAreaCorr(rs.getDouble("unit_price_area_corr"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return correlation;
    }
}