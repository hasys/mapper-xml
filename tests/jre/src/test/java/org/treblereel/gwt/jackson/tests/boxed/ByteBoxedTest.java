package org.treblereel.gwt.jackson.tests.boxed;

import java.util.Objects;

import javax.xml.stream.XMLStreamException;

import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Test;
import org.treblereel.gwt.jackson.api.annotation.XMLMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 4/22/20
 */
//@J2clTestInput(ByteBoxedTest.class)
public class ByteBoxedTest {

    private static final String XML_0 = "<?xml version='1.0' encoding='UTF-8'?><ByteType/>";
    private static final String XML_123 = "<?xml version='1.0' encoding='UTF-8'?><ByteType><check>123</check></ByteType>";
    private static final String XML_22 = "<?xml version='1.0' encoding='UTF-8'?><ByteType><check>-22</check></ByteType>";

    private ByteBoxedTest_ByteType_MapperImpl mapper = ByteBoxedTest_ByteType_MapperImpl.INSTANCE;

    @Test
    public void testSerializeValue() throws XMLStreamException {
        ByteType test = new ByteType();
        assertEquals(XML_0, mapper.write(test));
        test.setCheck((byte) 123);
        assertEquals(XML_123, mapper.write(test));
        assertEquals(test, mapper.read(mapper.write(test)));
        test.setCheck((byte) -22);
        assertEquals(XML_22, mapper.write(test));
        assertEquals(test, mapper.read(mapper.write(test)));
    }

    @Test
    public void testDeserializeValue() throws XMLStreamException {
        assertNull(mapper.read(XML_0).getCheck());
        assertEquals(Byte.valueOf((byte)123), mapper.read(XML_123).getCheck());
        assertEquals(Byte.valueOf((byte)-22), mapper.read(XML_22).getCheck());
    }

    @XMLMapper
    public static class ByteType {

        private Byte check;

        @Override
        public int hashCode() {
            return Objects.hash(getCheck());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ByteType)) {
                return false;
            }
            ByteType byteType = (ByteType) o;
            return getCheck() == byteType.getCheck();
        }

        public Byte getCheck() {
            return check;
        }

        public void setCheck(Byte check) {
            this.check = check;
        }
    }
}

