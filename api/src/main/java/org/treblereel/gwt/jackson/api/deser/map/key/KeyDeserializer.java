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

package org.treblereel.gwt.jackson.api.deser.map.key;

import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.exception.XMLDeserializationException;

/**
 * Base class for all the key deserializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class KeyDeserializer<T> {

    /**
     * Deserializes a key into an object.
     *
     * @param key key to deserialize
     * @param ctx Context for the full deserialization process
     * @return the deserialized object
     * @throws XMLDeserializationException if an error occurs during the deserialization
     */
    public T deserialize(String key, XMLDeserializationContext ctx) throws XMLDeserializationException {
        if (null == key) {
            return null;
        }
        return doDeserialize(key, ctx);
    }

    /**
     * Deserializes a non-null key into an object.
     *
     * @param key key to deserialize
     * @param ctx Context for the full deserialization process
     * @return the deserialized object
     */
    protected abstract T doDeserialize(String key, XMLDeserializationContext ctx);
}
