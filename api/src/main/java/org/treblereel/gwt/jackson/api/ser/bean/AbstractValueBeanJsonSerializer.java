/*
 * Copyright 2014 Nicolas Morel
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

import java.util.Set;

import org.treblereel.gwt.jackson.api.XMLSerializationContext;
import org.treblereel.gwt.jackson.api.stream.XMLWriter;

/**
 * <p>Abstract AbstractValueBeanJsonSerializer class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractValueBeanJsonSerializer<T> extends AbstractBeanJsonSerializer<T> {

    private final BeanPropertySerializer<T, ?> serializer;

    /**
     * <p>Constructor for AbstractValueBeanJsonSerializer.</p>
     */
    protected AbstractValueBeanJsonSerializer() {
        super();
        this.serializer = initValueSerializer();
    }

    /**
     * <p>initValueSerializer</p>
     *
     * @return a {@link BeanPropertySerializer} object.
     */
    protected abstract BeanPropertySerializer<T, ?> initValueSerializer();

    /** {@inheritDoc} */
    @Override
    protected void serializeObject(XMLWriter writer, T value, XMLSerializationContext ctx, Set<String> ignoredProperties,
                                   IdentitySerializationInfo identityInfo, ObjectIdSerializer<?> idWriter, String typeName, String
                                           typeInformation) {
        serializer.serialize(writer, value, ctx);
    }
}
