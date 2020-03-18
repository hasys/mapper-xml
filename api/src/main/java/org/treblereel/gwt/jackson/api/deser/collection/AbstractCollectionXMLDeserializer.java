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

package org.treblereel.gwt.jackson.api.deser.collection;

import java.util.AbstractCollection;
import java.util.ArrayList;

import org.treblereel.gwt.jackson.api.XMLDeserializer;

/**
 * Default {@link XMLDeserializer} implementation for {@link java.util.AbstractCollection}. The deserialization process returns an {@link java.util.ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link java.util.AbstractCollection}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class AbstractCollectionXMLDeserializer<T> extends BaseCollectionXMLDeserializer<AbstractCollection<T>, T> {

    /**
     * <p>newInstance</p>
     *
     * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link java.util.AbstractCollection}.
     * @param <T>          Type of the elements inside the {@link java.util.AbstractCollection}
     * @return a new instance of {@link AbstractCollectionXMLDeserializer}
     */
    public static <T> AbstractCollectionXMLDeserializer<T> newInstance(XMLDeserializer<T> deserializer) {
        return new AbstractCollectionXMLDeserializer<T>(deserializer);
    }

    /**
     * @param deserializer {@link XMLDeserializer} used to deserialize the objects inside the {@link AbstractCollection}.
     */
    private AbstractCollectionXMLDeserializer(XMLDeserializer<T> deserializer) {
        super(deserializer);
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractCollection<T> newCollection() {
        return new ArrayList<T>();
    }
}
