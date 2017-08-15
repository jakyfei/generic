package com.topsec.tsm.generic.core;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

/**
 * 分页信息封装类
 * Created by yue_tf on 2017/3/26
 */
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int pageNo = 1;

    protected int pageSize = -1;

    protected int firstNum;

    protected int lastNum;

    protected int totalPage = -1;

    protected int total = -1;

    protected boolean haveNext = false;
    protected boolean havePrevious = false;
    protected boolean haveFirstPage = false;
    protected boolean haveLastPage = false;

    protected int nextPageNo;
    protected int previousPageNo;

    public PageInfo() {

    }

    public PageInfo(int pageNo, int pageSize) {
        this.pageNo = (pageNo == 0 ? 1 : pageNo);
        this.pageSize = pageSize;
    }

    public int getFirstNum() {
        return firstNum;
    }

    public int getLastNum() {
        return lastNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        Validate.isTrue(pageNo > 0, "pageNo must be more than 0");
        this.pageNo = pageNo;
        if (totalPage != -1) {
            nextPageNo = (pageNo < totalPage ? pageNo + 1 : totalPage);
            previousPageNo = (pageNo == 1 ? 1 : pageNo - 1);
            haveNext = (pageNo < totalPage);
            havePrevious = (pageNo > 1);
            haveFirstPage = (pageNo > 1);
            haveLastPage = (pageNo < totalPage);
            if ((pageSize != -1) && (totalPage >= pageNo)) {
                firstNum = (total == 0 ? 0 : (pageNo - 1) * pageSize + 1);
                lastNum = (pageNo < totalPage ? pageNo * pageSize : total);
            }
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        Validate.isTrue(pageSize > 0, "pageSize must be more than 0");
        this.pageSize = pageSize;
        if (total != -1) {
            if (total % pageSize == 0) {
                totalPage = (total / pageSize);
            } else {
                totalPage = (total / pageSize + 1);
            }
            if (totalPage == 0) {
                totalPage = 1;
            }
            if ((pageNo != -1) && (total > 0)) {
                firstNum = ((pageNo - 1) * pageSize + 1);
                lastNum = (pageNo < totalPage ? pageNo * pageSize : total);
            }
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        Validate.isTrue(total > -1, "total must be more than -1");
        this.total = total;
        if (pageSize > 0) {
            if (total % pageSize == 0) {
                totalPage = (total / pageSize);
            } else {
                totalPage = (total / pageSize + 1);
            }
            if (totalPage == 0) {
                totalPage = 1;
            }
            if ((pageNo != -1) && (total > 0)) {
                firstNum = ((pageNo - 1) * pageSize + 1);
                lastNum = (pageNo < totalPage ? pageNo * pageSize : total);
            }
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean getHaveFirstPage() {
        return haveFirstPage;
    }

    public boolean getHaveLastPage() {
        return haveLastPage;
    }

    public boolean getHaveNext() {
        return haveNext;
    }

    public boolean getHavePrevious() {
        return havePrevious;
    }

    public int getNextPageNo() {
        return nextPageNo;
    }

    public int getPreviousPageNo() {
        return previousPageNo;
    }

    public void upPage() {
        Validate.isTrue(pageNo > 1, "Can not up the page, current page number is " + pageNo + ", and totalPage is" + totalPage);
        if (totalPage != -1) {
            pageNo = (totalPage < pageNo ? totalPage : pageNo - 1);
            firstNum = (total == 0 ? 0 : (pageNo - 1) * pageSize + 1);
            lastNum = (pageNo < totalPage ? pageNo * pageSize : total);
            haveNext = (pageNo < totalPage);
            havePrevious = (pageNo > 1);
            haveFirstPage = (pageNo > 1);
            haveLastPage = (pageNo < totalPage);
            nextPageNo = (pageNo < totalPage ? pageNo + 1 : totalPage);
            previousPageNo = (pageNo == 1 ? 1 : pageNo - 1);
        }
    }

    public void downPage() {
        Validate.isTrue((pageNo > 0) && (pageNo < totalPage), "Can not down the page, current page number is " + pageNo
                + ", and totalPage is" + totalPage);
        if (totalPage != -1) {
            pageNo += 1;
            firstNum = (total == 0 ? 0 : (pageNo - 1) * pageSize + 1);
            lastNum = (pageNo < totalPage ? pageNo * pageSize : total);
            haveNext = (pageNo < totalPage);
            havePrevious = (pageNo > 1);
            haveFirstPage = (pageNo > 1);
            haveLastPage = (pageNo < totalPage);
            nextPageNo = (pageNo < totalPage ? pageNo + 1 : totalPage);
            previousPageNo = (pageNo == 1 ? 1 : pageNo - 1);
        }
    }
}
