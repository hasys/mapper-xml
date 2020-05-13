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

package org.treblereel.gwt.jackson.api.deser.map;

import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.stream.XMLReader;

/**
 * Base {@link XMLDeserializer} implementation for {@link java.util.Map}.
 * @param <M> Type of the {@link java.util.Map}
 * @param <K> Type of the keys inside the {@link java.util.Map}
 * @param <V> Type of the values inside the {@link java.util.Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseMapXMLDeserializer<M extends Map<K, V>, K, V> extends XMLDeserializer<M> {

    /**
     * {@link XMLDeserializer} used to deserialize the keys.
     */
    protected final XMLDeserializer<K> keyDeserializer;

    /**
     * {@link XMLDeserializer} used to deserialize the values.
     */
    protected final XMLDeserializer<V> valueDeserializer;

    /**
     * <p>Constructor for BaseMapXMLDeserializer.</p>
     * @param keyDeserializer {@link XMLDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
     */
    protected BaseMapXMLDeserializer(XMLDeserializer<K> keyDeserializer, XMLDeserializer<V> valueDeserializer) {
        if (null == keyDeserializer) {
            throw new IllegalArgumentException("keyDeserializer cannot be null");
        }
        if (null == valueDeserializer) {
            throw new IllegalArgumentException("valueDeserializer cannot be null");
        }
        this.keyDeserializer = keyDeserializer;
        this.valueDeserializer = valueDeserializer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public M doDeserialize(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) throws XMLStreamException {
        M result = newMap();
        ctx.iterator().doDeserializeMap(reader, result, keyDeserializer, valueDeserializer, ctx, params);
        return result;
    }

    /**
     * Instantiates a new map for deserialization process.
     * @return the new map
     */
    protected abstract M newMap();
}