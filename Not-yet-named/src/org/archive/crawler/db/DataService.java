package org.archive.crawler.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

import org.archive.crawler.util.Toolkit;

public class DataService {
    private static Dao<DataTable, String> data = null;

    private DataService() {
    }

    public static Dao<DataTable, String> getTable() throws SQLException {

        if (data == null) {
            data = DaoManager.createDao(OrmService.getConnectionSource(), DataTable.class);
        }
        return data;
    }

    /**
     * 插入Data数据表一行记录
     *
     * @param item
     * @return
     */
    public static boolean addOne(DataTable item) {
        boolean result = false;
        try {
            getTable().create(item);
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isUrlExist(String url) throws SQLException {
        return getTable().queryBuilder().where().
                eq("signature", Toolkit.md5Encode(url)).countOf() > 0;
    }

}
