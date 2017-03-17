package hbec.app.hospital.db;

public interface IRequestContext {
	void printDbCalls(boolean enabled);
	void printDbCallsAndResults(boolean enabled);
	void classNameAndMethodName(String cn,String mn);
}
