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

package org.treblereel.gwt.jackson.api.deser.array;

import java.util.List;

import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.deser.BooleanXMLDeserializer;
import org.treblereel.gwt.jackson.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for array of boolean.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveBooleanArrayXMLDeserializer extends AbstractArrayXMLDeserializer<boolean[]> {

    private static final PrimitiveBooleanArrayXMLDeserializer INSTANCE = new PrimitiveBooleanArrayXMLDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveBooleanArrayXMLDeserializer}
     */
    public static PrimitiveBooleanArrayXMLDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveBooleanArrayXMLDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public boolean[] doDeserializeArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        List<Boolean> list = deserializeIntoList(reader, ctx, BooleanXMLDeserializer.getInstance(), params);

        boolean[] result = new boolean[list.size()];
        int i = 0;
        for (Boolean value : list) {
            if (null != value) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean[] doDeserializeSingleArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        return new boolean[]{BooleanXMLDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}