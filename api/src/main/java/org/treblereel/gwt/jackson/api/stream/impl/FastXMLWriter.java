//@formatter:off
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

package org.treblereel.gwt.jackson.api.stream.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.treblereel.gwt.jackson.api.JacksonContextProvider;
import org.treblereel.gwt.jackson.api.exception.XMLSerializationException;
import org.treblereel.gwt.jackson.api.stream.Stack;
import org.treblereel.gwt.jackson.api.stream.XMLWriter;

/**
 * <p>FastXMLWriter class.</p>
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public class FastXMLWriter implements XMLWriter {

    private static final Logger logger = Logger.getLogger("XMLWriter");

    /**
     * The output data, containing at most one top-level array or object.
     */
    private final StringBuilder out;

    //Change by Ahmad Bawaneh, replace JSNI types with IsInterop types
    private Stack<Integer> stack = JacksonContextProvider.get().integerStackFactory().make();
    private int stackSize = 0;

    {
        push(JsonScope.EMPTY_DOCUMENT);
    }

    /**
     * A string containing a full set of spaces for a single level of
     * indentation, or null for no pretty printing.
     */
    private String indent;

    /**
     * The name/value separator; either ":" or ": ".
     */
    private String separator = ":";

    private boolean lenient;

    private String deferredUnescapeName;

    private String deferredName;

    private boolean serializeNulls = true;

    /**
     * Creates a new instance that writes a JSON-encoded stream to {@code out}.
     *
     * @param out a {@link StringBuilder} object.
     */
    public FastXMLWriter(StringBuilder out) {
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }

    /** {@inheritDoc} */
    @Override
    public final void setIndent(String indent) {
        if (indent.length() == 0) {
            this.indent = null;
            this.separator = ":";
        } else {
            this.indent = indent;
            this.separator = ": ";
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    /**
     * Returns true if this writer has relaxed syntax rules.
     *
     * @return a boolean.
     */
    public boolean isLenient() {
        return lenient;
    }

    /** {@inheritDoc} */
    @Override
    public final void setSerializeNulls(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns true if object members are serialized when their value is null.
     * This has no impact on array elements. The default is true.
     */
    @Override
    public final boolean getSerializeNulls() {
        return serializeNulls;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter beginArray() {
        writeDeferredName();
        return open(JsonScope.EMPTY_ARRAY, "[");
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter endArray() {
        return close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter beginObject() {
        writeDeferredName();
        return open(JsonScope.EMPTY_OBJECT, "{");
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter endObject() {
        return close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
    }

    /**
     * Enters a new scope by appending any necessary whitespace and the given
     * bracket.
     */
    private FastXMLWriter open(int empty, String openBracket) {
        beforeValue(true);
        push(empty);
        out.append(openBracket);
        return this;
    }

    /**
     * Closes the current scope by appending any necessary whitespace and the
     * given bracket.
     */
    private FastXMLWriter close(int empty, int nonempty, String closeBracket) {
        int context = peek();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (deferredUnescapeName != null || deferredName != null) {
            throw new IllegalStateException("Dangling name: " + (deferredUnescapeName == null ? deferredName : deferredUnescapeName));
        }

        stackSize--;
        if (context == nonempty) {
            newline();
        }
        out.append(closeBracket);
        return this;
    }

    private void push(int newTop) {
        stack.setAt(stackSize++, newTop);
    }

    /**
     * Returns the value on the top of the stack.
     */
    private int peek() {
        if (stackSize == 0) {
            throw new IllegalStateException("XMLWriter is closed.");
        }
        return stack.getAt(stackSize - 1);
    }

    /**
     * Replace the value on the top of the stack with the given value.
     */
    private void replaceTop(int topOfStack) {
        stack.setAt(stackSize - 1, topOfStack);
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter name(String name) {
        checkName(name);
        deferredName = name;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter unescapeName(String name) {
        checkName(name);
        deferredUnescapeName = name;
        return this;
    }

    private void checkName(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (deferredUnescapeName != null || deferredName != null) {
            throw new IllegalStateException();
        }
        if (stackSize == 0) {
            throw new IllegalStateException("XMLWriter is closed.");
        }
    }

    private void writeDeferredName() {
        if (deferredUnescapeName != null) {
            beforeName();
            out.append('\"').append(deferredUnescapeName).append('\"');
            deferredUnescapeName = null;
        } else if (deferredName != null) {
            beforeName();
            string(deferredName);
            deferredName = null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter value(String value) {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue(false);
        string(value);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter unescapeValue(String value) {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue(false);
        out.append('\"').append(value).append('\"');
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter nullValue() {
        if (deferredUnescapeName != null || deferredName != null) {
            if (serializeNulls) {
                writeDeferredName();
            } else {
                deferredUnescapeName = null;
                deferredName = null;
                return this; // skip the name and the value
            }
        }
        beforeValue(false);
        out.append("null");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter cancelName() {
        if (deferredUnescapeName != null) {
            deferredUnescapeName = null;
        } else if (deferredName != null) {
            deferredName = null;
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter value(boolean value) {
        writeDeferredName();
        beforeValue(false);
        out.append(value ? "true" : "false");
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter value(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        writeDeferredName();
        beforeValue(false);
        out.append(Double.toString(value));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter value(long value) {
        writeDeferredName();
        beforeValue(false);
        out.append(Long.toString(value));
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter value(Number value) {
        if (value == null) {
            return nullValue();
        }

        writeDeferredName();
        String string = value.toString();
        if (!lenient
                && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        beforeValue(false);
        out.append(string);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public FastXMLWriter rawValue(Object value) {
        if (value == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue(false);
        out.append(value.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void flush() {
        if (stackSize == 0) {
            throw new IllegalStateException("XMLWriter is closed.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        int size = stackSize;
        if (size > 1 || size == 1 && stack.getAt(size - 1) != JsonScope.NONEMPTY_DOCUMENT) {
            logger.log(Level.SEVERE, "Incomplete document");
            throw new XMLSerializationException("Incomplete document");
        }
        stackSize = 0;
    }

    private void string(String value) {
        out.append(JacksonContextProvider.get().stringifier().stringify(value));
    }

    private void newline() {
        if (indent == null) {
            return;
        }

        out.append("\n");
        for (int i = 1, size = stackSize; i < size; i++) {
            out.append(indent);
        }
    }

    /**
     * Inserts any necessary separators and whitespace before a name. Also
     * adjusts the stack to expect the name's value.
     */
    private void beforeName() {
        int context = peek();
        if (context == JsonScope.NONEMPTY_OBJECT) { // first in object
            out.append(',');
        } else if (context != JsonScope.EMPTY_OBJECT) { // not in an object!
            throw new IllegalStateException("Nesting problem.");
        }
        newline();
        replaceTop(JsonScope.DANGLING_NAME);
    }

    /**
     * Inserts any necessary separators and whitespace before a literal value,
     * inline array, or inline object. Also adjusts the stack to expect either a
     * closing bracket or another element.
     *
     * @param root true if the value is a new array or object, the two values
     *             permitted as top-level elements.
     */
    @SuppressWarnings("fallthrough")
    private void beforeValue(boolean root) {
        switch (peek()) {
            case JsonScope.NONEMPTY_DOCUMENT:
                if (!lenient) {
                    throw new IllegalStateException(
                            "JSON must have only one top-level value.");
                }
                // fall-through
            case JsonScope.EMPTY_DOCUMENT: // first in document
                if (!lenient && !root) {
                    throw new IllegalStateException(
                            "JSON must start with an array or an object.");
                }
                replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                break;

            case JsonScope.EMPTY_ARRAY: // first in array
                replaceTop(JsonScope.NONEMPTY_ARRAY);
                newline();
                break;

            case JsonScope.NONEMPTY_ARRAY: // another in array
                out.append(',');
                newline();
                break;

            case JsonScope.DANGLING_NAME: // value for name
                out.append(separator);
                replaceTop(JsonScope.NONEMPTY_OBJECT);
                break;

            default:
                throw new IllegalStateException("Nesting problem.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getOutput() {
        return out.toString();
    }
}
//@formatter:on
