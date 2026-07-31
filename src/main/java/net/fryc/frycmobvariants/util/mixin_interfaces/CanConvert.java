package net.fryc.frycmobvariants.util.mixin_interfaces;

public interface CanConvert {

    void setCanConvertToTrue();
    void setCanConvertToFalse();
    void initMobEquipment();
    void setNextTickUpdate(Runnable nextTickUpdate);
}
