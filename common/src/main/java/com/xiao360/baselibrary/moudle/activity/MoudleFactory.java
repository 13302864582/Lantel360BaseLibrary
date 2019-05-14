package com.xiao360.baselibrary.moudle.activity;

class MoudleFactory {
    public static AbsMoudle newMoudleInstance(String moudleName) {
        if (moudleName == null || moudleName.equals("")) {
            return null;
        }
        try {
            Class<? extends AbsMoudle> moduleClzz = (Class<? extends AbsMoudle>) Class.forName(moudleName);
            if(moduleClzz != null){
                AbsMoudle instance = moduleClzz.newInstance();
                return instance;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
