package com.czertainly.util;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.*;
import com.czertainly.api.model.common.attribute.*;
import com.czertainly.api.model.common.attribute.content.*;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.czertainly.core.util.AttributeDefinitionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.czertainly.core.util.AttributeDefinitionUtils.*;

public class AttributeDefinitionUtilsTest {

    @Test
    public void testGetAttribute() {
        String attributeName = "testAttribute";
        List<RequestAttributeDto> attributes = createAttributes(attributeName, 1234);

        RequestAttributeDto attribute = getRequestAttributes(attributeName, attributes);
        Assertions.assertNotNull(attribute);
        Assertions.assertTrue(containsRequestAttributes(attributeName, attributes));
        Assertions.assertEquals(attributes.get(0), attribute);
    }

    @Test
    public void testGetAttributeContent() {
        String attribute1Name = "testAttribute1";
        String attribute2Name = "testAttribute2";

        AttributeDefinition attribute1 = new AttributeDefinition();
        attribute1.setName(attribute1Name);
        attribute1.setContent(new BaseAttributeContent<>(1234));

        AttributeDefinition attribute2 = new AttributeDefinition();
        attribute2.setName(attribute2Name);
        attribute2.setContent(new BaseAttributeContent<>("value"));

        List<AttributeDefinition> attributes = List.of(attribute1, attribute2);

        Object value1 = AttributeDefinitionUtils.getAttributeContent(attribute1Name, attributes);
        Assertions.assertNotNull(value1);
        Assertions.assertTrue(containsAttributeDefinition(attribute1Name, attributes));
        Assertions.assertEquals(attribute1.getContent(), value1);

        Object value2 = AttributeDefinitionUtils.getAttributeContent(attribute2Name, attributes);
        Assertions.assertNotNull(value2);
        Assertions.assertTrue(containsAttributeDefinition(attribute2Name, attributes));
        Assertions.assertEquals(attribute2.getContent(), value2);

        Object value3 = AttributeDefinitionUtils.getAttributeContent("wrongName", attributes);
        Assertions.assertNull(value3);
        Assertions.assertFalse(containsAttributeDefinition("wrongName", attributes));
    }

    @Test
    public void testGetAttributeNameAndUuidContent() {
        String attribute1Name = "testAttribute1";

        HashMap<String, Object> attribute1Value = new HashMap<>();

        HashMap<String, Object> attribute2Value = new HashMap<>();
        attribute2Value.put("uuid", UUID.randomUUID().toString());
        attribute2Value.put("name", "testName");

        attribute1Value.put("data", attribute2Value);
        attribute1Value.put("value", attribute1Name);

        RequestAttributeDto attribute1 = new RequestAttributeDto();
        attribute1.setName(attribute1Name);
        attribute1.setContent(attribute1Value);

        List<RequestAttributeDto> attributes = List.of(attribute1);

        NameAndUuidDto dto = getNameAndUuidData(attribute1Name, attributes);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(attribute2Value.get("uuid"), dto.getUuid());
        Assertions.assertEquals(attribute2Value.get("name"), dto.getName());
    }

    @Test
    public void testGetAttributeCredentialContent() {
        String attribute1Name = "testAttribute1";
        List<RequestAttributeDto> credentialAttributes = createAttributes("credAttr", 987);

        HashMap<String, Object> attribute1Value = new HashMap<>();

        HashMap<String, Object> attribute2Value = new HashMap<>();
        attribute2Value.put("uuid", UUID.randomUUID().toString());
        attribute2Value.put("name", "testName");
        attribute2Value.put("attributes", credentialAttributes);

        attribute1Value.put("data", attribute2Value);
        attribute1Value.put("value", attribute1Name);

        RequestAttributeDto attribute1 = new RequestAttributeDto();
        attribute1.setName(attribute1Name);
        attribute1.setContent(attribute1Value);

        List<RequestAttributeDto> attributes = List.of(attribute1);

        CredentialDto dto = getCredentialContent(attribute1Name, attributes);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(attribute2Value.get("uuid"), dto.getUuid());
        Assertions.assertEquals(attribute2Value.get("name"), dto.getName());
        Assertions.assertEquals(credentialAttributes.get(0).getName(), dto.getAttributes().get(0).getName());
    }

    @Test
    public void testAttributeSerialization() {
        String attrData = "[{ \"name\": \"tokenType\", \"content\": { \"value\": \"PEM\" } }," +
                "{ \"name\": \"description\", \"content\": \"DEMO RA Profile\" }," +
                "{ \"name\": \"endEntityProfile\", \"content\": { \"value\": \"DemoTLSServerEndEntityProfile\", \"data\": { \"id\": 0, \"name\": \"DemoTLSServerEndEntityProfile\" } } }," +
                "{ \"name\": \"certificateProfile\", \"content\": \"DemoTLSServerEECertificateProfile\", \"data\": { \"id\": 0, \"name\": \"DemoTLSServerEECertificateProfile\" } }," +
                "{ \"name\": \"certificationAuthority\", \"content\": \"DemoServerSubCA\", \"data\": { \"id\": 0, \"name\": \"DemoServerSubCA\" } }," +
                "{ \"name\": \"sendNotifications\", \"content\": false }," +
                "{ \"name\": \"keyRecoverable\", \"content\": true }]";

        List<AttributeDefinition> attrs = deserialize(attrData);
        Assertions.assertNotNull(attrs);
        Assertions.assertEquals(7, attrs.size());

        NameAndIdDto endEntityProfile = getNameAndIdData("endEntityProfile", AttributeDefinitionUtils.getClientAttributes(attrs));
        Assertions.assertNotNull(endEntityProfile);
        Assertions.assertEquals(0, endEntityProfile.getId());
        Assertions.assertEquals("DemoTLSServerEndEntityProfile", endEntityProfile.getName());

        String serialized = serialize(attrs);
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"tokenType\".*\"content\":.*\"value\":\"PEM\".*$"));
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"keyRecoverable\".*\"content\":true.*$"));
    }

    @Test
    public void testValidateAttributes_success() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("1234");

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_failNoAttribute() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
            // tested method
            validateAttributes(List.of(definition), List.of())
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_failNoValue() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(null); // cause or failure

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_regex() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("1234");
        String validationRegex = "^\\d{4}$";

        Assertions.assertTrue(attributeContent.getValue().matches(validationRegex));

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setValidationRegex(validationRegex);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_regexFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("12345");
        String validationRegex = "^\\d{4}$";

        Assertions.assertFalse(attributeContent.getValue().matches(validationRegex));

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setValidationRegex(validationRegex);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_credentialMap() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);

        CredentialDto credential = new CredentialDto();
        credential.setName("testName");
        credential.setUuid("testUuid");

        JsonAttributeContent credentialContent = new JsonAttributeContent("Test Credential", credential);

        attribute.setContent(credentialContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialDto() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        JsonAttributeContent credentialContent = new JsonAttributeContent(attributeName, new CredentialDto());

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(credentialContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialFail() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(123l); // cause or failure

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_unknownAttribute() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.STRING);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName("unknown-attribute");  // cause or failure
        attribute.setContent("123");

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributeCallback_success() {
        Set<AttributeCallbackMapping> mappings = new HashSet<>();
        mappings.add(new AttributeCallbackMapping(
                "credentialKind",
                AttributeValueTarget.PATH_VARIABLE,
                "softKeyStore"));

        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext("v1/test");
        callback.setCallbackMethod("GET");
        callback.setMappings(mappings);

        RequestAttributeCallback callbackRequest = new RequestAttributeCallback();
        callbackRequest.setPathVariables(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));

        validateCallback(callback, callbackRequest); // should not throw exception
    }

    @Test
    public void testValidateAttributeCallback_fail() {
        Set<AttributeCallbackMapping> mappings = new HashSet<>();
        mappings.add(new AttributeCallbackMapping(
                "credentialKind",
                AttributeValueTarget.PATH_VARIABLE,
                "softKeyStore"));
        mappings.add(new AttributeCallbackMapping(
                "fromAttribute",
                AttributeType.CREDENTIAL,
                "toQueryParam",
                AttributeValueTarget.REQUEST_PARAMETER));
        mappings.add(new AttributeCallbackMapping(
                "fromAttribute",
                "toBodyKey",
                AttributeValueTarget.BODY));

        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext("core/getCredentials");
        callback.setCallbackMethod("GET");
        callback.setMappings(mappings);

        RequestAttributeCallback callbackRequest = new RequestAttributeCallback();
        callbackRequest.setPathVariables(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));
        callbackRequest.setQueryParameters(Map.ofEntries(Map.entry("toQueryParam", 1234)));


        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validateCallback(callback, callbackRequest));

        Assertions.assertNotNull(exception.getErrors());
        Assertions.assertFalse(exception.getErrors().isEmpty());
        Assertions.assertEquals(2, exception.getErrors().size());
    }

    @Test
    public void testGetAttributeJsonContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Item\",\n" +
                "      \"data\": {\n" +
                "        \"customField\": 1234,\n" +
                "        \"exists\": true,\n" +
                "        \"name\": \"testingName\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        JsonAttributeContent data = getAttributeContent("testJsonAttribute", attrs, JsonAttributeContent.class);

        Assertions.assertEquals("Item", data.getValue());
    }

    @Test
    public void testGetAttributeStringContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Item\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        BaseAttributeContent<String> data = getAttributeContent("testJsonAttribute", attrs, BaseAttributeContent.class);

        Assertions.assertEquals("Item", data.getValue());
    }

    @Test
    public void testGetAttributeInetegerContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": 1234\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        BaseAttributeContent<Integer> data = getAttributeContent("testJsonAttribute", attrs, BaseAttributeContent.class);

        Assertions.assertEquals(1234, data.getValue());
    }

    @Test
    public void testGetAttributeDateTimeContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"2011-12-03T10:15:30+01:00\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        DateTimeAttributeContent data = getAttributeContent("testJsonAttribute", attrs, DateTimeAttributeContent.class);

        String dateInString = "2011-12-03T10:15:30+01:00";
        ZonedDateTime date = ZonedDateTime.parse(dateInString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Assertions.assertEquals(date, data.getValue());
    }

    @Test
    public void testGetAttributeDateContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"2001-07-04\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        DateAttributeContent data = getAttributeContent("testJsonAttribute", attrs, DateAttributeContent.class);

        String dateInString = "2001-07-04";
        LocalDate localDate = LocalDate.parse(dateInString);

        Assertions.assertEquals(localDate, data.getValue());
    }

    @Test
    public void testGetAttributeTimeContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"12:14:25\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        TimeAttributeContent data = getAttributeContent("testJsonAttribute", attrs, TimeAttributeContent.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dateInString = "12:14:25";
        LocalTime localTime = LocalTime.parse(dateInString);

        Assertions.assertEquals(localTime, data.getValue());
    }

    @Test
    public void testGetStringAttributeContentValue_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testStringAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Test String Value\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        String value = getAttributeContentValue("testStringAttribute", attrs, BaseAttributeContent.class);

        Assertions.assertNotNull(value);
        Assertions.assertEquals("Test String Value", value);
    }

    @Test
    public void testGetFileAttributeContentValue_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testFileAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Test File Value\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        String value = getAttributeContentValue("testFileAttribute", attrs, FileAttributeContent.class);

        Assertions.assertNotNull(value);
        Assertions.assertEquals("Test File Value", value);
    }

    @Test
    public void testGetAttributeContentValue_fail() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testFileAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Test File Value\"\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        String value = getAttributeContentValue("testStringAttribute", attrs, FileAttributeContent.class);
        Assertions.assertNull(value);
    }

    @Test
    public void testGetCredentialAttributeContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testCredentialAttribute\",\n" +
                "    \"content\": {\n" +
                "      \"value\": \"Test Credential Value\",\n" +
                "      \"data\": {\n" +
                "        \"uuid\": \"9379ca2c-aa51-42c8-8afd-2a2d16c99c57\",\n" +
                "        \"name\": \"Test Credential\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        NameAndUuidDto data = getJsonAttributeContentData("testCredentialAttribute", attrs, NameAndUuidDto.class);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getClass(), NameAndUuidDto.class);
    }

    @Test
    public void testGetAttributeContentAsListOfString_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testAttributeListString\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"value\": \"string1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"value\": \"string2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"value\": \"string3\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        List<String> listString = getAttributeContentValueList("testAttributeListString", attrs, BaseAttributeContent.class);

        Assertions.assertNotNull(listString);
        Assertions.assertEquals(3, listString.size());
    }

    @Test
    public void testGetJsonAttributeContentAsListOfUuidAndName_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testCredentialAttribute\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"value\": \"Test Credential Value 1\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"9379ca2c-aa51-42c8-8afd-2a2d16c99c57\",\n" +
                "          \"name\": \"Test Credential 1\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"value\": \"Test Credential Value 2\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"696a354f-55d2-4507-b454-a5a7475a7932\",\n" +
                "          \"name\": \"Test Credential 2\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        List<AttributeDefinition> attrs = deserialize(attrData);

        List<String> listData = getJsonAttributeContentDataList("testCredentialAttribute", attrs, NameAndUuidDto.class);

        Assertions.assertNotNull(listData);
        Assertions.assertEquals(2, listData.size());
    }
}
