package com.megazone.ERPSystem_phase3_Monolithic.common.config.database;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class NamingCustom implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return applyCamelToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return applyCamelToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return applyCamelToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return applyCamelToSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return applyCamelToSnakeCase(name);
    }

    private Identifier applyCamelToSnakeCase(Identifier name) {
        if (name == null) {
            return null;
        }

        if ("DTYPE".equals(name.getText())) {
            return name; // 변환하지 않고 그대로 반환
        }
        // 카멜 케이스 -> 스네이크 케이스 변환
        String newName = name.getText()
                .replaceAll("([a-z])([A-Z])", "$1_$2") // 카멜 케이스를 언더스코어로 구분
                .toLowerCase(); // 소문자로 변환
        return Identifier.toIdentifier(newName);
    }
}
