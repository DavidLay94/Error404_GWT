package movie.db.client;

import java.util.ArrayList;
import java.util.Map;

import movie.db.shared.DataResultShared;
import movie.db.shared.Selection;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyServiceAsync {
	//public void myMethod(String s, AsyncCallback<String> callback);
	public void getFilteredData(Selection selection, AsyncCallback<Map<Integer, DataResultShared>> callback);
}
