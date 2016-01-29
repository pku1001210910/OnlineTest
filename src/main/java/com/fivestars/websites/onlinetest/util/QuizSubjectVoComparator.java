package com.fivestars.websites.onlinetest.util;

import java.util.Comparator;

import com.fivestars.websites.onlinetest.vo.UserQuizSubjectVo;

public class QuizSubjectVoComparator implements Comparator<UserQuizSubjectVo> {

    public int compare(UserQuizSubjectVo qs1, UserQuizSubjectVo qs2) {
        
        return qs1.getQuizSubject().getSubjectOrder() - qs2.getQuizSubject().getSubjectOrder();
    }

}