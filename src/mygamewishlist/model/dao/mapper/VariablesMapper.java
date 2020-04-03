package mygamewishlist.model.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface VariablesMapper {

	public String getVariableByName(@Param("name") String name);
}
