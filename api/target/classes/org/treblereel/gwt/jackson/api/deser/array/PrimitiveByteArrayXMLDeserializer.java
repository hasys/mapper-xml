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
import org.treblereel.gwt.jackson.api.deser.BaseNumberXMLDeserializer;
import org.treblereel.gwt.jackson.api.stream.XMLReader;
import org.treblereel.gwt.jackson.api.stream.XMLToken;
import org.treblereel.gwt.jackson.api.utils.Base64Utils;

/**
 * Default {@link XMLDeserializer} implementation for array of byte.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveByteArrayXMLDeserializer extends AbstractArrayXMLDeserializer<byte[]> {

    private static final PrimitiveByteArrayXMLDeserializer INSTANCE = new PrimitiveByteArrayXMLDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveByteArrayXMLDeserializer}
     */
    public static PrimitiveByteArrayXMLDeserializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveByteArrayXMLDeserializer() {
    }

    /** {@inheritDoc} */
    @Override
    public byte[] doDeserializeArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        List<Byte> list = deserializeIntoList(reader, ctx, BaseNumberXMLDeserializer.ByteXMLDeserializer.getInstance(), params);

        byte[] result = new byte[list.size()];
        int i = 0;
        for (Byte value : list) {
            if (null != value) {
                result[i] = value;
            }
            i++;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    protected byte[] doDeserializeNonArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        if (XMLToken.STRING == reader.peek()) {
            return Base64Utils.fromBase64(reader.nextString());
        } else if (ctx.isAcceptSingleValueAsArray()) {
            return doDeserializeSingleArray(reader, ctx, params);
        } else {
            throw ctx.traceError("Cannot deserialize a byte[] out of " + reader.peek() + " token", reader);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected byte[] doDeserializeSingleArray(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) {
        return new byte[]{BaseNumberXMLDeserializer.ByteXMLDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}