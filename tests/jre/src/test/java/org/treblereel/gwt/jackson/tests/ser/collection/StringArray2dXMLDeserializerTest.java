package org.treblereel.gwt.jackson.tests.ser.collection;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.treblereel.gwt.jackson.tests.beans.collection.PrimitiveArrays;
import org.treblereel.gwt.jackson.tests.beans.collection.StringArray2d;
import org.treblereel.gwt.jackson.tests.beans.collection.StringArray2d_MapperImpl;

import static org.junit.Assert.assertEquals;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 3/29/20
 */
public class StringArray2dXMLDeserializerTest {

    StringArray2d_MapperImpl mapper = StringArray2d_MapperImpl.INSTANCE;

    @Test
    public void testDeserializeValue() throws XMLStreamException {
        StringArray2d test = new StringArray2d();

        assertEquals("<?xml version='1.0' encoding='UTF-8'?><StringArray2d><array><array><array>AAA</array><array>BB</array></array><array><array>CCC</array><array>DDD</array></array></array></StringArray2d>", mapper.write(test));
        assertEquals(test, mapper.read(mapper.write(test)));
        test.setCheck1("ONE");
        test.setCheck2("TWO");
        assertEquals("<?xml version='1.0' encoding='UTF-8'?><StringArray2d><check1>ONE</check1><array><array><array>AAA</array><array>BB</array></array><array><array>CCC</array><array>DDD</array></array></array><check2>TWO</check2></StringArray2d>", mapper.write(test));
        assertEquals(test, mapper.read(mapper.write(test)));

    }

}