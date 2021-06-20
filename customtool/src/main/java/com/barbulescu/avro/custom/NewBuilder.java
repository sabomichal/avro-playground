package com.barbulescu.avro.custom;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

import static java.util.stream.Collectors.joining;

public class NewBuilder {

    public String newBuilder(SpecificCompiler specificCompiler, Schema schema) {
        if (schema.getFields().size() > 1) {
            return "";
        }
        String parameters = schema.getFields().stream()
                .map(f -> specificCompiler.javaType(f.schema()) + " " + f.name())
                .collect(joining(", "));
        String setters = schema.getFields().stream()
                .map(f -> "." + specificCompiler.generateSetMethod(schema, f) + "(" + f.name() + ")")
                .collect(joining("\n"));

        return "public static " + specificCompiler.javaType(schema) + ".Builder newBuilder(" + parameters + ") {return newBuilder()"+setters+";}";
    }

}
