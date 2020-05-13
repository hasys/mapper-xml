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

package org.treblereel.gwt.jackson.api.deser.array.dd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.treblereel.gwt.jackson.api.XMLDeserializationContext;
import org.treblereel.gwt.jackson.api.XMLDeserializer;
import org.treblereel.gwt.jackson.api.XMLDeserializerParameters;
import org.treblereel.gwt.jackson.api.deser.StringXMLDeserializer;
import org.treblereel.gwt.jackson.api.stream.XMLReader;
import org.treblereel.gwt.jackson.api.utils.Base64Utils;

/**
 * Default {@link XMLDeserializer} implementation for 2D array of byte.
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveByteArray2dXMLDeserializer extends AbstractArray2dXMLDeserializer<byte[][]> {

    private static final PrimitiveByteArray2dXMLDeserializer INSTANCE = new PrimitiveByteArray2dXMLDeserializer();

    private PrimitiveByteArray2dXMLDeserializer() {
    }

    /**
     * <p>getInstance</p>
     * @return an instance of {@link PrimitiveByteArray2dXMLDeserializer}
     */
    public static PrimitiveByteArray2dXMLDeserializer getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[][] doDeserialize(XMLReader reader, XMLDeserializationContext ctx, XMLDeserializerParameters params) throws XMLStreamException {

        byte[][] result;

        List<String> strings = doDeserializeInnerIntoList(reader, ctx, StringXMLDeserializer.getInstance(), params);

        if (strings.isEmpty()) {
            result = new byte[0][0];
        } else {
            List<byte[]> list = new ArrayList<>();

            int size = 0;
            for (String string : strings) {
                byte[] decoded = Base64Utils.fromBase64(string);
                size = Math.max(size, decoded.length);
                list.add(decoded);
            }

            result = new byte[list.size()][size];
            int i = 0;
            for (byte[] value : list) {
                if (null != value) {
                    result[i] = value;
                }
                i++;
            }
        }
        return result;
    }
}