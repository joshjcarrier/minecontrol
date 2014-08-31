package com.joshjcarrier.minecontrol.framework.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AutomationBinding {
    private final int keyEventId;

    public AutomationBinding(int keyEventId) {
        this.keyEventId = keyEventId;
    }

    public String getName() {
        switch(this.keyEventId)
        {
            case MouseEvent.BUTTON1_MASK:
                return "Mouse left click (button 1)";
            case MouseEvent.BUTTON2_MASK:
                return "Mouse middle click (button 2)";
            case MouseEvent.BUTTON3_MASK:
                return "Mouse right click (button 3)";
            case MouseEvent.MOUSE_WHEEL:
                return "Mouse scroll";
            default:
                return KeyEvent.getKeyText(this.keyEventId);
        }
    }

    @Override
    public String toString() {
        return "  " + getName();
    }
}
