package com.czertainly.api.model.core.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchFieldDataDto {
    @Schema(description = "Field to search",
            required = true)
    private SearchableFields field;

    @Schema(description = "Label for the field",
            required = true)
    private String label;

    @Schema(description = "Type of the field",
            required = true)
    private SearchableFieldType type;

    @Schema(description = "List of available conditions for the field",
            required = true)
    private List<SearchCondition> conditions;

    @Schema(description = "Available values for the field")
    private Object value;

    @Schema(description = "Multivalue flag. true = yes, false = no")
    private Boolean multiValue;

    public SearchableFields getField() {
        return field;
    }

    public void setField(SearchableFields field) {
        this.field = field;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SearchableFieldType getType() {
        return type;
    }

    public void setType(SearchableFieldType type) {
        this.type = type;
    }

    public List<SearchCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<SearchCondition> conditions) {
        this.conditions = conditions;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }
}
