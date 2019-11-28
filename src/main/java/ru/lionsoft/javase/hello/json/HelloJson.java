/*
 * File:    HelloJson.java
 * Project: HelloJavaSE
 * Date:    22 нояб. 2019 г. 16:40:20
 * Author:  Igor Morenko <morenko at lionsoft.ru>
 * 
 * Copyright 2005-2019 LionSoft LLC. All rights reserved.
 */
package ru.lionsoft.javase.hello.json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 * Пример по работе с JSON форматом (JSON-P)
 *
 * @author Igor Morenko <morenko at lionsoft.ru>
 */
public class HelloJson {

    public static void main(String[] args) throws IOException {

//        JsonObject object = Json.createObjectBuilder().build();
        JsonObject json = Json.createObjectBuilder()
                .add("name", "foo")
                .add("num", 100)
                .add("balance", 1000.21)
                .add("is_vip", true)
                .build();
        System.out.println(json);

        // Create Json Object
        JsonObject model = createJsonObjectModel();
        System.out.println("model = " + model);

        navigateTree(model, null);
        
        // Writing an Object Model to a Stream (Write to file)
        try (JsonWriter writer = Json.createWriter(new FileWriter("example.json"));) {
            writer.writeObject(model);
        }
        // close writer

        // Creating an Object Model from JSON Data (Read from file)
        try (JsonReader reader = Json.createReader(new FileReader("example.json"));) {
            JsonObject readValue = reader.readObject();
            System.out.println("read: = " + readValue);
            String firstName = readValue.getString("firstName");
            System.out.println("firstName = " + firstName);
        }
        // close reader
    }
    
    /*
    Example JSON:

    {
        "firstName": "John", "lastName": "Smith", "age": 25,
        "address" : {
            "streetAddress": "21 2nd Street",
            "city": "New York",
            "state": "NY",
            "postalCode": "10021"
        },
        "phoneNumber": [
            { "type": "home", "number": "212 555-1234" },
            { "type": "fax", "number": "646 555-4567" }
        ]
    }        
     */
    public static JsonObject createJsonObjectModel() {
        Map<String, Object> config = new HashMap<>();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        return factory.createObjectBuilder()
                .add("firstName", "John")
                .add("lastName", "Smith")
                .add("age", 25)
                .add("address", factory.createObjectBuilder()
                        .add("streetAddress", "21 2nd Street")
                        .add("city", "New York")
                        .add("state", "NY")
                        .add("postalCode", "10021"))
                .add("phoneNumber", factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "212 555-1234"))
                        .add(factory.createObjectBuilder()
                                .add("type", "fax")
                                .add("number", "646 555-4567")))
                .build();
    }

    public static void navigateTree(JsonValue tree, String key) {
        if (key != null) {
            System.out.print("Key " + key + ": ");
        }
        switch (tree.getValueType()) {
            case OBJECT:
                System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet()) {
                    navigateTree(object.get(name), name);
                }
                break;
            case ARRAY:
                System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array) {
                    navigateTree(val, null);
                }
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println("STRING " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println("NUMBER " + num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }

    public void readJsonDataUsingParser(String jsonData) {
        JsonParser parser = Json.createParser(new StringReader(jsonData));
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    System.out.println(event.toString());
                    break;
                case KEY_NAME:
                    System.out.print(event.toString() + " "
                            + parser.getString() + " - ");
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.println(event.toString() + " "
                            + parser.getString());
                    break;
            }
        }
    }

    public void writeJsonDataUsingGenerator() throws IOException {
        FileWriter writer = new FileWriter("test.txt");
        JsonGenerator gen = Json.createGenerator(writer);
        gen.writeStartObject()
                .write("firstName", "Duke")
                .write("lastName", "Java")
                .write("age", 18)
                .write("streetAddress", "100 Internet Dr")
                .write("city", "JavaTown")
                .write("state", "JA")
                .write("postalCode", "12345")
                .writeStartArray("phoneNumbers")
                .writeStartObject()
                .write("type", "mobile")
                .write("number", "111-111-1111")
                .writeEnd()
                .writeStartObject()
                .write("type", "home")
                .write("number", "222-222-2222")
                .writeEnd()
                .writeEnd()
                .writeEnd();
        gen.close();
    }
}
