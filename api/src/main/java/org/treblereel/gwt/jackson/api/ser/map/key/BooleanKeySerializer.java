/*
 * Copyright 2015 Nicolas Morel
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

package org.treblereel.gwt.jackson.api.ser.map.key;

import org.treblereel.gwt.jackson.api.XMLSerializationContext;

/**
 * Default {@link KeySerializer} implementation for {@link Boolean}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class BooleanKeySerializer extends KeySerializer<Boolean> {

    private static final BooleanKeySerializer INSTANCE = new BooleanKeySerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link BooleanKeySerializer}
     */
    @SuppressWarnings("unchecked")
    public static BooleanKeySerializer getInstance() {
        return INSTANCE;
    }

    private BooleanKeySerializer() {
    }

    /** {@inheritDoc} */
    @Override
    public boolean mustBeEscaped(XMLSerializationContext ctx) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    protected String doSerialize(Boolean value, XMLSerializationContext ctx) {
        return value.toString();
    }
}
