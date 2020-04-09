package org.treblereel.gwt.jackson.tests.deser.collection;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.treblereel.gwt.jackson.tests.beans.collection.StringArray2d;
import org.treblereel.gwt.jackson.tests.beans.collection.StringArray2d_MapperImpl;

import static org.junit.Assert.assertEquals;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 3/29/20
 */
public class StringArray2dXMLDeserializerTest {

    StringArray2d test = new StringArray2d();
    StringArray2d_MapperImpl mapper = StringArray2d_MapperImpl.INSTANCE;

    @Test
    public void testDeserializeValue() throws XMLStreamException {
        test.setCheck1("Check1");
        test.setCheck2("Check2");
        System.out.println("xml " + mapper.write(test));
        //show(test);
        //show(mapper.read(mapper.write(test)));
        //System.out.println("2 " + mapper.read(mapper.write(test)).toString());
        assertEquals(test, mapper.read(mapper.write(test)));
    }

    private void show(StringArray2d arr) {
        int c = 0;
        for (String[] strings : arr.getArray()) {

            for (String string : strings) {
                System.out.println(c + " " + string);
            }
            c++;
        }
    }
}
