package com.recipemanager.common;


public class baseContext {
    private static ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();


    public static void setUserID(Long id){
        userThreadLocal.set(id);
    }

    public static Long getUserID(){
        return userThreadLocal.get();
    }

}
