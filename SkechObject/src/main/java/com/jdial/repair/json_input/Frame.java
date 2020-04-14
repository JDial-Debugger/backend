package json_input;

import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import constants.Json;
public class Frame {

	@SerializedName(value = "func_name")
	private String name;
	@SerializedName(value = "encoded_locals")
	@JsonAdapter(EncodedLocalsDeserializer.class)
	private Map<String, Integer> encodedLocals;
	@SerializedName(value = "ordered_varnames")
	@JsonAdapter(OrderedVarnamesDeserializer.class)
	private List<String> orderedVarnames;
	@SerializedName(value = "is_highlighted")
	private boolean highlighted;
	@SerializedName(value = "is_zombie")
	private boolean zombie;
	@SerializedName(value = "frame_id")
	private Integer id;

	public Frame() {}
	public Frame(String name,
					Map<String, Integer> encodedLocals,
					List<String> orderedVarnames,
					boolean highlighted,
					boolean zombie,
					Integer id) {
		this.name = name;
		this.encodedLocals = encodedLocals;
		this.orderedVarnames = orderedVarnames;
		this.highlighted = highlighted;
		this.zombie = zombie;
		this.id = id;
	}
	
	public Frame(Frame frame) {
		this.highlighted = frame.isHighlighted();
		this.zombie = frame.isZombie();
		this.id = frame.getId();
		this.name = frame.getName();
		this.encodedLocals = new HashMap<String, Integer>(frame.getEncodedLocals());
		this.orderedVarnames = new ArrayList<String>(frame.getOrderedVarnames());
	}
	
	@Override
	public String toString(){
		return name + "\n" + id+"\n"+encodedLocals.toString();
	}
	
	public String getName() { return this.name; }
	public Map<String, Integer> getEncodedLocals() { return this.encodedLocals; }
	public List<String> getOrderedVarnames() { return this.orderedVarnames; }
	public boolean isHighlighted() { return this.highlighted; }
	public boolean isZombie() { return this.zombie; }
	public Integer getId() { return this.id; }
	public List<String> getOrderedLocals() { return this.orderedVarnames; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encodedLocals == null) ? 0 : encodedLocals.hashCode());
		result = prime * result + (highlighted ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderedVarnames == null) ? 0 : orderedVarnames.hashCode());
		result = prime * result + (zombie ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Frame other = (Frame) obj;
		if (encodedLocals == null) {
			if (other.encodedLocals != null)
				return false;
		} else if (!encodedLocals.equals(other.encodedLocals))
			return false;
		if (highlighted != other.highlighted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderedVarnames == null) {
			if (other.orderedVarnames != null)
				return false;
		} else if (!orderedVarnames.equals(other.orderedVarnames))
			return false;
		if (zombie != other.zombie)
			return false;
		return true;
	}

	private class EncodedLocalsDeserializer implements JsonDeserializer<Map<String, Integer>> {
		@Override
		public Map<String, Integer> deserialize(JsonElement json, 
												Type typeOfT, 
												JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			Map<String, Integer> encodedLocals = new HashMap<String, Integer>();
			jsonObject.entrySet().forEach(entry -> {
				if (entry.getKey().equals(Json.RETURN_VAR_NAME)) {
					encodedLocals.put(entry.getKey(), null);
				} else {
					encodedLocals.put(entry.getKey(), entry.getValue().getAsInt());
				}
			});
			return encodedLocals;
		}
	}
	private class OrderedVarnamesDeserializer implements JsonDeserializer<List<String>> {
		@Override
		public List<String> deserialize(JsonElement json, 
												Type typeOfT, 
												JsonDeserializationContext context)
				throws JsonParseException {
			JsonArray jsonArray = json.getAsJsonArray();
			List<String> orderedVarnames = new ArrayList<String>();
			jsonArray.forEach(element -> {
				orderedVarnames.add(element.getAsString());
			});
			return orderedVarnames;
		}
	}
}
