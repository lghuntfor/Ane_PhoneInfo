package com.xkw.ane.phoneinfo;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * FREContext在FREExtension中的初始化
 * 接口类和AIR程序对接<BR>
 * @author anonymous
 */

public class PhoneExtension implements FREExtension{

	@Override
	public FREContext createContext(String arg0) {
		//FREContext在FREExtension中的初始化
		return new PhoneContext();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void initialize() {
	}
}
