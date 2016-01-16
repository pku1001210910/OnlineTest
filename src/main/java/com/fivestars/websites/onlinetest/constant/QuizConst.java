package com.fivestars.websites.onlinetest.constant;

public class QuizConst {

	// quiz subject type 
	public static final int TYPE_SINGLE_CHOICE = 0;
	public static final int TYPE_MULTIPLE_CHOICE = 1;
	public static final int TYPE_COMPLETION = 3;
	public static final int TYPE_ANSWER = 4;
	
	// quiz status
	public static final int STATUS_TEMPORARY_SAVE = 0;
	public static final int STATUS_SUBMITTED = 1;
	
	// quiz need charge
	public static final byte NOT_NEED_CHARGE = 0;
	public static final byte NEED_CHARGE = 1;
	
	// quiz repeatable
	public static final byte REPEATABLE_FALSE = 0;
	public static final byte REPEATABLE_TRUE = 1;
}
