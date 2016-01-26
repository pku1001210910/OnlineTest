package com.fivestars.websites.onlinetest.util;

import java.util.Comparator;

import com.fivestars.websites.onlinetest.model.QuizSubject;
import com.fivestars.websites.onlinetest.model.SubjectItem;

public class QuizSubjectComparator implements Comparator<QuizSubject> {

    public int compare(QuizSubject qs1, QuizSubject qs2) {
        
        return qs1.getSubjectOrder() - qs2.getSubjectOrder();
    }

}