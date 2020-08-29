/*
 * Copyright © 2020 Treblereel
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
package org.treblereel.gwt.jackson.client.tests.beans.iface;

import static org.junit.Assert.assertEquals;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.junit.Test;

/** @author Dmitrii Tikhomirov Created by treblereel 5/13/20 */
@J2clTestInput(IfaceBeanTest.class)
public class IfaceBeanTest {

  User_MapperImpl userMapper = User_MapperImpl.INSTANCE;

  @Test
  public void test() throws XMLStreamException {
    User user = new User();
    user.setUser("test");
    IAddress address = new Address();
    address.setAddress("iAddress");
    user.setIAddress(address);

    String userXml = userMapper.write(user);
    // System.out.println(userXml);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><User><user>test</user><iAddress xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address1\"><address>iAddress</address></iAddress></User>",
        userXml);
    assertEquals(user, userMapper.read(userXml));
  }

  @Test
  public void testList() throws XMLStreamException {
    User user = new User();
    user.setUser("test");

    IAddress address1 = new Address();
    address1.setAddress("AAAAA");
    user.setIAddress(address1);

    IAddress address2 = new Address2();
    address2.setAddress("BBB");

    Address3 address3 = new Address3();
    address3.setAddress("CCC");

    List<IAddress> list = new LinkedList<>();
    list.add(address1);
    list.add(address2);
    list.add(address3);

    user.setIAddressList(list);
    user.setIAddressListRef(list);

    List<IAddress> list1 = new ArrayList<>();
    list1.add(address3);
    user.setIAddressOneElm(list1);
    user.setIAddress2OneElm(list1);

    String xml = userMapper.write(user);
    // System.out.println(xml);
    assertEquals(
        "<?xml version='1.0' encoding='UTF-8'?><User><user>test</user><iAddress xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address1\"><address>AAAAA</address></iAddress><iAddressList><iAddressList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address1\"><address>AAAAA</address></iAddressList><iAddressList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address2\"><address>BBB</address></iAddressList><iAddressList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address3\"><address>CCC</address></iAddressList></iAddressList><iAddressListRef><_Address1><address>AAAAA</address></_Address1><_Address2><address>BBB</address></_Address2><_Address3><address>CCC</address></_Address3></iAddressListRef><iAddressOneElm><_Address3><address>CCC</address></_Address3></iAddressOneElm><iAddress2OneElm><iAddress2OneElm xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"_Address3\"><address>CCC</address></iAddress2OneElm></iAddress2OneElm></User>",
        xml);
    assertEquals(user, userMapper.read(xml));
  }
}
