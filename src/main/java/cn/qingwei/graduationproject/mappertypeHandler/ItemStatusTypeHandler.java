package cn.qingwei.graduationproject.mappertypeHandler;

import cn.qingwei.graduationproject.pojo.Item;
import cn.qingwei.graduationproject.pojo.ItemStatus;
import cn.qingwei.graduationproject.pojo.OrderStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//声明jdbcType 为
@MappedJdbcTypes(JdbcType.INTEGER)
//声明JavaType为SexEnum
@MappedTypes(value = ItemStatus.class)
public class ItemStatusTypeHandler extends BaseTypeHandler<ItemStatus> {
    //    设置非空性别参数
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ItemStatus status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,status.getId());
    }


    //    通过列名读取性别
    @Override
    public ItemStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int  status=resultSet.getInt(s);
        if(status!=0&&status!=1&&status!=2)
            return  null;
        return ItemStatus.getEnumByid(status);
    }
    //    通过下标读取性别
    @Override
    public ItemStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int status=resultSet.getInt(i);
        if(status!=0&&status!=1&&status!=2)
            return  null;
        return ItemStatus.getEnumByid(status);
    }
    //    通过存储过程读取性别
    @Override
    public ItemStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int status=callableStatement.getInt(i);
        if(status!=0&&status!=1&&status!=2)
            return  null;
        return ItemStatus.getEnumByid(status);
    }
}
