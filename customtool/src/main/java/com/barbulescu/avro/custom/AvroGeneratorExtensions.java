package com.barbulescu.avro.custom;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

import static java.util.stream.Collectors.joining;

public class AvroGeneratorExtensions {

    public String staticConstructor(SpecificCompiler specificCompiler, Schema schema) {
        if (schema.getFields().size() != 1) {
            return "";
        }
        String parameters = createParameters(specificCompiler, schema);
        String setters = createSetters(specificCompiler, schema);
        return "public static " + specificCompiler.javaType(schema) + " of(" + parameters + ") {return newBuilder()" + setters + ".build();}";
    }

    public String newPopulatedBuilder(SpecificCompiler specificCompiler, Schema schema) {
        if (schema.getFields().size() > 3) {
            return "";
        }
        String parameters = createParameters(specificCompiler, schema);
        String setters = createSetters(specificCompiler, schema);

        return "public static " + specificCompiler.javaType(schema) + ".Builder newPopulatedBuilder(" + parameters + ") {return newBuilder()" + setters + ";}";
    }

    private String createSetters(SpecificCompiler specificCompiler, Schema schema) {
        String setters = schema.getFields().stream()
                .map(f -> "." + specificCompiler.generateSetMethod(schema, f) + "(" + f.name() + ")")
                .collect(joining("\n"));
        return setters;
    }

    private String createParameters(SpecificCompiler specificCompiler, Schema schema) {
        String parameters = schema.getFields().stream()
                .map(f -> specificCompiler.javaType(f.schema()) + " " + f.name())
                .collect(joining(", "));
        return parameters;
    }
}
