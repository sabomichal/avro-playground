package com.barbulescu.avro.custom;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.compiler.specific.SpecificCompiler;

import static java.util.stream.Collectors.joining;
import static org.apache.avro.compiler.specific.SpecificCompiler.generateGetMethod;
import static org.apache.avro.compiler.specific.SpecificCompiler.generateSetMethod;

public class AvroGeneratorExtensions {

    public String staticConstructor(SpecificCompiler specificCompiler, Schema schema) {
        if (schema.getFields().size() != 1) {
            return "";
        }
        String parameters = createParameters(specificCompiler, schema);
        String setters = createSetters(schema);
        return "public static " + specificCompiler.javaType(schema) + " of(" + parameters + ") {return newBuilder()" + setters + ".build();}";
    }

    public String nestedGetter(SpecificCompiler specificCompiler, Schema schema) {
        return schema.getFields()
                .stream()
                .filter(f -> f.schema().getType().equals(Schema.Type.RECORD))
                .filter(f -> f.schema().getFields().size() == 1)
                .map(f -> nestedGetter(specificCompiler, schema, f))
                .collect(joining("\n"));
    }

    private String nestedGetter(SpecificCompiler specificCompiler, Schema schema, Field field) {
        Schema.Field innerField = field.schema().getFields().get(0);
        String innerType = specificCompiler.javaType(innerField.schema());
        String innerFieldName = innerField.name().substring(0, 1).toUpperCase() + innerField.name().substring(1);
        String getterName = generateGetMethod(schema, field);
        return "public " + innerType + " " + getterName + innerFieldName + "() { return this." + getterName + "().get" + innerFieldName + "();}";
    }

    public String nestedSetter(SpecificCompiler specificCompiler, Schema schema) {
        return schema.getFields()
                .stream()
                .filter(f -> f.schema().getType().equals(Schema.Type.RECORD))
                .filter(f -> f.schema().getFields().size() == 1)
                .map(f -> nestedSetter(specificCompiler, schema, f))
                .collect(joining("\n"));
    }

    private String nestedSetter(SpecificCompiler specificCompiler, Schema schema, Field field) {
        Schema.Field innerField = field.schema().getFields().get(0);
        String builderType = specificCompiler.javaType(schema) + ".Builder";
        String innerType = specificCompiler.javaType(innerField.schema());
        String innerName = innerField.name();
        String capitalizedInnerName = innerName.substring(0, 1).toUpperCase() + innerName.substring(1);
        String methodName = generateSetMethod(schema, field) + "Builder";
        return "public " + builderType + " " + methodName + "(" + innerType + " " + innerName + ") { " +
                "return " + methodName + "(" + specificCompiler.javaType(field.schema()) + ".newBuilder().set" + capitalizedInnerName + "(" + innerName + "));}";
    }

    public String newPopulatedBuilder(SpecificCompiler specificCompiler, Schema schema) {
        if (schema.getFields().size() > 3) {
            return "";
        }
        String parameters = createParameters(specificCompiler, schema);
        String setters = createSetters(schema);

        return "public static " + specificCompiler.javaType(schema) + ".Builder newPopulatedBuilder(" + parameters + ") {return newBuilder()" + setters + ";}";
    }

    private String createSetters(Schema schema) {
        String setters = schema.getFields().stream()
                .map(f -> "." + generateSetMethod(schema, f) + "(" + f.name() + ")")
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
