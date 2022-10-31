package com.czertainly.api.model.deprecated.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeCallbackMapping {

    /**
     * Name of the attribute whose value is to be used as value of path variable or request param or body field
     * defined in {@link AttributeCallbackMapping#to}.
     * It is optional and must be set only if {@link AttributeCallbackMapping#value} is not set.
     **/
    @Schema(
            description = "Name of the attribute whose value is to be used as value of " +
                    "path variable or request param or body field." +
                    "It is optional and must be set only if value is not set."
    )
    private String from;

    /**
     * Type of the attribute defined in {@link AttributeCallbackMapping#from}.
     * It is optional and must be set only if special behaviour is needed.
     **/
    @Schema(
            description = "Type of the attribute. " +
                    "It is optional and must be set only if special behaviour is needed."
    )
    private AttributeType attributeType;

    /**
     * Name of the path variable or request param or body field which is to be used to assign value of attribute
     * defined in {@link AttributeCallbackMapping#from}.
     **/
    @Schema(
            description = "Name of the path variable or request param or body field" +
                    " which is to be used to assign value of attribute",
            required = true
    )
    private String to;

    /**
     * Set of {@link AttributeValueTarget}s for propagating {@link AttributeCallbackMapping#value}.
     **/
    @Schema(
            description = "Set of targets for propagating value.",
            required = true
    )
    private Set<AttributeValueTarget> targets;

    /**
     * Static value to be propagated to {@link AttributeCallbackMapping#targets}.
     * It is optional and is set only if the value is known at attribute creation time.
     */
    @Schema(
            description = "Static value to be propagated to targets. " +
                    "It is optional and is set only if the value is known at attribute creation time."
    )
    private Serializable value;

    /**
     *
     * @param from - {@link AttributeCallbackMapping#from}
     * @param to - {@link AttributeCallbackMapping#to}
     * @param target - {@link AttributeValueTarget}
     */
    public AttributeCallbackMapping(String from, String to, AttributeValueTarget target) {
        this.from = from;
        this.to = to;
        this.targets = Set.of(target);
    }

    public AttributeCallbackMapping() {
        // default constructor for Jackson
    }

    /**
     *
     * @param from - {@link AttributeCallbackMapping#from}
     * @param attributeType - {@link AttributeCallbackMapping#attributeType}
     * @param to - {@link AttributeCallbackMapping#to}
     * @param target - {@link AttributeValueTarget}
     */
    public AttributeCallbackMapping(String from, AttributeType attributeType, String to, AttributeValueTarget target) {
        this.from = from;
        this.attributeType = attributeType;
        this.to = to;
        this.targets = Set.of(target);
    }

    /**
     *
     * @param to - {@link AttributeCallbackMapping#to}
     * @param target - {@link AttributeValueTarget}
     * @param value - {@link AttributeCallbackMapping#value}
     */
    public AttributeCallbackMapping(String to, AttributeValueTarget target, Serializable value) {
        this.to = to;
        this.targets = Set.of(target);
        this.value = value;
    }

    /**
     *
     * @param from - {@link AttributeCallbackMapping#from}
     * @param to - {@link AttributeCallbackMapping#to}
     * @param targets - Set of {@link AttributeValueTarget}
     */
    public AttributeCallbackMapping(String from, String to, Set<AttributeValueTarget> targets) {
        this.from = from;
        this.to = to;
        this.targets = targets;
    }

    /**
     *
     * @param from - {@link AttributeCallbackMapping#from}
     * @param attributeType - {@link AttributeCallbackMapping#attributeType}
     * @param to - {@link AttributeCallbackMapping#to}
     * @param targets - Set of {@link AttributeValueTarget}
     */
    public AttributeCallbackMapping(String from, AttributeType attributeType, String to, Set<AttributeValueTarget> targets) {
        this.from = from;
        this.to = to;
        this.attributeType = attributeType;
        this.targets = targets;
    }

    /**
     *
     * @param to - {@link AttributeCallbackMapping#to}
     * @param targets - Set of {@link AttributeValueTarget}
     * @param value - {@link AttributeCallbackMapping#value}
     */
    public AttributeCallbackMapping(String to, Set<AttributeValueTarget> targets, Serializable value) {
        this.to = to;
        this.targets = targets;
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Set<AttributeValueTarget> getTargets() {
        return targets;
    }

    public void setTargets(Set<AttributeValueTarget> targets) {
        this.targets = targets;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from", from)
                .append("attributeType", attributeType)
                .append("to", to)
                .append("targets", targets)
                .append("value", value)
                .toString();
    }
}
