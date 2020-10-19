package vn.aptech.project4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="size")
public class Size {
	@Id
	@Column(name="Id")
	private int id;
	@Column(name="name")
	private String name;

	public Size() {
	}
	public Size(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
