package org.transformice.modules.lua.debug;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public final class LuaGetCurrentLuaThreadName extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        return LuaValue.valueOf("Main");
    }
}