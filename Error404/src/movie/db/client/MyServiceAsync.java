package movie.db.client;

import java.util.ArrayList;

import java.util.Map;

import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Selection;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous service interface for getting the data from 
 * the server to the client asynchronous.
 * 
 * @Author Christoph Weber
 */
public interface MyServiceAsync {
	public void getFilteredData(Selection selection, AsyncCallback<Map<Integer, DataResultShared>> callback);

	public void getWorldMapData(int selectedYear, AsyncCallback<ArrayList<DataResultAggregated>> asyncCallback);
	
	public void getColumnEntries(String column, AsyncCallback<ArrayList<String>> callback);
	
	public void getBarChartData(String country, String genre, int yearFrom, int yearTo, AsyncCallback<Map<Integer,Integer>> callback);
}

