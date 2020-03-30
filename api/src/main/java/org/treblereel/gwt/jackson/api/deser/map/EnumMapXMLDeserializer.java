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

import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.deser.map.key.EnumKeyDeserializer;

import java.util.EnumMap;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.EnumMap}.
 * <p>Cannot be overriden. Use {@link BaseMapXMLDeserializer}.</p>
 *
 * @param <E> Type of the enum keys inside the {@link java.util.EnumMap}
 * @param <V> Type of the values inside the {@link java.util.EnumMap}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class EnumMapXMLDeserializer<E extends Enum<E>, V> extends BaseMapXMLDeserializer<EnumMap<E, V>, E, V> {

    /**
     * <p>newInstance</p>
     *
     * @param keyDeserializer   {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
     * @param <V>               Type of the values inside the {@link java.util.EnumMap}
     * @return a new instance of {@link EnumMapXMLDeserializer}
     */
    public static <E extends Enum<E>, V> EnumMapXMLDeserializer<E, V> newInstance(EnumKeyDeserializer<E> keyDeserializer,
                                                                                   XMLDeserializer<V> valueDeserializer) {
        return new EnumMapXMLDeserializer<>(keyDeserializer, valueDeserializer);
    }

    /**
     * Class of the enum key
     */
    private final Class<E> enumClass;

    /**
     * @param keyDeserializer   {@link EnumKeyDeserializer} used to deserialize the enum keys.
     * @param valueDeserializer {@link XMLDeserializer} used to deserialize the values.
     */
    private EnumMapXMLDeserializer(EnumKeyDeserializer<E> keyDeserializer, XMLDeserializer<V> valueDeserializer) {
        super(keyDeserializer, valueDeserializer);
        this.enumClass = keyDeserializer.getEnumClass();
    }

    /** {@inheritDoc} */
    @Override
    protected EnumMap<E, V> newMap() {
        return new EnumMap<>(enumClass);
    }
}
