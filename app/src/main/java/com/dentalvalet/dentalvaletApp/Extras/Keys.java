package com.dentalvalet.dentalvaletApp.Extras;

/**
 * Created by Awais Mahmood on 21-Nov-15.
 */
public interface Keys {

    public static final String URL_DENTAL_VALET_APP = "http://dentalvalet.com/api/app";
    public static final String URL_DENTIST_PROFILE = "http://dentalvalet.com/Images/Dentist/Profile/";


    public static final String URL_DENTIST_SERVICE = "http://dentalvalet.com/Images/Dentist/Service/";

    public static final String URL_PROMOTION_IMAGES = "http://dentalvalet.com/Images/Dentist/Promotion/";
    public static final String URL_REWARD_IMAGES = "http://dentalvalet.com/Images/Dentist/Rewards/";

    public static final String URL_PATIENT_IMAGES = "http://dentalvalet.com/Images/Patient/Profile/";

    public static final String URL_DENTIST_STAFF = "http://dentalvalet.com/Images/Dentist/Staff/";






    public  static final String URL_CHAR_QUESTION = "?";
    public  static final String URL_CHAR_AMPERSAND = "&";
    public  static final String URL_PARAM_STATUS = "status=";


    public interface Attributes
    {
        public static final String KEY_EMAIL = "email=";
        public static final String KEY_ZIPCODE = "zipcode=";

        public static final String KEY_INSURANCE = "insuranc=";
        public static final String KEY_SPECIALITY = "speciality=";
        public static final String KEY_USERID = "userId=";
        public static final String KEY_ID = "Id=";
        public  static final String KEY_PASSWORD = "password=";
        public  static final String KEY_NAME = "name=";
        public  static final String KEY_GENDER = "gender=";
        public  static final String KEY_DOB = "birthday=";
        public  static final String KEY_FBID = "fbId=";
        public  static final String KEY_PHONE = "phone=";
        public  static final String KEY_TID = "tId=";
        public  static final String KEY_ADDRESS = "address=";
        public  static final String KEY_IMAGE = "image=";

        public static final String KEY_DENTIST_ID = "dentistId=";
        public static final String KEY_PATIENT_ID = "patientId=";

        public static final String KEY_SERVICE_ID = "serviceId=";




    }

    public interface Status
    {
        public static final String LOGIN = "1";
        public  static final String REGISTRATION = "2";
        public  static final int PATIENT_BY_ID = 3;
        public  static final String ADD_WHISHLIST = "4";
        public  static final int HIRE_DENTIST = 5;
        public  static final String GET_DENTIST_BY_ID = "6";
        public  static final int GET_ALL_DENTIST = 7;
        public  static final int PROMOTION_DENTIST_BY_PATIENT_ID =9;
        public  static final int SEARCH_DENTIST = 10;

        public  static final int DENTIST_EDUCATION = 10;
        public  static final int SERVIC_DENTIST_BY_DENTIST_ID = 11;


        public  static final String DENTIST_SEARCH_BY_AREA = "12";
        public  static final int DENTIST_EXPERIENCE = 12;

        public  static final int DENTIST_AVAL_APPOINTMENTS = 16;

        public  static final int ADD_WISHLIST = 21;

        public  static final int TREATMENT_PLAN = 22;


        public  static final int REQUEST_APPOINTMENT = 23;
        public  static final int UPDATE_PATIENT = 26;

        public  static final int REWARD = 27;



        public  static final int TESTIMONIAL = 31;
        public  static final int SPECIALITIES = 32;

        public  static final int INSURANCE =33;
        public  static final int FORGOT_PASSWORD =37;
        public  static final int REMOVE_SERVICE =39;
        public  static final int PATIENT_WISHLIST =40;
        public  static final int DENTIST_STAFF =41;

    }

}
