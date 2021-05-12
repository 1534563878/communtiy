package com.zyq.community.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * @author zyq
 * @description   分页功能, 就是下面那个第一页第二页那种
 * @create 2021/5/10
 **/
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrev;
    private boolean showFirst;
    private boolean showNext;
    private boolean showEnd;
    private Integer page;
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private List<Integer> pages =new ArrayList<>();

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowFirst() {
        return showFirst;
    }

    public void setShowFirst(boolean showFirst) {
        this.showFirst = showFirst;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEnd() {
        return showEnd;
    }

    public void setShowEnd(boolean showEnd) {
        this.showEnd = showEnd;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }


    public void setPagination(Integer count, Integer page, Integer size) {
        //有多少页
        if (count % size==0) {
            totalPage=count/size;
        }else {
            totalPage = count/size +1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        //page 是页数
        //count 是多少条数据
        this.page= page;
        pages.add(page);
        for (int i = 1; i < 3; i++) {
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //第一页的时候没有上一页
        if (page==1){
            showPrev=false;
        }else {
            showPrev=true;
        }
        //最后一页没有下一页
        if (page.equals(totalPage)){
            showNext=false;
        }else {
            showNext=true;
        }
        //第五页的时候有跳到第一页
        if (pages.contains(1)){
            showFirst=false;
        }else {
            showFirst=true;
        }

        if (pages.contains(totalPage)){
            showEnd=false;
        }else {
            showEnd=true;
        }
    }
}
