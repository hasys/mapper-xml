/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.treblereel.gwt.jackson.api.ser.bean;

import java.util.HashMap;
import java.util.Map;

import org.treblereel.gwt.jackson.api.annotation.XMLTypeInfo;
import org.treblereel.gwt.jackson.api.annotation.XMLTypeInfo.As;

/**
 * Contains type serialization informations
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class TypeSerializationInfo<T> {

    /**
     * Inclusion mechanism
     */
    private final As include;

    /**
     * Name of the property containing information about the type
     */
    private final String propertyName;

    private final Map<Class<? extends T>, String> typeClassToInfo;

    /**
     * <p>Constructor for TypeSerializationInfo.</p>
     * @param include      a {@link XMLTypeInfo.As} object.
     * @param propertyName a {@link String} object.
     */
    public TypeSerializationInfo(As include,String propertyName) {
        this.include = include;
        this.propertyName = propertyName;
        this.typeClassToInfo = new HashMap<>();
    }

    /**
     * <p>addTypeInfo</p>
     *
     * @param <S> type of the {@link Class}
     * @param clazz    a {@link Class} object.
     * @param typeInfo a {@link String} object.
     * @return a {@link TypeSerializationInfo} object.
     */
    public <S extends T> TypeSerializationInfo<T> addTypeInfo(Class<S> clazz, String typeInfo) {
        typeClassToInfo.put(clazz, typeInfo);
        return this;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link As} object.
     */
    public As getInclude() {
        return include;
    }

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>getTypeInfo</p>
     *
     * @param aClass a {@link Class} object.
     * @return a {@link String} object.
     */
    public String getTypeInfo(Class aClass) {
        return typeClassToInfo.get(aClass);
    }
}