package DataAccessObject;

import java.util.ArrayList;

public interface DAO_Interface<T> {
    public int insert(T obj);
    public int update(T obj);
    public int delete(T obj);
    public ArrayList<T> selectAll();
    public T selectTopPLayer();
    public ArrayList<T> selectByCondition(String condition);
    public T selectByName(T obj);
}
