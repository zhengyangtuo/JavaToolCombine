package org.example.pojo;

import java.io.Serializable;

public class Page implements Serializable {
    private int current = 1;
    private int limit = 10;
    private int rows;
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current > 0) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0 && limit < 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOffset(){
        return current * limit - limit;
    }

    public int getTotal(){
        if (rows % limit == 0){
            return rows / limit;
        }else{
            return rows / limit + 1;
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }

    //分页栏起始页码
    public int getFrom(){
        return current < 3 ? 1 : current - 2;
    }
    //分页栏结束页码
    public int getTo(){
        int total = getTotal();
        return current + 2 > total ? total : current + 2;
    }
}
