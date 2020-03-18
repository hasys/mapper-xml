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

import java.util.Map;

import org.treblereel.gwt.jackson.api.XMLSerializationContext;
import org.treblereel.gwt.jackson.api.ser.map.MapXMLSerializer;
import org.treblereel.gwt.jackson.api.stream.XMLWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AnyGetterPropertySerializer<T> extends BeanPropertySerializer<T, Map> {

    /**
     * <p>newSerializer</p>
     *
     * @return a {@link MapXMLSerializer} object.
     */
    protected abstract MapXMLSerializer newSerializer();

    /**
     * <p>Constructor for AnyGetterPropertySerializer.</p>
     */
    public AnyGetterPropertySerializer() {
        super(null);
    }

    /** {@inheritDoc} */
    public void serializePropertyName(XMLWriter writer, T bean, XMLSerializationContext ctx) {
        // no-op
    }

    /**
     * {@inheritDoc}
     * <p>
     * Serializes the property defined for this instance.
     */
    public void serialize(XMLWriter writer, T bean, XMLSerializationContext ctx) {
        Map map = getValue(bean, ctx);
        if (null != map) {
            ((MapXMLSerializer) getSerializer()).serializeValues(writer, map, ctx, getParameters());
        }
    }
}
