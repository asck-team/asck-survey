package org.asck.api.service.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Event {

	@JsonCreator
	public static Event create(@JsonProperty(required = false, value = "id") Long id, @JsonProperty("name") String name, @JsonProperty("ownedBy") Long ownedBy) {
		return builder().id(id).name(name).ownedBy(ownedBy).build();
	}
	
	private Long id;
	
	@NotNull
	private String name;

	@NotNull
	private Long ownedBy;
	
	@JsonIgnore
	private List<Question> questions;
	
}
