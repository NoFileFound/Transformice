package org.luaj.vm2.lib;

import org.luaj.vm2.Varargs;

public class TimeOutDebugLib extends DebugLib {
    private long timeOut = -1L;
    private boolean testCode;

    public void setTimeOut(int time, boolean test) {
        this.timeOut = time == -1 ? -1L : System.currentTimeMillis() + (long)time;
        this.testCode = test;
    }

    public boolean checkTestCode() {
        return this.testCode;
    }

    public void onInstruction(int pc, Varargs v, int top) {
        if (this.timeOut != -1L && System.currentTimeMillis() > this.timeOut) {
            throw new RuntimeException();
        } else {
            super.onInstruction(pc, v, top);
        }
    }
}