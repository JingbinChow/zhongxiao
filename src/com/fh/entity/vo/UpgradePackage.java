package com.fh.entity.vo;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class UpgradePackage {
    private int step_value;
    private String step_name;

    public UpgradePackage() {
    }

    public UpgradePackage(int step_value, String step_name) {
        this.step_value = step_value;
        this.step_name = step_name;
    }

    public int getStep_value() {
        return step_value;
    }

    public void setStep_value(int step_value) {
        this.step_value = step_value;
    }

    public String getStep_name() {
        return step_name;
    }

    public void setStep_name(String step_name) {
        this.step_name = step_name;
    }

    @Override
    public String toString() {
        return "UpgradePackage{" +
                "step_value=" + step_value +
                ", step_name='" + step_name + '\'' +
                '}';
    }
}
