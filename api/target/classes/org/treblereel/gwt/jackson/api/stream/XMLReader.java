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

package org.treblereel.gwt.jackson.api.stream;

/**
 * <p>XMLReader interface.</p>
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public interface XMLReader {

    /**
     * Configure this parser to be  be liberal in what it accepts. By default,
     * this parser is strict and only accepts JSON as specified by <a
     * href="http://www.ietf.org/rfc/rfc4627.txt">RFC 4627</a>. Setting the
     * parser to lenient causes it to ignore the following syntax errors:
     * <div>
     * <ul>
     * <li>Streams that start with the <a href="#nonexecuteprefix">non-execute
     * prefix</a>, <code>")]}'\n"</code>.
     * <li>Streams that include multiple top-level values. With strict parsing,
     * each stream must contain exactly one top-level value.
     * <li>Top-level values of any type. With strict parsing, the top-level
     * value must be an object or an array.
     * <li>Numbers may be {@link Double#isNaN() NaNs} or {@link
     * Double#isInfinite() infinities}.
     * <li>End of line comments starting with {@code //} or {@code #} and
     * ending with a newline character.
     * <li>C-style comments starting with {@code /*} and ending with
     * {@code *}{@code /}. Such comments may not be nested.
     * <li>Names that are unquoted or {@code 'single quoted'}.
     * <li>Strings that are unquoted or {@code 'single quoted'}.
     * <li>Array elements separated by {@code ;} instead of {@code ,}.
     * <li>Unnecessary array separators. These are interpreted as if null
     * was the omitted value.
     * <li>Names and values separated by {@code =} or {@code =>} instead of
     * {@code :}.
     * <li>Name/value pairs separated by {@code ;} instead of {@code ,}.
     * </ul>
     * </div>
     *
     * @param lenient a boolean.
     */
    void setLenient(boolean lenient);

    /**
     * Consumes the next token from the JSON stream and asserts that it is the
     * beginning of a new array.
     */
    void beginArray();

    /**
     * Consumes the next token from the JSON stream and asserts that it is the
     * end of the current array.
     */
    void endArray();

    /**
     * Consumes the next token from the JSON stream and asserts that it is the
     * beginning of a new object.
     */
    void beginObject();

    /**
     * Consumes the next token from the JSON stream and asserts that it is the
     * end of the current object.
     */
    void endObject();

    /**
     * Returns true if the current array or object has another element.
     *
     * @return a boolean.
     */
    boolean hasNext();

    /**
     * Returns the type of the next token without consuming it.
     *
     * @return a {@link XMLToken} object.
     */
    XMLToken peek();

    /**
     * Returns the next token, a {@link XMLToken#NAME property name}, and
     * consumes it.
     *
     * @return a {@link String} object.
     */
    String nextName();

    /**
     * Returns the {@link XMLToken#STRING string} value of the next token,
     * consuming it. If the next token is a number, this method will return its
     * string form.
     *
     * @return a {@link String} object.
     * @throws IllegalStateException if the next token is not a string or if
     *                               this reader is closed.
     */
    String nextString();

    /**
     * Returns the {@link XMLToken#BOOLEAN boolean} value of the next token,
     * consuming it.
     *
     * @return a boolean.
     * @throws IllegalStateException if the next token is not a boolean or if
     *                               this reader is closed.
     */
    boolean nextBoolean();

    /**
     * Consumes the next token from the JSON stream and asserts that it is a
     * literal null.
     *
     * @throws IllegalStateException if the next token is not null or if this
     *                               reader is closed.
     */
    void nextNull();

    /**
     * Returns the {@link XMLToken#NUMBER double} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as a double using {@link Double#parseDouble(String)}.
     *
     * @return a double.
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a double, or is non-finite.
     */
    double nextDouble();

    /**
     * Returns the {@link XMLToken#NUMBER long} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as a long. If the next token's numeric value cannot be exactly
     * represented by a Java {@code long}, this method throws.
     *
     * @return a long.
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a number, or exactly represented as a long.
     */
    long nextLong();

    /**
     * Returns the {@link XMLToken#NUMBER int} value of the next token,
     * consuming it. If the next token is a string, this method will attempt to
     * parse it as an int. If the next token's numeric value cannot be exactly
     * represented by a Java {@code int}, this method throws.
     *
     * @return a int.
     * @throws IllegalStateException if the next token is not a literal value.
     * @throws NumberFormatException if the next literal value cannot be parsed
     *                               as a number, or exactly represented as an int.
     */
    int nextInt();

    /**
     * Closes this JSON reader and the underlying {@link java.io.Reader}.
     */
    void close();

    /**
     * Skips the next value recursively. If it is an object or array, all nested
     * elements are skipped. This method is intended for use when the JSON token
     * stream contains unrecognized or unhandled values.
     */
    void skipValue();

    /**
     * Reads the next value recursively and returns it as a String. If it is an object or array, all nested
     * elements are read.
     *
     * @return a {@link String} object.
     */
    String nextValue();

    /**
     * <p>getLineNumber</p>
     *
     * @return a int.
     */
    int getLineNumber();

    /**
     * <p>getColumnNumber</p>
     *
     * @return a int.
     */
    int getColumnNumber();

    /**
     * <p>getInput</p>
     *
     * @return a {@link String} object.
     */
    String getInput();

    /**
     * Returns the {@link Number} value of the next token, consuming it.
     * This method will attempt to return the best matching number.
     * For non-decimal number, if it fits into an int, an int is returned,
     * else a long else a {@link java.math.BigInteger}.
     * For decimal number, a double is returned.
     * If the next token's numeric value cannot be exactly represented by a Java {@link Number}, this method throws.
     *
     * @return a {@link Number} object.
     * @throws IllegalStateException if the next token is not a number.
     * @throws NumberFormatException if the next value cannot be parsed as a number.
     */
    Number nextNumber();

}
