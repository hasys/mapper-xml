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

package org.treblereel.gwt.jackson.api.ser.array.dd;

import org.treblereel.gwt.jackson.api.XMLSerializationContext;
import org.treblereel.gwt.jackson.api.XMLSerializer;
import org.treblereel.gwt.jackson.api.XMLSerializerParameters;
import org.treblereel.gwt.jackson.api.stream.XMLWriter;

/**
 * Default {@link XMLSerializer} implementation for 2D array of long.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveLongArray2dXMLSerializer extends XMLSerializer<long[][]> {

    private static final PrimitiveLongArray2dXMLSerializer INSTANCE = new PrimitiveLongArray2dXMLSerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveLongArray2dXMLSerializer}
     */
    public static PrimitiveLongArray2dXMLSerializer getInstance() {
        return INSTANCE;
    }

    private PrimitiveLongArray2dXMLSerializer() {
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isEmpty(long[][] value) {
        return null == value || value.length == 0;
    }

    /** {@inheritDoc} */
    @Override
    public void doSerialize(XMLWriter writer, long[][] values, XMLSerializationContext ctx, XMLSerializerParameters params) {
        if (!ctx.isWriteEmptyXMLArrays() && values.length == 0) {
            writer.cancelName();
            return;
        }

        writer.beginArray();
        for (long[] array : values) {
            writer.beginArray();
            for (long value : array) {
                writer.value(value);
            }
            writer.endArray();
        }
        writer.endArray();
    }
}