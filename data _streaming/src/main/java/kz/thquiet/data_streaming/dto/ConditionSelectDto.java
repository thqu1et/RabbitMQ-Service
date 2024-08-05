package kz.thquiet.data_streaming.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConditionSelectDto {
	private String migrationServiceName;
	private String columnName;
	private List<Object> values;
}
