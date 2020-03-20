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

package org.treblereel.gwt.jackson.api.deser.array.cast;

import org.treblereel.gwt.jackson.api.JacksonContextProvider;
import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.deser.BaseNumberXMLDeserializer;
import org.treblereel.gwt.jackson.api.deser.array.AbstractArrayXMLDeserializer;
import org.treblereel.gwt.jackson.api.stream.XMLReader;

/**
 * Default {@link XMLDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveIntegerArrayXMLDeserializer extends AbstractArrayXMLDeserializer<int[]> {

    private static final PrimitiveIntegerArrayXMLDeserializer INSTANCE = new PrimitiveIntegerArrayXMLDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveIntegerArrayXMLDeserializer}
     */
    public static PrimitiveIntegerArrayXMLDeserializer getInstance() {
        return INSTANCE;
    }


    private PrimitiveIntegerArrayXMLDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public int[] doDeserializeArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        return JacksonContextProvider.get().integerArrayReader().readArray(reader);

    }

    /** {@inheritDoc} */
    @Override
    protected int[] doDeserializeSingleArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        return new int[]{BaseNumberXMLDeserializer.IntegerXMLDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}