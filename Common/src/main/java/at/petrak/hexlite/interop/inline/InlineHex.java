package at.petrak.hexlite.interop.inline;

import com.samsthenerd.inline.api.InlineAPI;

public class InlineHex {
    public static void init(){
        InlineAPI.INSTANCE.addDataType(InlinePatternData.InlinePatternDataType.INSTANCE);
    }
}
