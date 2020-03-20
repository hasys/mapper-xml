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

import org.treblereel.gwt.jackson.api.JacksonContextProvider;
import org.treblereel.gwt.jackson.api.XMLSerializationContext;
import org.treblereel.gwt.jackson.api.XMLSerializer;
import org.treblereel.gwt.jackson.api.XMLSerializerParameters;
import org.treblereel.gwt.jackson.api.stream.XMLWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BeanPropertySerializer<T, V> extends HasSerializer<V, XMLSerializer<V>> {

    protected final String propertyName;

    private XMLSerializerParameters parameters;

    /**
     * <p>Constructor for BeanPropertySerializer.</p>
     *
     * @param propertyName a {@link String} object.
     */
    protected BeanPropertySerializer(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * <p>Getter for the field <code>parameters</code>.</p>
     *
     * @return a {@link XMLSerializerParameters} object.
     */
    protected XMLSerializerParameters getParameters() {
        if (null == parameters) {
            parameters = newParameters();
        }
        return parameters;
    }

    /**
     * <p>newParameters</p>
     *
     * @return a {@link XMLSerializerParameters} object.
     */
    protected XMLSerializerParameters newParameters() {
        return JacksonContextProvider.get().defaultSerializerParameters();
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
     * Serializes the property name
     *
     * @param writer writer
     * @param bean   bean containing the property to serialize
     * @param ctx    context of the serialization process
     */
    public void serializePropertyName(XMLWriter writer, T bean, XMLSerializationContext ctx) {
        writer.unescapeName(propertyName);
    }

    /**
     * <p>getValue</p>
     *
     * @param bean bean containing the property to serialize
     * @param ctx  context of the serialization process
     * @return the property's value
     */
    public abstract V getValue(T bean, XMLSerializationContext ctx);

    /**
     * Serializes the property defined for this instance.
     *
     * @param writer writer
     * @param bean   bean containing the property to serialize
     * @param ctx    context of the serialization process
     */
    public void serialize(XMLWriter writer, T bean, XMLSerializationContext ctx) {
        getSerializer().serialize(writer, getValue(bean, ctx), ctx, getParameters());
    }
}