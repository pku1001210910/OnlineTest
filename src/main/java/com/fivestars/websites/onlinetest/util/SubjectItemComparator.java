package com.fivestars.websites.onlinetest.util;

import java.util.Comparator;

import com.fivestars.websites.onlinetest.model.SubjectItem;

public class SubjectItemComparator implements Comparator<SubjectItem> {

    public int compare(SubjectItem s1, SubjectItem s2) {
        
        return s1.getItemOrder() - s2.getItemOrder();
    }

}