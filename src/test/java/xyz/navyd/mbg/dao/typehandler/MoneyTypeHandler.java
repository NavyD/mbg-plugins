package xyz.navyd.mbg.dao.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 在 Money 与 Long 之间转换的 TypeHandler，处理 CNY 人民币
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {
    public static final byte PRECISE_BITS = 2;
    public static final CurrencyUnit DEFAULT_CURRENCY_UNIT = CurrencyUnit.of("CNY");

    private final double base = Math.pow(10, PRECISE_BITS);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getAmountMinorLong());
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : parseMoney(rs.getLong(columnName));
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : parseMoney(rs.getLong(columnIndex));
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : parseMoney(cs.getLong(columnIndex));
    }

    private Money parseMoney(Long value) {
        return Money.of(DEFAULT_CURRENCY_UNIT, value / base);
    }
}