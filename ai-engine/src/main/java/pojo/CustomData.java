/**
 * 
 */
package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author priyank
 *
 */
@Entity
@Table(name="custom_data")
public class CustomData extends BaseData {

	@Column(unique = true)
	String key;
	
	String value;
	
	String data;


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}	
	
	
}
