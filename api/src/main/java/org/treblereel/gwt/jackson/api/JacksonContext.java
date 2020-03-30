package org.treblereel.gwt.jackson.api;

import java.util.Date;

import javax.xml.stream.XMLStreamException;

import org.treblereel.gwt.jackson.api.deser.bean.MapLike;
import org.treblereel.gwt.jackson.api.deser.map.key.DateKeyParser;
import org.treblereel.gwt.jackson.api.stream.Stack;
import org.treblereel.gwt.jackson.api.stream.XMLReader;

/**
 * <p>JacksonContext interface.</p>
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JacksonContext {

    /**
     * <p>dateFormat.</p>
     * @return a {@link JacksonContext.DateFormat} object.
     */
    DateFormat dateFormat();

    /**
     * <p>integerStackFactory.</p>
     * @return a {@link JacksonContext.IntegerStackFactory} object.
     */
    IntegerStackFactory integerStackFactory();

    /**
     * <p>mapLikeFactory.</p>
     * @return a {@link JacksonContext.MapLikeFactory} object.
     */
    MapLikeFactory mapLikeFactory();

    /**
     * <p>stringArrayReader.</p>
     * @return a {@link JacksonContext.StringArrayReader} object.
     */
    StringArrayReader stringArrayReader();

    /**
     * <p>shortArrayReader.</p>
     * @return a {@link JacksonContext.ShortArrayReader} object.
     */
    ShortArrayReader shortArrayReader();

    /**
     * <p>integerArrayReader.</p>
     * @return a {@link JacksonContext.IntegerArrayReader} object.
     */
    IntegerArrayReader integerArrayReader();

    /**
     * <p>doubleArrayReader.</p>
     * @return a {@link JacksonContext.DoubleArrayReader} object.
     */
    DoubleArrayReader doubleArrayReader();

    /**
     * <p>defaultSerializerParameters.</p>
     * @return a {@link XMLSerializerParameters} object.
     */
    XMLSerializerParameters defaultSerializerParameters();

    /**
     * <p>newSerializerParameters</p>
     * @return a new instance of {@link XMLSerializerParameters} object
     */
    XMLSerializerParameters newSerializerParameters();

    /**
     * <p>defaultDeserializerParameters.</p>
     * @return a {@link XMLDeserializerParameters} object.
     */
    XMLDeserializerParameters defaultDeserializerParameters();

    /**
     * <p>newDeserializerParameters</p>
     * @return a new instance of {@link XMLDeserializerParameters} object
     */
    XMLDeserializerParameters newDeserializerParameters();

    interface DateFormat {

        String format(Date date);

        String format(XMLSerializerParameters params, Date date);

        Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date);

        <D extends Date> DateKeyParser<D> makeDateKeyParser();
    }

    interface IntegerStackFactory {

        Stack<Integer> make();
    }

    interface ValueStringifier {

        String stringify(String value);
    }

    interface MapLikeFactory {

        <T> MapLike<T> make();
    }

    interface StringArrayReader {

        String[] readArray(XMLReader reader) throws XMLStreamException;
    }

    interface ShortArrayReader {

        short[] readArray(XMLReader reader) throws XMLStreamException;
    }

    interface IntegerArrayReader {

        int[] readArray(XMLReader reader) throws XMLStreamException;
    }

    interface DoubleArrayReader {

        double[] readArray(XMLReader reader) throws XMLStreamException;
    }
}
