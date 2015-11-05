package movie.db.client;

import java.util.Map;

import movie.db.shared.DataResultShared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")

public interface MyService extends RemoteService {
	//public String myMethod(String s);
	public Map<Integer, DataResultShared> getFilteredData();
}
