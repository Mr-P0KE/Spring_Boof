package com.poke.Comon;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * ����ֵ
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * ��ȡֵ
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
