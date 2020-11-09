package me.shy.demo.dto;

import java.util.ArrayList;
import java.util.List;
import me.shy.demo.domain.User;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 分页信息封装，用于Servlet到JSP的数据传递
 */
public class PageInfo {
    private Long totalPage;
    private int pageNumber;
    private int pageSize;
    private List<?> currentUsers = new ArrayList<>();

    public PageInfo(Long totalPage, int pageNumber, int pageSize, List<User> currentUsers) {
        this.totalPage = totalPage;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.currentUsers = currentUsers;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(List<?> currentUsers) {
        this.currentUsers = currentUsers;
    }
}
