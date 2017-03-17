package hbec.app.hospital.db;

import hbec.platform.commons.web.WrappedSession;

public class RequestContext implements IRequestContext {
	public boolean printDbCalls;
	public boolean printDbCallsAndResults;
	public String browserId;
	public String invokeClassFullName;
	public String invokeMethodName;
	public String clientIp;
	public WrappedSession session;
	@Override
	public void printDbCalls(boolean enabled) {
		printDbCalls = enabled;

	}

	@Override
	public void printDbCallsAndResults(boolean enabled) {
		printDbCallsAndResults = enabled;
	}

	@Override
	public void classNameAndMethodName(String cn, String mn) {
		invokeClassFullName = cn;
		invokeMethodName = mn;
		
	}

}
