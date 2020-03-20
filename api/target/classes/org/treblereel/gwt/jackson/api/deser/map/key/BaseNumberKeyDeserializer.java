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


import java.math.BigDecimal;
import java.math.BigInteger;

import org.treblereel.gwt.jackson.api.XMLDeserializationContext;

/**
 * Base implementation of {@link KeyDeserializer} for {@link java.lang.Number}s.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseNumberKeyDeserializer<N extends Number> extends KeyDeserializer<N> {

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link BigDecimal}
     */
    public static final class BigDecimalKeyDeserializer extends BaseNumberKeyDeserializer<BigDecimal> {

        private static final BigDecimalKeyDeserializer INSTANCE = new BigDecimalKeyDeserializer();

        /**
         * @return an instance of {@link BigDecimalKeyDeserializer}
         */
        public static BigDecimalKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private BigDecimalKeyDeserializer() {
        }

        @Override
        protected BigDecimal doDeserialize(String key, XMLDeserializationContext ctx) {
            return new BigDecimal(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link BigInteger}
     */
    public static final class BigIntegerKeyDeserializer extends BaseNumberKeyDeserializer<BigInteger> {

        private static final BigIntegerKeyDeserializer INSTANCE = new BigIntegerKeyDeserializer();

        /**
         * @return an instance of {@link BigIntegerKeyDeserializer}
         */
        public static BigIntegerKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private BigIntegerKeyDeserializer() {
        }

        @Override
        protected BigInteger doDeserialize(String key, XMLDeserializationContext ctx) {
            return new BigInteger(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Byte}
     */
    public static final class ByteKeyDeserializer extends BaseNumberKeyDeserializer<Byte> {

        private static final ByteKeyDeserializer INSTANCE = new ByteKeyDeserializer();

        /**
         * @return an instance of {@link ByteKeyDeserializer}
         */
        public static ByteKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private ByteKeyDeserializer() {
        }

        @Override
        protected Byte doDeserialize(String key, XMLDeserializationContext ctx) {
            return Byte.valueOf(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Double}
     */
    public static final class DoubleKeyDeserializer extends BaseNumberKeyDeserializer<Double> {

        private static final DoubleKeyDeserializer INSTANCE = new DoubleKeyDeserializer();

        /**
         * @return an instance of {@link DoubleKeyDeserializer}
         */
        public static DoubleKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private DoubleKeyDeserializer() {
        }

        @Override
        protected Double doDeserialize(String key, XMLDeserializationContext ctx) {
            return Double.valueOf(key);
        }

    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Float}
     */
    public static final class FloatKeyDeserializer extends BaseNumberKeyDeserializer<Float> {

        private static final FloatKeyDeserializer INSTANCE = new FloatKeyDeserializer();

        /**
         * @return an instance of {@link FloatKeyDeserializer}
         */
        public static FloatKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private FloatKeyDeserializer() {
        }

        @Override
        protected Float doDeserialize(String key, XMLDeserializationContext ctx) {
            return Float.valueOf(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Integer}
     */
    public static final class IntegerKeyDeserializer extends BaseNumberKeyDeserializer<Integer> {

        private static final IntegerKeyDeserializer INSTANCE = new IntegerKeyDeserializer();

        /**
         * @return an instance of {@link IntegerKeyDeserializer}
         */
        public static IntegerKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private IntegerKeyDeserializer() {
        }

        @Override
        protected Integer doDeserialize(String key, XMLDeserializationContext ctx) {
            return Integer.valueOf(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Long}
     */
    public static final class LongKeyDeserializer extends BaseNumberKeyDeserializer<Long> {

        private static final LongKeyDeserializer INSTANCE = new LongKeyDeserializer();

        /**
         * @return an instance of {@link LongKeyDeserializer}
         */
        public static LongKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private LongKeyDeserializer() {
        }

        @Override
        protected Long doDeserialize(String key, XMLDeserializationContext ctx) {
            return Long.valueOf(key);
        }
    }

    /**
     * Default implementation of {@link BaseNumberKeyDeserializer} for {@link Short}
     */
    public static final class ShortKeyDeserializer extends BaseNumberKeyDeserializer<Short> {

        private static final ShortKeyDeserializer INSTANCE = new ShortKeyDeserializer();

        /**
         * @return an instance of {@link ShortKeyDeserializer}
         */
        public static ShortKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private ShortKeyDeserializer() {
        }

        @Override
        protected Short doDeserialize(String key, XMLDeserializationContext ctx) {
            return Short.valueOf(key);
        }
    }

}
