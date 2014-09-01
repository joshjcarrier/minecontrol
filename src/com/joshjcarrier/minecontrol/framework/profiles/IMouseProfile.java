package com.joshjcarrier.minecontrol.framework.profiles;

public interface IMouseProfile {
    public int getSensitivityX();

    public int getSensitivityY();

    public boolean isInvertY();

    public void setSensitivityX(int value);

    public void setSensitivityY(int value);

    public void setInvertY(boolean value);
}
