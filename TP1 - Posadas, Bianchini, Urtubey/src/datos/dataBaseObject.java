package datos;

import java.util.ArrayList;
import java.util.HashMap;


public class dataBaseObject {
	private ArrayList<HashMap<String, String>> maplist;
	
	public dataBaseObject(){
		this.maplist = new ArrayList<>();
	}
	
	public boolean addData(HashMap<String, String> data){
		return this.maplist.add(data);
	}
	
	public HashMap<String, String> getRowData(int index){
		return this.maplist.get(index);
	}
	
	public ArrayList<HashMap<String, String>> getObject(){
		return this.maplist;
	}

	@Override
	public String toString() {
		return "dataBaseObject [maplist=" + maplist + "]";
	}

}
